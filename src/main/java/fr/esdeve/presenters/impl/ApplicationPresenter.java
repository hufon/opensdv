package fr.esdeve.presenters.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;

import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.presenters.IApplicationPresenter;
import fr.esdeve.views.IApplicationView;

@SuppressWarnings("serial")
@Component
public class ApplicationPresenter implements IApplicationPresenter {

	
	@Autowired
	private IApplicationView applicationView;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	@PostConstruct
	public void bind() {
		// TODO Auto-generated method stub
		applicationView.getViewRoot().addAttachListener(new AttachListener() {
			
			@Override
			public void attach(AttachEvent event) {
				applicationEventPublisher.publishEvent(
						new UIEvent(ApplicationPresenter.this, UIEventTypes.APPLICATION_VIEW_ATTACHED));
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
