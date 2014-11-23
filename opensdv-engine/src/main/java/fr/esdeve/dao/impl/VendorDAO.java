package fr.esdeve.dao.impl;

import java.text.DateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import fr.esdeve.model.Vendor_;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.esdeve.model.Vendor;

@Component
@Transactional 
public class VendorDAO extends GenericDAO<Vendor> implements fr.esdeve.dao.IVendorDAO {
	
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
    protected Order getDefaultOrderBy(CriteriaBuilder builder, Root root) {
        return builder.desc(root.get(Vendor_.id));  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	protected EntityManager getManager() {
		// TODO Auto-generated method stub
		return manager;
	}


    @Transactional
    public List<Vendor> searchVendor(String name)
    {
        EntityManager manager= getManager();
        CriteriaBuilder builder= manager.getCriteriaBuilder();
        CriteriaQuery<Vendor> criteria = manager.getCriteriaBuilder().createQuery(Vendor.class);
        Root<Vendor> root = criteria.from(Vendor.class);
        criteria.where(builder.like(builder.lower(root.get(Vendor_.name)),name.toLowerCase()+"%"));
        criteria.select(root);
        List<Vendor> list = manager.createQuery(criteria).getResultList();
        return list;

    }
	
}
