package fr.esdeve.presenters;

import java.util.logging.Logger;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import fr.esdeve.model.Vente;
import fr.esdeve.views.VentesTabView;

public class VentesTabPresenter {
	private VentesTabView view;
	private JPAContainer<Vente> container;

	public VentesTabPresenter(VentesTabView view) {
		this.view  = view;          
		view.addListener(this);
		container = JPAContainerFactory.make(Vente.class, "ventes");
		view.setContainer(container);
	}

	public void addVente()
	{
		Vente vae = new Vente();
		container.addEntity(vae);
		Logger.getLogger("main").info("inserted"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public void selectVente(Object venteId)
	{
		if (venteId!=null)
		{
			EntityItem<Vente> venteItem =
		            container.getItem(venteId);
			view.setFormSource(venteItem);
		}
	}

	public void doDeleteVente(Object itemId) {
		container.removeItem(itemId);
	}
}
