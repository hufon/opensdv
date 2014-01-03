package fr.esdeve.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public interface IVenteDetailsView extends View {

	Button getReturnListButton();

	VerticalLayout getArticleListContainer();

}
