package fr.esdeve.views.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import fr.esdeve.views.IVendorTabView;

@Component
@SuppressWarnings("serial")
public class VendorTabView implements IVendorTabView {

	
	private Logger LOG = Logger.getGlobal();
	
	@Override
	public ComponentContainer getViewRoot() {
		// TODO Auto-generated method stub
		return root;
	}
	
	private VerticalLayout root;
	private Button addVendorBtn;
	private Table vendorTable;
	private Button addArticleBtn;

	@PostConstruct
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		LOG.info("Building VendorTabView");
		root = new VerticalLayout();
		root.setCaption("Gestion des vendeurs");
		addVendorBtn = new Button("Ajouter vendeur");
		root.addComponent(addVendorBtn);
		HorizontalSplitPanel hvendorpanel = new HorizontalSplitPanel();
		hvendorpanel.setSplitPosition(50, Unit.PERCENTAGE);
		root.addComponent(hvendorpanel);
		vendorTable = new Table();// test //$NON-NLS-1$
		vendorTable.setWidth("100%"); //$NON-NLS-1$
		vendorTable.setSelectable(true);
		vendorTable.setImmediate(true);
		hvendorpanel.addComponent(vendorTable);
		addArticleBtn = new Button("Ajouter article");
		root.addComponent(addArticleBtn);
	}

}
