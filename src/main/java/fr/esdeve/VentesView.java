package fr.esdeve;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import fr.esdeve.model.Vente;
import fr.esdeve.presenters.VentesTabPresenter;
import fr.esdeve.views.VentesTabView;

public class VentesView extends Panel implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VentesTabView ventesTab;
	private VendorTab vendorTab;
	private TabSheet tabSheet;
	JPAContainer<Vente> container;

	public static final String NAME = "";

	public VentesView() {
	}

	@Override
	public void attach() {
		// TODO Auto-generated method stub
		super.attach();
		tabSheet = new TabSheet();
		ventesTab = new VentesTabView();
		vendorTab = new VendorTab();
		new VentesTabPresenter(ventesTab);
		VerticalLayout layout = new VerticalLayout();
		tabSheet.addTab(ventesTab);
		tabSheet.addTab(vendorTab);
		layout.addComponent(tabSheet);
		setContent(layout);
	}

	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}

}
