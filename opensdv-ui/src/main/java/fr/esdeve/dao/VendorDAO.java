package fr.esdeve.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;

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
import fr.esdeve.model.Vente;

@Component
public class VendorDAO extends IGenericDAO<Vendor> {
	
	public VendorDAO()
	{
		container = JPAContainerFactory.make(Vendor.class, "ventes");
		manager = container.getEntityProvider().getEntityManager();
	}
	
	public Integer getNextVendorNumber()
	{
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Vendor> root = criteria.from(Vendor.class);
		criteria.where(builder.equal(root.get("year"), java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD)));
		criteria.select(builder.count(root));
		Long result = manager.createQuery(criteria).getSingleResult();
		return result.intValue()+1;
	}
	
	public EntityItem<Vendor> add(Vendor newVendor) {
		String vendorId = Integer.toString(java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD));
		vendorId += "-";
		vendorId += getNextVendorNumber();
		newVendor.setId(vendorId);
		newVendor.setYear(java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD));
		Object id = container.addEntity(newVendor);
		EntityItem<Vendor> vendor = container.getItem(id);
		return vendor;
	}
	
}
