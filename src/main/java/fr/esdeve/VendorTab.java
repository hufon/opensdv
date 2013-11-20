package fr.esdeve;

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

import fr.esdeve.forms.VaeFieldFactory;
import fr.esdeve.forms.VaeFieldGroup;
import fr.esdeve.model.Vendor;

public class VendorTab extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 697109453422018473L;
	
	private JPAContainer<Vendor> vendorContainer;

	private VaeFieldGroup binder;
	private FormLayout vendorForm;

	public VendorTab() {
		setCaption("Gestion des vendeurs");
		HorizontalLayout toolbar = new HorizontalLayout();
		Button addVendorBtn = new Button("Ajouter vendeur");
		toolbar.addComponent(addVendorBtn);
		addVendorBtn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Vendor newVendor = new Vendor();
				newVendor.setName("Nouveau vendeur");
				Object id = vendorContainer.addEntity(newVendor);
				EntityItem<Vendor> vendor = vendorContainer.getItem(id);
				vendor.getItemProperty("number").setValue(newVendor.getId());
				vendorContainer.commit();
				binder.setItemDataSource(vendor);
				vendorForm.setEnabled(true);
			}
		});
		vendorContainer = JPAContainerFactory.make(Vendor.class, "ventes");
		HorizontalSplitPanel hpanel = new HorizontalSplitPanel();
		hpanel.setSplitPosition(50, Unit.PERCENTAGE);
		Table vendorTable = buildVendorTable();
		vendorForm = buildVendorForm();
		hpanel.addComponents(vendorTable,vendorForm);
		this.addComponents(toolbar,hpanel);
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
	
	private Table buildVendorTable()
	{
		Table table = new Table();// test //$NON-NLS-1$
		table.setWidth("100%"); //$NON-NLS-1$
		table.setSelectable(true);
		table.setImmediate(true);
		table.setContainerDataSource(vendorContainer);
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
			            vendorContainer.getItem(event.getProperty().getValue());
				binder.setItemDataSource(vendorItem);
		        vendorForm.setEnabled(true);
			}
		}
	};
	
}
