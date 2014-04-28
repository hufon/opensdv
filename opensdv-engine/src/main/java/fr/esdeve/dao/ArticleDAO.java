package fr.esdeve.dao;


import java.text.DateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import fr.esdeve.model.Article;
import fr.esdeve.model.Article_;
import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vente;


@Component
@Transactional 
public class ArticleDAO extends IGenericDAO<Article> {
	
	private Logger LOG = Logger.getGlobal();
	
    @PersistenceContext(unitName = "ventes")
    protected EntityManager manager;
	

	public ArticleDAO()
	{
		super(Article.class);
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
	
	@Override
	public Article addBean(Article article) {
		String articleId = article.getVendor().getId();
		articleId += "-";
		articleId += getNextArticleNumber(article);
		article.setId(articleId);
		manager.persist(article);
		return article;
	}
	
	
	@Transactional
	public void setOrder(Article article, Integer targetOrder)
	{
		LOG.info("Article : "+article.getId()+" set order "+targetOrder);
		Article articleItem = this.get(article.getId());
		articleItem.setVenteOrder(targetOrder);
		this.save(articleItem);
		Query query = manager.createNativeQuery("UPDATE Article SET venteOrder = venteOrder + 1 WHERE venteOrder >= ? AND id <> ? AND vente_id = ?");
		query.setParameter(1, targetOrder);
		query.setParameter(2, article.getId());
		query.setParameter(3, article.getVente().getId());
		query.executeUpdate();
	}
	
	public List<Article> listArticleByVente(Vente vente)
	{
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<Article> criteria = manager.getCriteriaBuilder().createQuery(Article.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.where(builder.equal(root.get("vente"), vente));
		criteria.select(root);
		List<Article> listArticle = manager.createQuery(criteria).getResultList();
		return listArticle;
	}

    public List<Article> listArticleByVendor(Vendor vendor)
    {
        CriteriaBuilder builder= manager.getCriteriaBuilder();
        CriteriaQuery<Article> criteria = manager.getCriteriaBuilder().createQuery(Article.class);
        Root<Article> root = criteria.from(Article.class);
        criteria.where(builder.equal(root.get("vendor"), vendor));
        criteria.select(root);
        List<Article> listArticle = manager.createQuery(criteria).getResultList();
        return listArticle;
    }
	



	@Override
	protected EntityManager getManager() {
		// TODO Auto-generated method stub
		return manager;
	}

	@Override
	public void add(Article newItem) {
		// TODO Auto-generated method stub
		
	}

}
