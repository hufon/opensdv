package fr.esdeve.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import fr.esdeve.presenters.VentesTabPresenter;

@Component
@Scope("prototype")
@VaadinView(value = VentesView.NAME, cached = true)
public class VentesView extends Panel implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VentesTabView ventesTab;
	@Autowired
	private VendorTab vendorTab;
	private TabSheet tabSheet;

	public static final String NAME = "";

	public VentesView() {
	}

	@Override
	public void attach() {
		// TODO Auto-generated method stub
		super.attach();
		tabSheet = new TabSheet();
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
