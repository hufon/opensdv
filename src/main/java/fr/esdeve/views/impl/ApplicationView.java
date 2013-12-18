package fr.esdeve.views.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import fr.esdeve.views.IApplicationView;

@Component
public class ApplicationView implements IApplicationView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*@Autowired
	private VentesTabView ventesTab;
	@Autowired
	private VendorTab vendorTab;
	*/
	private TabSheet tabSheet;
	
	private VerticalLayout root;

	public static final String NAME = "";

	public ApplicationView() {
	}
/*
	@Override
	public void attach() {
		// TODO Auto-generated method stub
		super.attach();
		tabSheet = new TabSheet();
		
		tabSheet.addTab(ventesTab);
		tabSheet.addTab(vendorTab);
		layout.addComponent(tabSheet);
		setContent(layout);
	}
*/
	

	

	@Override
	public ComponentContainer getViewRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	@PostConstruct
	public void initView() {
		// TODO Auto-generated method stub
		root = new VerticalLayout();
		root.setSizeFull();
		tabSheet = new TabSheet();
		root.addComponent(tabSheet);
	}

	@Override
	public TabSheet getApplicationTabContainer() {
		// TODO Auto-generated method stub
		return tabSheet;
	}

}
