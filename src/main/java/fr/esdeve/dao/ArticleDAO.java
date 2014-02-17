package fr.esdeve.dao;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.expressions.Expression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;

import fr.esdeve.model.Article;
import fr.esdeve.model.Vendor;


@Component
public class ArticleDAO implements IGenericDAO<Article> {

	private JPAContainer<Article> container;
	private JPAContainer<Article> articleVentecontainer;
	private EntityManager manager;
	
	@Override
	public JPAContainer<Article> getContainer() {
		return container;
	}
	
	@Override
	public void setContainer(JPAContainer<Article> container) {
		this.container = container;
	}
	
	public ArticleDAO()
	{
		container = JPAContainerFactory.make(Article.class, "ventes");
		articleVentecontainer = JPAContainerFactory.make(Article.class, "ventes");
		manager = container.getEntityProvider().getEntityManager();
		container.addListener(new ItemSetChangeListener() {
			
			@Override
			public void containerItemSetChange(ItemSetChangeEvent event) {
				// TODO Auto-generated method stub
				articleVentecontainer.refresh();
			}
		});
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
	
	public void setOrder(String previousItemId, String newItemId)
	{
		
	}
	
	@Override
	public void remove(Object itemId) {
		this.container.removeItem(itemId);
	}

	public JPAContainer<Article> getArticleVentecontainer() {
		return articleVentecontainer;
	}

	public void setArticleVentecontainer(JPAContainer<Article> articleVentecontainer) {
		this.articleVentecontainer = articleVentecontainer;
	}
}
