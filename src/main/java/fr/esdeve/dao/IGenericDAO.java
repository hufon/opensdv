package fr.esdeve.dao;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;

import fr.esdeve.model.Article;

public interface IGenericDAO<T> {

	public EntityItem<T> add(T newItem);
	public void remove(Object itemId);
	void setContainer(JPAContainer<T> container);
	JPAContainer<T> getContainer();
}
