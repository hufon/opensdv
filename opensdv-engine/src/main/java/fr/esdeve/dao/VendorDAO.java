package fr.esdeve.dao;

import java.text.DateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import fr.esdeve.model.Vendor;

@Component
public class VendorDAO extends IGenericDAO<Vendor> {
	
	@PersistenceContext(unitName = "ventes")
	private EntityManager manager;
	
	public VendorDAO()
	{
		super(Vendor.class);
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
	

	@Override
	public Vendor addBean(Vendor newVendor) {
		String vendorId = Integer.toString(java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD));
		vendorId += "-";
		vendorId += getNextVendorNumber();
		newVendor.setId(vendorId);
		newVendor.setYear(java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD));
		manager.persist(newVendor);
		return newVendor;
	}

	@Override
	protected EntityManager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Vendor newItem) {
		// TODO Auto-generated method stub
		
	}
	
}
