package fr.esdeve.dao;

import java.text.DateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.esdeve.model.Vente;


@Component
@Transactional 
public class VenteDAO extends IGenericDAO<Vente>{

	
	
	@PersistenceContext(unitName = "ventes")
	private EntityManager manager;

	public VenteDAO()
	{
		super(Vente.class);
	}
	
	private Integer getNextVenteNumber()
	{
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Vente> root = criteria.from(Vente.class);
		criteria.where(builder.equal(root.get("year"), java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD)));
		criteria.select(builder.count(root));
		Long result = manager.createQuery(criteria).getSingleResult();
		return result.intValue()+1;
	}
	
	@Override
	public Vente addBean(Vente newVente) {
		String venteId = Integer.toString(java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD));
		venteId += "-";
		venteId += getNextVenteNumber();
		newVente.setId(venteId);
		newVente.setName(newVente.getName()+newVente.getId());
		manager.persist(newVente);
		return newVente;
	}

	@Override
	public void add(Vente newItem) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	protected EntityManager getManager() {
		// TODO Auto-generated method stub
		return manager;
	}

}
