package fr.esdeve.presenters.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

import fr.esdeve.common.ConfirmDialog;
import fr.esdeve.common.ConfirmDialog.ConfirmationDialogCallback;
import fr.esdeve.dao.VendorDAO;
import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.model.Vendor;
import fr.esdeve.presenters.IApplicationPresenter;
import fr.esdeve.presenters.IVendorTabPresenter;
import fr.esdeve.views.IVendorTabView;
import fr.esdeve.views.IVentesTabView;
import fr.esdeve.views.View;
import fr.esdeve.views.impl.VendorTabView;

@SuppressWarnings("serial")
@Component
public class VendorTabPresenter implements IVendorTabPresenter {

	@Autowired
	private IVendorTabView vendorTabView;
	
	@Autowired
	private VendorDAO vendorDAO;
	
	
	@Override
	public View getDisplay() {
		// TODO Auto-generated method stub
		return vendorTabView;
	}
	
	private void doDeleteVendor(Object itemId) {
		// TODO Auto-generated method stub
		vendorDAO.remove(itemId);
	}

	@PostConstruct
	@Override
	public void bind() {
		// TODO Auto-generated method stub
		vendorTabView.getVendorTable().addAttachListener(new AttachListener() {
			
			@Override
			public void attach(AttachEvent event) {
				((Table)event.getSource()).setContainerDataSource(vendorDAO.getContainer());
				vendorTabView.buildVendorTable();
			}
		});
		
		vendorTabView.getVendorTable().addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					EntityItem<Vendor> vendorItem = vendorDAO.getContainer()
							.getItem(event.getProperty().getValue());
					vendorTabView.getBinder().setItemDataSource(vendorItem);
					vendorTabView.getVendorForm().setEnabled(true);
				}
			}
		});
		
		vendorTabView.getAddVendorBtn().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Vendor newVendor = new Vendor();
				newVendor.setName("Nouveau vendeur");
				vendorTabView.getBinder().setItemDataSource(vendorDAO.add(newVendor));
				vendorTabView.getVendorForm().setEnabled(true);
			}
		});
		
		vendorTabView.getSaveVendorBtn().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					vendorTabView.getBinder().commit();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		vendorTabView.setRemoveVendorClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(final ClickEvent event) {
				// TODO Auto-generated method stub
				ConfirmDialog confirm = new ConfirmDialog("Supprimer la vente","êtes vous sur?",
						"Oui", "Non", new ConfirmationDialogCallback() {
							
							@Override
							public void response(boolean ok) {
								if (ok==true) {
									doDeleteVendor(event.getButton().getData());
									vendorTabView.getVendorForm().setEnabled(false);
								}
							}
						});
				vendorTabView.getViewRoot().getUI().addWindow(confirm);
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
		((IApplicationPresenter)event.getSource()).getDisplay().getApplicationTabContainer().addTab(vendorTabView.getViewRoot());
	}

}
