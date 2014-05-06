package fr.esdeve.dao.impl;

import fr.esdeve.model.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.util.List;


@Component
@Transactional 
public class ClientDAO extends GenericDAO<Client> implements fr.esdeve.dao.IClientDAO {


	@PersistenceContext(unitName = "ventes")
	private EntityManager manager;

    @Override
    protected Order getDefaultOrderBy(CriteriaBuilder builder, Root root) {
        return builder.desc(root.get(Client_.id));
    }


	public ClientDAO()
	{
		super(Client.class);
	}

    @Override
    public Client get(String itemId)
    {
        EntityManager manager= getManager();
        return manager.find(Client.class, Long.parseLong(itemId));
    }

    public List<Client> listClientByVente(Vente vente) {
        CriteriaBuilder builder= manager.getCriteriaBuilder();
        CriteriaQuery<Client> criteria = manager.getCriteriaBuilder().createQuery(Client.class);
        Root<Client> root = criteria.from(Client.class);
        criteria.where(builder.equal(root.get("vente"), vente));
        criteria.select(root);
        List<Client> listClient = manager.createQuery(criteria).getResultList();
        return listClient;
    }



	@Override
	protected EntityManager getManager() {
		// TODO Auto-generated method stub
		return manager;
	}

}
