package fr.esdeve.views.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import fr.esdeve.Messages;
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
	private VerticalLayout ventesTabContainer;

	public static final String NAME = "";

	public ApplicationView() {
	}
	

	

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
		//root.setSizeFull();
        Label title = new Label("OpenSDV");
        title.addStyleName("title");
		tabSheet = new TabSheet();
		ventesTabContainer = new VerticalLayout();
		ventesTabContainer.setSizeFull();
		ventesTabContainer.setCaption(Messages.getString("VentesTab.0"));
		tabSheet.addTab(ventesTabContainer);
		root.addComponents(title,tabSheet);
	}

	public VerticalLayout getVentesTabContainer() {
		return ventesTabContainer;
	}




	@Override
	public TabSheet getApplicationTabContainer() {
		// TODO Auto-generated method stub
		return tabSheet;
	}

}
