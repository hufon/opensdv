package fr.esdeve.presenters.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;

import fr.esdeve.common.ConfirmDialog;
import fr.esdeve.common.ConfirmDialog.ConfirmationDialogCallback;
import fr.esdeve.dao.VenteDAO;
import fr.esdeve.event.AppEvent;
import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.model.Vente;
import fr.esdeve.presenters.IApplicationPresenter;
import fr.esdeve.presenters.IVentesTabPresenter;
import fr.esdeve.views.IVentesTabView;
import fr.esdeve.views.View;

@Component
public class VentesTabPresenter implements IVentesTabPresenter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IVentesTabView ventesTabView;
	@Autowired
	private VenteDAO venteDAO;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	private Logger LOG = Logger.getGlobal();

	@Override
	public View getDisplay() {
		return ventesTabView;
	}
	
	private void doDeleteVente(Object itemId) {
		venteDAO.getContainer().removeItem(itemId);
		ventesTabView.getVaeEditform().setEnabled(false);
	}
	
	private void doSelectVente(Object venteId)
	{
		if (venteId!=null)
		{
			EntityItem<Vente> venteItem =
					venteDAO.getContainer().getItem(venteId);
			ventesTabView.getBinder().setItemDataSource(venteItem);
			ventesTabView.getVaeEditform().setEnabled(true);
		}
	}
	
	@PostConstruct
	@Override
	public void bind() {
		// TODO Auto-generated method stub
		ventesTabView.getAddVenteBtn().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Vente vae = new Vente();
				venteDAO.add(vae);
			}
		});
		
		ventesTabView.setViewVenteButtonClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				LOG.info("View vente..."+event.getButton().getData());
				applicationEventPublisher.publishEvent(new AppEvent(event.getButton().getData(),VentesTabPresenter.this,UIEventTypes.VENTE_DISPLAY));
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
				((Table)event.getSource()).setContainerDataSource(venteDAO.getContainer());
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
			UIEvent uiEvent = (UIEvent)event;
			if(uiEvent.getEventType().equals(UIEventTypes.APPLICATION_VIEW_ATTACHED)){
				handleApplicationViewAttached((UIEvent)event);
			}
			if(uiEvent.getEventType().equals(UIEventTypes.LISTVENTES_DISPLAY) &&
					uiEvent.getSource() instanceof ApplicationPresenter){
				handleApplicationViewAttached((UIEvent)event);
			}
		}
		
	}

	private void handleApplicationViewAttached(UIEvent event) {
		LOG.info("Adding tab : ventesTabView");
		((IApplicationPresenter)event.getSource()).getDisplay().getVentesLayout().removeAllComponents();
		((IApplicationPresenter)event.getSource()).getDisplay().getVentesLayout().addComponent(ventesTabView.getViewRoot());
		
	}
}
