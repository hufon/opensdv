package fr.esdeve.dao;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import fr.esdeve.model.Article;
import fr.esdeve.model.Vendor;

public class ArticleDAO {

	private JPAContainer<Article> container;
	private EntityManager manager;
	public JPAContainer<Article> getContainer() {
		return container;
	}
	public void setContainer(JPAContainer<Article> container) {
		this.container = container;
	}
	
	public ArticleDAO()
	{
		container = JPAContainerFactory.make(Article.class, "ventes");
		manager = container.getEntityProvider().getEntityManager();
	}
}
