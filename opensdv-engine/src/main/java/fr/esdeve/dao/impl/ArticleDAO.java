package fr.esdeve.dao.impl;


import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import fr.esdeve.model.Article;
import fr.esdeve.model.Article_;
import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vente;


@Component
@Transactional 
public class ArticleDAO extends GenericDAO<Article> implements fr.esdeve.dao.IArticleDAO {
	
	private Logger LOG = Logger.getGlobal();
	
    @PersistenceContext(unitName = "ventes")
    protected EntityManager manager;
	

	public ArticleDAO()
	{
		super(Article.class);
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
    protected Order getDefaultOrderBy(CriteriaBuilder builder, Root root) {
        return builder.desc(root.get(Article_.id));  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	protected EntityManager getManager() {
		// TODO Auto-generated method stub
		return manager;
	}

}
