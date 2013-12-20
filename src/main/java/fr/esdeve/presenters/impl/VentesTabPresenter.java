package fr.esdeve.presenters.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;

import fr.esdeve.common.ConfirmDialog;
import fr.esdeve.common.ConfirmDialog.ConfirmationDialogCallback;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IVentesTabView ventesTabView;
	private JPAContainer<Vente> container;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	private Logger LOG = Logger.getGlobal();

	@Override
	public View getDisplay() {
		return ventesTabView;
	}
	
	private void doDeleteVente(Object itemId) {
		container.removeItem(itemId);
		ventesTabView.getVaeEditform().setEnabled(false);
	}
	
	private void doSelectVente(Object venteId)
	{
		if (venteId!=null)
		{
			EntityItem<Vente> venteItem =
		            container.getItem(venteId);
			ventesTabView.getBinder().setItemDataSource(venteItem);
			ventesTabView.getVaeEditform().setEnabled(true);
		}
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
			public void buttonClick(final ClickEvent event) {
				// TODO Auto-generated method stub
				ConfirmDialog confirm = new ConfirmDialog("Supprimer la vente","êtes vous sur?",
						"Oui", "Non", new ConfirmationDialogCallback() {
							
							@Override
							public void response(boolean ok) {
								if (ok==true) {
									doDeleteVente(event.getButton().getData());
								}
							}
						});
				ventesTabView.getViewRoot().getUI().addWindow(confirm);
			}
		});
		ventesTabView.getSaveVenteBtn().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					ventesTabView.getBinder().commit();
					applicationEventPublisher.publishEvent(
							new UIEvent(VentesTabPresenter.this, UIEventTypes.ITEM_SAVED));
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					LOG.warning(e.toString());
				}
			}
		});
		ventesTabView.getVaeTable().addAttachListener(new AttachListener() {
			
			@Override
			public void attach(AttachEvent event) {
				((Table)event.getSource()).setContainerDataSource(container);
				ventesTabView.buildVaeTable();
			}
		});
		
		ventesTabView.getVaeTable().addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				doSelectVente(event.getProperty().getValue());
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
		
		((IApplicationPresenter)event.getSource()).getDisplay().getApplicationTabContainer().addTab(ventesTabView.getViewRoot());
		
	}
}
