package fr.esdeve.views.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.ui.Alignment;
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
	
	private TabSheet tabSheet;
	
	private VerticalLayout root;
	public static final String NAME = "";
	
	private VerticalLayout ventesLayout;

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
		root.setId("mainRootLayout");
		root.setSizeFull();
		ventesLayout = new VerticalLayout();
		ventesLayout.setCaption("Gestion des ventes");
		ventesLayout.setSizeFull();
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
        Label title = new Label("OpenSDV");
        titleBar.setHeight("76px");
        title.addStyleName("title");
        titleBar.addComponent(title);
        titleBar.addStyleName("toolbar");
        titleBar.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.addTab(ventesLayout);
		HorizontalLayout statusBar = new HorizontalLayout();
		statusBar.setHeight("30px");
		Label statusTitle = new Label(new Date().toLocaleString());
		statusBar.addComponent(statusTitle);
		statusBar.setComponentAlignment(statusTitle, Alignment.MIDDLE_RIGHT);
		root.addComponents(titleBar,tabSheet,statusBar);
		root.setExpandRatio(tabSheet, 1.0f);
	}


	@Override
	public TabSheet getApplicationTabContainer() {
		// TODO Auto-generated method stub
		return tabSheet;
	}



	@Override
	public VerticalLayout getVentesLayout() {
		return ventesLayout;
	}


}
