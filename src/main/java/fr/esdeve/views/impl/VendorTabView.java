package fr.esdeve.views.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Table.ColumnGenerator;

import fr.esdeve.Messages;
import fr.esdeve.forms.VaeFieldFactory;
import fr.esdeve.forms.VaeFieldGroup;
import fr.esdeve.model.Vendor;
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
	private VerticalLayout articleListContainer;


	private Button saveVendorBtn;
	private VaeFieldGroup binder;
	private FormLayout vendorForm;
	private ClickListener removeVendorClickListener;

	@Override
	public VerticalLayout getArticleListContainer() {
		return articleListContainer;
	}
	
	@Override
	public void setRemoveVendorClickListener(ClickListener removeVendorClickListener) {
		this.removeVendorClickListener = removeVendorClickListener;
	}

	@Override
	public FormLayout getVendorForm() {
		return vendorForm;
	}

	@PostConstruct
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		LOG.info("Building VendorTabView");
		root = new VerticalLayout();
		root.setCaption("Gestion des vendeurs");
		root.setSpacing(true);
		root.setMargin(true);
		root.setSizeFull();
		Panel mainPanel = new Panel();
		mainPanel.setSizeFull();
		mainPanel.setHeight("100%");
		VerticalSplitPanel vsplit = new VerticalSplitPanel();
		vsplit.setHeight("100%");
		vsplit.setSizeFull();
		vsplit.setSplitPosition(50, Unit.PERCENTAGE);
		mainPanel.setContent(vsplit);
		root.addComponent(mainPanel);
		VerticalLayout vendorLayout = new VerticalLayout();
		vendorLayout.setSizeFull();
		vendorLayout.setMargin(true);
		HorizontalLayout toolbar = new HorizontalLayout();
		addVendorBtn = new Button("Ajouter vendeur");
		toolbar.addComponents(addVendorBtn);
		toolbar.setHeight("40px");
		HorizontalSplitPanel hvendorpanel = new HorizontalSplitPanel();
		hvendorpanel.setSizeFull();
		hvendorpanel.setSplitPosition(50, Unit.PERCENTAGE);
		vendorTable = new Table();// test //$NON-NLS-1$
		vendorTable.setSizeFull(); //$NON-NLS-1$
		vendorTable.setSelectable(true);
		vendorTable.setImmediate(true);
		hvendorpanel.addComponent(vendorTable);
		Panel formPanel = new Panel(buildVendorForm());
		formPanel.setCaption("Edition vendeur");
		hvendorpanel.addComponent(formPanel);
		vendorLayout.addComponents(toolbar,hvendorpanel);
		vendorLayout.setExpandRatio(hvendorpanel, 1.0f);
		vsplit.setFirstComponent(vendorLayout);
		
		VerticalLayout articleLayout = new VerticalLayout();
		articleLayout.setMargin(true);
		articleLayout.setSizeFull();
		HorizontalLayout toolbar2 = new HorizontalLayout();
		addArticleBtn = new Button("Ajouter article");
		toolbar2.setHeight("40px");
		toolbar2.addComponent(addArticleBtn);
		addArticleBtn.setEnabled(false);
		articleLayout.addComponent(toolbar2);
		articleListContainer = new VerticalLayout();
		articleListContainer.setSizeFull();
		articleLayout.addComponent(articleListContainer);
		articleLayout.setExpandRatio(articleListContainer, 1.0f);
		vsplit.setSecondComponent(articleLayout);
	}
	
	@Override
	public Button getAddVendorBtn() {
		return addVendorBtn;
	}

	@Override
	public Table getVendorTable() {
		return vendorTable;
	}

	@Override
	public Button getAddArticleBtn() {
		return addArticleBtn;
	}

	@Override
	public Button getSaveVendorBtn() {
		return saveVendorBtn;
	}

	private FormLayout buildVendorForm() {

		vendorForm = new FormLayout();
		vendorForm.setCaption("Edition vendeur"); //$NON-NLS-1$
		vendorForm.addStyleName("bordered"); // Custom style //$NON-NLS-1$
		vendorForm.setWidth("420px"); //$NON-NLS-1$
		vendorForm.setEnabled(false);

		HorizontalLayout btnLayout = new HorizontalLayout();
	    saveVendorBtn = new Button(Messages.getString("VentesTab.21")); //$NON-NLS-1$
		binder = new VaeFieldGroup(new BeanItem<Vendor>(new Vendor()));
		binder.setFieldFactory(new VaeFieldFactory());
		vendorForm.removeAllComponents();
		vendorForm.addComponent(binder.buildAndBind("Numéro", "number")); //$NON-NLS-1$ //$NON-NLS-2$
		vendorForm.addComponent(binder.buildAndBind("Nom", "name")); //$NON-NLS-1$ //$NON-NLS-2$
		vendorForm.addComponent(btnLayout);
		vendorForm.addComponents(saveVendorBtn);
		vendorForm.setMargin(true);
		return vendorForm;
	}

	@Override
	public void buildVendorTable() {
		vendorTable.removeGeneratedColumn("actions");
		vendorTable.setVisibleColumns("number", "name"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		vendorTable.setColumnHeader("number", "Numéro"); //$NON-NLS-1$ //$NON-NLS-2$
		vendorTable.setColumnHeader("name", "Nom"); //$NON-NLS-1$ //$NON-NLS-2$
		vendorTable.addGeneratedColumn("actions", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Button removeButton = new Button("Supprimer");
				removeButton.setData(itemId);
				removeButton.addClickListener(removeVendorClickListener);
				return new HorizontalLayout(removeButton);
			}
		});
		vendorTable.setSizeFull();
	}
	
	
	@Override
	public VaeFieldGroup getBinder() {
		return binder;
	}

}
