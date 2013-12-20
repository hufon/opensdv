package fr.esdeve.dao;

import com.vaadin.addon.jpacontainer.EntityItem;

import fr.esdeve.model.Article;

public interface IGenericDAO<T> {

	public EntityItem<T> add(T newItem);
	public void remove(Object itemId);
}
