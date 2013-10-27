package fr.esdev.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;

public class GenericDao<T> {
	
	
	public T update(T entity)  {
		EntityManager em = getEm();
		EntityTransaction transac = em.getTransaction();
		transac.begin();
        em.persist(entity);
        transac.commit();
        return entity;
	}
	
	final public EntityManager getEm() {
        return JPAContainerFactory.createEntityManagerForPersistenceUnit("ventes");        
	}
}
