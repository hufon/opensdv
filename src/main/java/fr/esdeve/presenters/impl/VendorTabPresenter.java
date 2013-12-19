package fr.esdeve.presenters.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.presenters.IApplicationPresenter;
import fr.esdeve.presenters.IVendorTabPresenter;
import fr.esdeve.views.IVendorTabView;
import fr.esdeve.views.IVentesTabView;
import fr.esdeve.views.View;

@SuppressWarnings("serial")
@Component
public class VendorTabPresenter implements IVendorTabPresenter {

	@Autowired
	private IVendorTabView vendorTabView;
	
	
	@Override
	public View getDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
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
