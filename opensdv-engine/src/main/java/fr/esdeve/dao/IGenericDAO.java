package fr.esdeve.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import fr.esdeve.model.Article;
import fr.esdeve.model.Vente;

public abstract class IGenericDAO<T> {

	private final Class<T> persistentClass;

    protected abstract Order getDefaultOrderBy(CriteriaBuilder builder, Root root);
	

	protected abstract EntityManager getManager();
	
	public abstract void add(T newItem);
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
        criteria.orderBy(getDefaultOrderBy(builder,root));
		List<T> list = manager.createQuery(criteria).getResultList();
		return list;

	}
}
