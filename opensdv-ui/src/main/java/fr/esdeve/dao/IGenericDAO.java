package fr.esdeve.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;

import fr.esdeve.model.Article;
import fr.esdeve.model.Vente;

public abstract class IGenericDAO<T> {

	protected JPAContainer<T> container;
	private final Class<T> persistentClass;
	

	protected abstract EntityManager getManager();
	
	public abstract EntityItem<T> add(T newItem);
	public abstract T addBean(T newItem);
	
	public IGenericDAO(Class<T> clasz)
	{
		this.persistentClass = clasz;
	}
	
	public T get(String itemId) 
	{
		EntityManager manager= getManager();
		
		return manager.find(this.persistentClass, itemId);
	}
	
	public void setContainer(JPAContainer<T> container)
	{
		this.container = container;
	}
	
	public  JPAContainer<T>  getContainer() 
	{
		return container;
	}
	
	public void remove(T item) {
		EntityManager manager= getManager();
		manager.remove(item);
	}
	
	@Transactional
	public void remove(String itemId) {
		this.remove(get(itemId));
	}
	
	@Transactional
	public void save(T item) {
		EntityManager manager= getManager();
		item = manager.merge(item);
		manager.persist(item);
	}
	
	
	@Transactional
	public List<T> list()
	{
		EntityManager manager= getManager();
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = manager.getCriteriaBuilder().createQuery(persistentClass);
		Root<T> root = criteria.from(persistentClass);
		criteria.select(root);
		List<T> list = manager.createQuery(criteria).getResultList();
		return list;

	}
}
