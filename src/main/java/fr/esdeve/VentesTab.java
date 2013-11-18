package fr.esdeve;

import java.util.Date;
import java.util.logging.Logger;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import fr.esdeve.forms.VaeFieldFactory;
import fr.esdeve.forms.VaeFieldGroup;
import fr.esdeve.model.Vente;

public class VentesTab extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button button;
	private JPAContainer<Vente> container;
	private Button removeBtn;
	private Button saveBtn;
	private Button button2;
	private FormLayout venteForm;
	private HorizontalLayout btnLayout;
	private TextField vaeFormName;
	private PopupDateField vaeFormDate;
	private VaeFieldGroup binder;
	private Table vaeTable;

	public VentesTab()
	{
		HorizontalLayout toolbar = new HorizontalLayout();
		setCaption(Messages.getString("VentesTab.0")); //$NON-NLS-1$
		setMargin(true);
		button = new Button(Messages.getString("VentesTab.1")); //$NON-NLS-1$
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Vente vae = new Vente();
				container.addEntity(vae);
				Logger.getLogger("main").info("inserted"); //$NON-NLS-1$ //$NON-NLS-2$
				// container.refresh();
			}
		});
		
		container = JPAContainerFactory.make(Vente.class, "ventes"); //$NON-NLS-1$
		HorizontalSplitPanel hpanel = new HorizontalSplitPanel();
		Label gap = new Label();
		gap.setHeight("1em"); //$NON-NLS-1$
		toolbar.addComponents(button);
		venteForm  = buildVaeForm();
		vaeTable = buildVaeTable();
		Panel formPanel = new Panel(venteForm);
		hpanel.setSplitPosition(75, Unit.PERCENTAGE);
		hpanel.addComponents(vaeTable,formPanel);
		this.addComponents(toolbar,gap, hpanel,btnLayout);
		
	}
	
	private Table buildVaeTable()
	{
		Table table = new Table(Messages.getString("VentesTab.7"));// test //$NON-NLS-1$
		table.setWidth("100%"); //$NON-NLS-1$
		table.setSelectable(true);
		table.setImmediate(true);
		table.setContainerDataSource(container);
		table.setVisibleColumns("date","name","location"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		table.setColumnHeader("name", Messages.getString("VentesTab.13")); //$NON-NLS-1$ //$NON-NLS-2$
		table.setColumnHeader("location", Messages.getString("VentesTab.15")); //$NON-NLS-1$ //$NON-NLS-2$
		table.addValueChangeListener(tableSelectLister);
		table.addGeneratedColumn("", new ColumnGenerator() {
			  @Override public Object generateCell(final Table source, final Object itemId, Object columnId) {
			 
			    Button removeButton = new Button("Supprimer");
			    Button accessButton = new Button("Fiche vente");
			    
			    accessButton.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub
						getUI().getNavigator().navigateTo(MainVenteView.NAME+"/"+source.getValue()); //$NON-NLS-1$
					}
				});
			 
			    removeButton.addClickListener(new ClickListener() {
			 
			      @Override public void buttonClick(ClickEvent event) {
			 
			        source.getContainerDataSource().removeItem(itemId);
			        venteForm.setEnabled(false);
			      }
			    });
			    
			    return new HorizontalLayout(accessButton, removeButton);
			  }
			});
		table.setSizeFull();
		return table;
	}
	
	private FormLayout buildVaeForm()
	{
		
		FormLayout form = new FormLayout();
		form.setCaption(Messages.getString("VentesTab.17")); //$NON-NLS-1$
		form.addStyleName("bordered"); // Custom style //$NON-NLS-1$
		form.setWidth("420px"); //$NON-NLS-1$
		form.setEnabled(false);
		
		
		btnLayout = new HorizontalLayout();
		saveBtn = new Button(Messages.getString("VentesTab.21")); //$NON-NLS-1$
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
				venteForm.setEnabled(false);
			}
		});
		binder = new VaeFieldGroup(new BeanItem<Vente>(new Vente()));
		binder.setFieldFactory(new VaeFieldFactory());
		form.removeAllComponents();
		form.addComponent(binder.buildAndBindWithValidator(Messages.getString("VentesTab.23"), "name",Vente.class)); //$NON-NLS-1$ //$NON-NLS-2$
		form.addComponent(binder.buildAndBind(Messages.getString("VentesTab.25"), "date")); //$NON-NLS-1$ //$NON-NLS-2$
		form.addComponent(binder.buildAndBind(Messages.getString("VentesTab.27"), "location")); //$NON-NLS-1$ //$NON-NLS-2$
		form.addComponent(btnLayout);
		form.addComponents(saveBtn);
		form.setMargin(true);
		return form;
	}
	

	
	private ValueChangeListener tableSelectLister = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			if (event.getProperty().getValue() != null) {
				EntityItem<Vente> venteItem =
			            container.getItem(event.getProperty().getValue());
				binder.setItemDataSource(venteItem);
		        venteForm.setEnabled(true);
			}
		}
	};
}
