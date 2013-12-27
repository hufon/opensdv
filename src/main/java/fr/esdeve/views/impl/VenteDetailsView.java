package fr.esdeve.views.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

import fr.esdeve.views.IVenteDetailsView;

@SuppressWarnings("serial")
@Component
public class VenteDetailsView implements IVenteDetailsView {
	
	private VerticalLayout root;
	private Button returnListButton;

	@Override
	public ComponentContainer getViewRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@PostConstruct
	@Override
	public void initView() {
		root = new VerticalLayout();
		returnListButton = new Button();
		returnListButton.setCaption("Retour à la liste");
		root.addComponents(returnListButton);

	}

	@Override
	public Button getReturnListButton() {
		return returnListButton;
	}

}
