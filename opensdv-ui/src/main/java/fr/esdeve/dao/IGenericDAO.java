package fr.esdeve.dao;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;

import fr.esdeve.model.Article;
import fr.esdeve.model.Vente;

public abstract class IGenericDAO<T> {

	protected JPAContainer<T> container;
	protected EntityManager manager;
	
	public abstract EntityItem<T> add(T newItem);
	
	public T get(Object itemId) 
	{
		return this.container.getItem(itemId).getEntity();
	}
	
	public void setContainer(JPAContainer<T> container)
	{
		this.container = container;
	}
	
	public  JPAContainer<T>  getContainer() 
	{
		return container;
	}
	
	public void remove(Object itemId) {
		this.container.removeItem(itemId);
	}
}
