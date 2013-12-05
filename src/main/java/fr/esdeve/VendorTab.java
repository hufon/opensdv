package fr.esdeve;

import java.util.logging.Logger;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import fr.esdeve.dao.ArticleDAO;
import fr.esdeve.dao.VendorDAO;
import fr.esdeve.forms.VaeFieldFactory;
import fr.esdeve.forms.VaeFieldGroup;
import fr.esdeve.model.Vendor;

public class VendorTab extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 697109453422018473L;
	
	private VendorDAO vendorDAO = new VendorDAO();
	private ArticleDAO articleDAO = new ArticleDAO();
	private VaeFieldGroup binder;
	private FormLayout vendorForm;
	private HorizontalSplitPanel harticlepanel;
	private Logger LOG = Logger.getGlobal();

	public VendorTab() {
		LOG.info("Building vendorTab");
		setCaption("Gestion des vendeurs");
		VerticalLayout vlayout = new VerticalLayout();
		Button addVendorBtn = new Button("Ajouter vendeur");
		vlayout.addComponent(addVendorBtn);
		addVendorBtn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Vendor newVendor = new Vendor();
				newVendor.setName("Nouveau vendeur");
				binder.setItemDataSource(vendorDAO.add(newVendor));
				vendorForm.setEnabled(true);
			}
		});
		HorizontalSplitPanel hvendorpanel = new HorizontalSplitPanel();
		hvendorpanel.setSplitPosition(50, Unit.PERCENTAGE);
		Table vendorTable = buildVendorTable();
		vendorForm = buildVendorForm();
		vendorForm.setCaption("Fiche vendeur");
		hvendorpanel.addComponents(vendorTable,vendorForm);
		vlayout.addComponent(hvendorpanel);
		Button addArticleBtn = new Button("Ajouter article");
		vlayout.addComponent(addArticleBtn);
		harticlepanel = new HorizontalSplitPanel();
		harticlepanel.setSplitPosition(50, Unit.PERCENTAGE);
		harticlepanel.setEnabled(false);
		Table articleTable = buildArticleTable();
		harticlepanel.addComponents(articleTable);
		vlayout.addComponent(harticlepanel);
		this.addComponents(vlayout);
	}
	
	private FormLayout buildVendorForm()
	{
		
		FormLayout form = new FormLayout();
		form.setCaption("Edition vendeur"); //$NON-NLS-1$
		form.addStyleName("bordered"); // Custom style //$NON-NLS-1$
		form.setWidth("420px"); //$NON-NLS-1$
		form.setEnabled(false);
		
		
		HorizontalLayout btnLayout = new HorizontalLayout();
		Button saveBtn = new Button(Messages.getString("VentesTab.21")); //$NON-NLS-1$
		saveBtn.addClickListener(new ClickListener() {
			

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				try {
					binder.commit();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vendorForm.setEnabled(false);
			}
		});
		binder = new VaeFieldGroup(new BeanItem<Vendor>(new Vendor()));
		binder.setFieldFactory(new VaeFieldFactory());
		form.removeAllComponents();
		form.addComponent(binder.buildAndBind("Numéro", "number")); //$NON-NLS-1$ //$NON-NLS-2$
		form.addComponent(binder.buildAndBind("Nom", "name")); //$NON-NLS-1$ //$NON-NLS-2$
		form.addComponent(btnLayout);
		form.addComponents(saveBtn);
		form.setMargin(true);
		return form;
	}
	

	private Table buildArticleTable()
	{
		Table table = new Table();// test //$NON-NLS-1$
		table.setWidth("100%"); //$NON-NLS-1$
		table.setSelectable(true);
		table.setImmediate(true);
		table.setContainerDataSource(articleDAO.getContainer());
		//table.setVisibleColumns("id","Numéro"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		table.setSizeFull();
		return table;
	}
	
	private Table buildVendorTable()
	{
		Table table = new Table();// test //$NON-NLS-1$
		table.setWidth("100%"); //$NON-NLS-1$
		table.setSelectable(true);
		table.setImmediate(true);
		table.setContainerDataSource(vendorDAO.getContainer());
		table.setVisibleColumns("number","name"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		table.setColumnHeader("number", "Numéro"); //$NON-NLS-1$ //$NON-NLS-2$
		table.setColumnHeader("name", "Nom"); //$NON-NLS-1$ //$NON-NLS-2$
		table.addValueChangeListener(tableSelectListener);
		table.setSizeFull();
		return table;
	}
	
	private ValueChangeListener tableSelectListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			if (event.getProperty().getValue() != null) {
				EntityItem<Vendor> vendorItem =
			            vendorDAO.getContainer().getItem(event.getProperty().getValue());
				binder.setItemDataSource(vendorItem);
		        vendorForm.setEnabled(true);
		        harticlepanel.setEnabled(true);
			}
		}
	};
	
}
