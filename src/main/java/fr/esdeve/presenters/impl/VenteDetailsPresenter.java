package fr.esdeve.presenters.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import fr.esdeve.event.AppEvent;
import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.presenters.IVenteDetailsPresenter;
import fr.esdeve.views.IArticleListView;
import fr.esdeve.views.IVenteDetailsView;
import fr.esdeve.views.View;

@SuppressWarnings("serial")
@Component
public class VenteDetailsPresenter implements IVenteDetailsPresenter {

	@Autowired
	IVenteDetailsView venteDetailsView;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	private IArticleListView articleListView;
	
	@Override
	public View getDisplay() {
		// TODO Auto-generated method stub
		return venteDetailsView;
	}

	@PostConstruct
	@Override
	public void bind() {
		// TODO Auto-generated method stub
		venteDetailsView.getReturnListButton().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				applicationEventPublisher.publishEvent(
						new UIEvent(VenteDetailsPresenter.this, UIEventTypes.LISTVENTES_DISPLAY));
			}
		});
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AppEvent) {
			if (((((AppEvent) event)).getSource() instanceof ApplicationPresenter) &&
			(((AppEvent) event)).getEventType().equals(UIEventTypes.VENTE_DISPLAY))
			{
				((ApplicationPresenter)((AppEvent) event).getSource()).getDisplay().getVentesLayout().removeAllComponents();
				((ApplicationPresenter)((AppEvent) event).getSource()).getDisplay().getVentesLayout().addComponent(venteDetailsView.getViewRoot());
			}
		}
		if (event instanceof UIEvent) {
			if (((UIEvent) event).getEventType().equals(
					UIEventTypes.APPLICATION_VIEW_ATTACHED)) {
				handleApplicationViewAttached((UIEvent) event);
			}
		}

	}

	private void handleApplicationViewAttached(UIEvent event) {
		venteDetailsView.getArticleListContainer().addComponent(
				articleListView.getViewRoot());
		
	}

}
