package fr.esdeve.views.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.client.ui.VSlider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

import fr.esdeve.views.IVenteDetailsView;

@SuppressWarnings("serial")
@Component
public class VenteDetailsView implements IVenteDetailsView {
	
	private VerticalLayout root;
	private Button returnListButton;
	private VerticalLayout articleListContainer;

	@Override
	public ComponentContainer getViewRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@PostConstruct
	@Override
	public void initView() {
		root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		HorizontalLayout toolbar = new HorizontalLayout();
		VerticalSplitPanel vsplit = new VerticalSplitPanel();
		vsplit.setHeight("100%");
		vsplit.setSizeFull();
		vsplit.setSplitPosition(50, Unit.PERCENTAGE);
		returnListButton = new Button();
		returnListButton.setCaption("Retour à la liste");
		toolbar.addComponent(returnListButton);
		toolbar.setHeight("33px");
		articleListContainer = new VerticalLayout();
		articleListContainer.setSizeFull();
		vsplit.setFirstComponent(articleListContainer);
		vsplit.setSecondComponent(new VerticalLayout());
		root.addComponents(toolbar,vsplit);
		root.setExpandRatio(vsplit, 1.0f);

	}

	@Override
	public VerticalLayout getArticleListContainer() {
		return articleListContainer;
	}

	@Override
	public Button getReturnListButton() {
		return returnListButton;
	}

}
