package fr.esdeve.presenters.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;

import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.presenters.IApplicationPresenter;
import fr.esdeve.views.IApplicationView;

@SuppressWarnings("serial")
@Component
public class ApplicationPresenter implements IApplicationPresenter {

	private Logger LOG = Logger.getGlobal();
	
	@Autowired
	private IApplicationView applicationView;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	@PostConstruct
	public void bind() {
		// TODO Auto-generated method stub
		applicationView.getViewRoot().addAttachListener(new AttachListener() {
			
			private static final long serialVersionUID = 1L;
			@Override
			public void attach(AttachEvent event) {
				LOG.info("Attaching applicationView");
				applicationEventPublisher.publishEvent(
						new UIEvent(ApplicationPresenter.this, UIEventTypes.APPLICATION_VIEW_ATTACHED));
			}
		});
		
		applicationView.getApplicationTabContainer().addSelectedTabChangeListener(new SelectedTabChangeListener() {
			
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				// TODO Auto-generated method stub
				LOG.info("Tab changed...");
			}
		});

	}

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public IApplicationView getDisplay() {
		// TODO Auto-generated method stub
		return applicationView;
	}

}
