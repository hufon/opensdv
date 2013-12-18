package fr.esdeve.views;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TabSheet;

public interface IApplicationView extends View {
	TabSheet getApplicationTabContainer();
	ComponentContainer getVentesTabContainer();
}
