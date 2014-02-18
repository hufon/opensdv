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

	private BeanItemContainer<Article> articleVentecontainer;
	private EntityManager manager;
	
	private Logger LOG = Logger.getGlobal();
	
    @PersistenceContext(unitName = "ventes")
    protected EntityManager em;
	

	public ArticleDAO()
	{
		container = JPAContainerFactory.make(Article.class, "ventes");
		//articleVentecontainer = JPAContainerFactory.make(Article.class, "ventes");
		articleVentecontainer = new BeanItemContainer<Article>(Article.class);
		manager = container.getEntityProvider().getEntityManager();
		container.addListener(new ItemSetChangeListener() {
			
			@Override
			public void containerItemSetChange(ItemSetChangeEvent event) {
				// TODO Auto-generated method stub
				listArticle();
			}
		});
	}
	
	public Integer getNextArticleOrder(Article article, Vente vente)
	{
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.where(builder.equal(root.get("vente"), vente));
		
		criteria.select(builder.max(root.get(Article_.venteOrder)));
		Integer result = manager.createQuery(criteria).getSingleResult();
		return result+1;
	}
	
	private Integer getNextArticleNumber(Article article)
	{
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.where(builder.equal(root.get("vendor"), article.getVendor()));
		criteria.select(builder.count(root));
		Long result = manager.createQuery(criteria).getSingleResult();
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
	
	public void setOrder(Article article, Integer targetOrder)
	{
		LOG.info("Article : "+article.getId()+" set order "+targetOrder);
		BeanItem<Article> articleItem = articleVentecontainer.getItem(article.getId());
		articleItem.getItemProperty("venteOrder").setValue(targetOrder);
		//articleItem.commit();
		manager.getTransaction().begin();
		Query query = manager.createNativeQuery("UPDATE Article SET venteOrder = venteOrder + 1 WHERE venteOrder >= ? AND id <> ? AND vente_id = ?");
		query.setParameter(1, targetOrder);
		query.setParameter(2, article.getId());
		query.setParameter(3, article.getVente().getId());
		query.executeUpdate();
		manager.getTransaction().commit();
		//articleVentecontainer.refresh();
	}
	
	public void listArticle()
	{
		CriteriaBuilder builder= em.getCriteriaBuilder();
		CriteriaQuery<Article> criteria = em.getCriteriaBuilder().createQuery(Article.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.select(root);
		List<Article> listArticle = em.createQuery(criteria).getResultList();
		articleVentecontainer.removeAllItems();
		articleVentecontainer.addAll(listArticle);
	}
	


	public BeanItemContainer<Article> getArticleVentecontainer() {
		return articleVentecontainer;
	}

	public void setArticleVentecontainer(BeanItemContainer<Article> articleVentecontainer) {
		this.articleVentecontainer = articleVentecontainer;
	}

}
