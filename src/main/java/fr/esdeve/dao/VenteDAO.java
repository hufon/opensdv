package fr.esdeve.dao;

import java.text.DateFormat;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import fr.esdeve.model.Article;
import fr.esdeve.model.Vente;


@Component
public class VenteDAO implements IGenericDAO<Vente>{

	
	private JPAContainer<Vente> container;
	private EntityManager manager;

	public VenteDAO()
	{
		container = JPAContainerFactory.make(Vente.class, "ventes");
		manager = container.getEntityProvider().getEntityManager();
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
	public EntityItem<Vente> add(Vente newVente) {
		String venteId = Integer.toString(java.util.Calendar.getInstance().get(DateFormat.YEAR_FIELD));
		venteId += "-";
		venteId += getNextVenteNumber();
		newVente.setId(venteId);
		newVente.setName(newVente.getName()+newVente.getId());
		Object id = container.addEntity(newVente);
		EntityItem<Vente> venteItem = container.getItem(id);
		return venteItem;
	}

	@Override
	public void remove(Object itemId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContainer(JPAContainer<Vente> container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPAContainer<Vente> getContainer() {
		return container;
	}

}
