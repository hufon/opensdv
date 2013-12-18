package fr.esdeve.dao;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.expressions.Expression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vendor_;

public class VendorDAO {
	private JPAContainer<Vendor> container;
	private EntityManager manager;

	public JPAContainer<Vendor> getContainer() {
		return container;
	}

	public void setContainer(JPAContainer<Vendor> container) {
		this.container = container;
	}
	
	public VendorDAO()
	{
		container = JPAContainerFactory.make(Vendor.class, "ventes");
		manager = container.getEntityProvider().getEntityManager();
	}
	
	public Integer getNextVendorNumber()
	{
		//CriteriaQuery<Vendor> crit=manager.getCriteriaBuilder().max(Expression.)
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteria = builder.createQuery(BigDecimal.class);
		Root<Vendor> root = criteria.from(Vendor.class);
		criteria.select(builder.max(root.get(Vendor_.number)));
		BigDecimal maxNumber = manager.createQuery(criteria).getSingleResult();
		if (maxNumber == null)
			return 0;
		else 
			return maxNumber.intValue()+1;
	}
	
	public EntityItem<Vendor> add(Vendor newVendor) {
		Object id = container.addEntity(newVendor);
		EntityItem<Vendor> vendor = container.getItem(id);
		vendor.getItemProperty("number").setValue(getNextVendorNumber());
		vendor.commit();
		return vendor;
	}

}
