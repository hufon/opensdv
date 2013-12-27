package fr.esdeve.views;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public interface IApplicationView extends View {
	TabSheet getApplicationTabContainer();

	VerticalLayout getVentesLayout();
}
