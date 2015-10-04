package fr.esdeve.dao.impl;


import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.esdeve.dao.IArticleVenteDAO;
import fr.esdeve.model.Article;
import fr.esdeve.model.ArticleVente;
import fr.esdeve.model.ArticleVente_;
import fr.esdeve.model.Article_;
import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vente;


@Component
@Transactional 
public class ArticleVenteDAO extends GenericDAO<ArticleVente> implements IArticleVenteDAO {
	
	private Logger LOG = Logger.getGlobal();
	
    @PersistenceContext(unitName = "ventes")
    protected EntityManager manager;
	

	public ArticleVenteDAO()
	{
		super(ArticleVente.class);
	}
	
	

	
	
	
	public List<ArticleVente> listArticleByVente(Vente vente)
	{
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<ArticleVente> criteria = manager.getCriteriaBuilder().createQuery(ArticleVente.class);
		Root<ArticleVente> root = criteria.from(ArticleVente.class);
		criteria.where(builder.equal(root.get("vente"), vente));
		criteria.select(root);
		List<ArticleVente> listArticle = manager.createQuery(criteria).getResultList();
		return listArticle;
	}

    @Override
    protected Order getDefaultOrderBy(CriteriaBuilder builder, Root root) {
        return builder.desc(root.get(Article_.id));  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	protected EntityManager getManager() {
		// TODO Auto-generated method stub
		return manager;
	}

	public Integer getNextArticleOrder(ArticleVente articleVente, Vente vente) {
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
		Root<ArticleVente> root = criteria.from(ArticleVente.class);
		criteria.where(builder.equal(root.get("vente"), vente));
		
		criteria.select(builder.max(root.get(ArticleVente_.venteOrder)));
		Integer result = manager.createQuery(criteria).getSingleResult();
		return result+1;
	}

	public void setOrder(ArticleVente article, Integer targetOrder) {
		LOG.info("Article : "+article.getId()+" set order "+targetOrder);
		ArticleVente articleItem = this.get(article.getId());
		articleItem.setVenteOrder(targetOrder);
		this.save(articleItem);
		Query query = manager.createNativeQuery("UPDATE ArticleVente SET venteOrder = venteOrder + 1 WHERE venteOrder >= ? AND id <> ? AND vente_id = ?");
		query.setParameter(1, targetOrder);
		query.setParameter(2, article.getId());
		query.setParameter(3, article.getVente().getId());
		query.executeUpdate();
		
	}

}
