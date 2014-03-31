package fr.esdeve.dao;


import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.expressions.Expression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

import fr.esdeve.model.Article;
import fr.esdeve.model.Article_;
import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vente;


@Component
@Transactional 
public class ArticleDAO extends IGenericDAO<Article> {
	
	private Logger LOG = Logger.getGlobal();
	
    @PersistenceContext(unitName = "ventes")
    protected EntityManager em;
	

	public ArticleDAO()
	{
		super(Article.class);
		container = JPAContainerFactory.make(Article.class, "ventes");
	}
	
	public Integer getNextArticleOrder(Article article, Vente vente)
	{
		CriteriaBuilder builder= em.getCriteriaBuilder();
		CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.where(builder.equal(root.get("vente"), vente));
		
		criteria.select(builder.max(root.get(Article_.venteOrder)));
		Integer result = em.createQuery(criteria).getSingleResult();
		return result+1;
	}
	
	private Integer getNextArticleNumber(Article article)
	{
		CriteriaBuilder builder= em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.where(builder.equal(root.get("vendor"), article.getVendor()));
		criteria.select(builder.count(root));
		Long result = em.createQuery(criteria).getSingleResult();
		return result.intValue()+1;
	}
	
	public EntityItem<Article> add(Article article) {
		String articleId = article.getVendor().getId();
		articleId += "-";
		articleId += getNextArticleNumber(article);
		article.setId(articleId);
		Object id = container.addEntity(article);
		EntityItem<Article> articleItem = container.getItem(id);
		return articleItem;
	}
	
	@Transactional
	public void setOrder(Article article, Integer targetOrder)
	{
		LOG.info("Article : "+article.getId()+" set order "+targetOrder);
		Article articleItem = this.get(article.getId());
		articleItem.setVenteOrder(targetOrder);
		this.save(articleItem);
		Query query = em.createNativeQuery("UPDATE Article SET venteOrder = venteOrder + 1 WHERE venteOrder >= ? AND id <> ? AND vente_id = ?");
		query.setParameter(1, targetOrder);
		query.setParameter(2, article.getId());
		query.setParameter(3, article.getVente().getId());
		query.executeUpdate();
	}
	
	public List<Article> listArticleByVente(Vente vente)
	{
		CriteriaBuilder builder= em.getCriteriaBuilder();
		CriteriaQuery<Article> criteria = em.getCriteriaBuilder().createQuery(Article.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.where(builder.equal(root.get("vente"), vente));
		criteria.select(root);
		List<Article> listArticle = em.createQuery(criteria).getResultList();
		return listArticle;
	}
	



	@Override
	public Article addBean(Article newItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EntityManager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
