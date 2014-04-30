package fr.esdeve.dao;

import java.text.DateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import fr.esdeve.model.Vente_;
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

    private String getVenteId(Integer number) {
        String venteId = Integer.toString(java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD));
        venteId += "-";
        venteId += number;
        return venteId;
    }
	
	private Integer getNextVenteNumber()
	{
		CriteriaBuilder builder= manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Vente> root = criteria.from(Vente.class);
		criteria.where(builder.equal(root.get("year"), java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD)));
		criteria.select(builder.count(root));
		Long result = manager.createQuery(criteria).getSingleResult();
		return checkNextVenteNumber(result.intValue()+1);
	}

    private Integer checkNextVenteNumber(Integer number)
    {
        if (this.get(getVenteId(number))!=null)
        {
             return checkNextVenteNumber(number+1);
        } else
            return number;
    }

    @Override
    protected Order getDefaultOrderBy(CriteriaBuilder builder, Root root)
    {
        return builder.desc(root.get(Vente_.date));
    }

	public Vente addBean(Vente newVente) {
		newVente.setId(getVenteId(getNextVenteNumber()));
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
