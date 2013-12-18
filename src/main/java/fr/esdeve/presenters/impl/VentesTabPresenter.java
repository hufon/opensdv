package fr.esdeve.presenters.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Button.ClickEvent;

import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.model.Vente;
import fr.esdeve.presenters.IApplicationPresenter;
import fr.esdeve.presenters.IVentesTabPresenter;
import fr.esdeve.views.IVentesTabView;
import fr.esdeve.views.View;
import fr.esdeve.views.impl.VentesTabView;

@Component
public class VentesTabPresenter implements IVentesTabPresenter {
	
	@Autowired
	private IVentesTabView ventesTabView;
	private JPAContainer<Vente> container;
	
	private Logger LOG = Logger.getGlobal();
/*
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
	*/

	@Override
	public View getDisplay() {
		// TODO Auto-generated method stub
		return ventesTabView;
	}
	
	private void doDeleteVente(Object itemId) {
		container.removeItem(itemId);
	}
	
	@PostConstruct
	@Override
	public void bind() {
		// TODO Auto-generated method stub
		container = JPAContainerFactory.make(Vente.class, "ventes");
		ventesTabView.getAddVenteBtn().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Vente vae = new Vente();
				container.addEntity(vae);
			}
		});
		ventesTabView.setRemoveClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				doDeleteVente(event.getButton().getData());
			}
		});
	}
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof UIEvent){
			if(((UIEvent) event).getEventType().equals(UIEventTypes.APPLICATION_VIEW_ATTACHED)){
				handleApplicationViewAttached((UIEvent)event);
			}
		}
		
	}

	private void handleApplicationViewAttached(UIEvent event) {
		LOG.info("Adding tab : ventesTabView");
		ventesTabView.getVaeTable().setContainerDataSource(container);
		ventesTabView.buildVaeTable();
		((IApplicationPresenter)event.getSource()).getDisplay().getApplicationTabContainer()
			.addTab(ventesTabView.getViewRoot());
		
	}
}
