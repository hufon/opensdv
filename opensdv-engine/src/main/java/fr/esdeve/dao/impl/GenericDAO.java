package fr.esdeve.dao.impl;

import java.text.DateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import fr.esdeve.model.Vendor;
import org.springframework.transaction.annotation.Transactional;

import fr.esdeve.model.Article;
import fr.esdeve.model.Vente;

public abstract class GenericDAO<T> implements fr.esdeve.dao.IGenericDAO<T> {

	private final Class<T> persistentClass;

    protected abstract Order getDefaultOrderBy(CriteriaBuilder builder, Root root);
	

	protected abstract EntityManager getManager();

    public GenericDAO(Class<T> clasz)
	{
		this.persistentClass = clasz;
	}
	
    public T get(String itemId)
	{
		EntityManager manager= getManager();
		return manager.find(this.persistentClass, itemId);
	}

	public T get(Long itemId)
	{
		EntityManager manager= getManager();
		return manager.find(this.persistentClass, itemId);
	}

    @Transactional
    public T addBean(T newBean) {
        EntityManager manager= getManager();
        manager.persist(newBean);
        return newBean;
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
