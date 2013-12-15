package fr.esdeve.views;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import fr.esdeve.MainVenteView;
import fr.esdeve.Messages;
import fr.esdeve.forms.VaeFieldFactory;
import fr.esdeve.forms.VaeFieldGroup;
import fr.esdeve.model.Vente;
import fr.esdeve.presenters.VentesTabPresenter;

public class VentesTabView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button button;
	private Button saveBtn;
	private FormLayout venteForm;
	private HorizontalLayout btnLayout;
	private VaeFieldGroup binder;
	private Table vaeTable;
	VentesTabPresenter listener;

	public VentesTabView()
	{
		HorizontalLayout toolbar = new HorizontalLayout();
		setCaption(Messages.getString("VentesTab.0")); //$NON-NLS-1$
		setMargin(true);
		button = new Button(Messages.getString("VentesTab.1")); //$NON-NLS-1$
		button.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.addVente();
			}
		});
		HorizontalSplitPanel hpanel = new HorizontalSplitPanel();
		Label gap = new Label();
		gap.setHeight("1em"); //$NON-NLS-1$
		toolbar.addComponents(button);
		venteForm  = buildVaeForm();
		vaeTable = new Table(Messages.getString("VentesTab.7"));// test //$NON-NLS-1$
		vaeTable.setWidth("100%"); //$NON-NLS-1$
		vaeTable.setSelectable(true);
		vaeTable.setImmediate(true);
		vaeTable.setSizeFull();
		Panel formPanel = new Panel(venteForm);
		hpanel.setSplitPosition(75, Unit.PERCENTAGE);
		hpanel.addComponents(vaeTable,formPanel);
		this.addComponents(toolbar,gap, hpanel,btnLayout);
		
	}
	
    public void addListener(VentesTabPresenter _listener) {
        this.listener = _listener;
    }
    
    public void setContainer(JPAContainer<Vente> container)
    {
    	buildVaeTable( container);
    }
	
	private void buildVaeTable(JPAContainer<Vente> container)
	{
		vaeTable.setContainerDataSource(container);
		vaeTable.setVisibleColumns("date","name","location"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		vaeTable.setColumnHeader("name", Messages.getString("VentesTab.13")); //$NON-NLS-1$ //$NON-NLS-2$
		vaeTable.setColumnHeader("location", Messages.getString("VentesTab.15")); //$NON-NLS-1$ //$NON-NLS-2$
		vaeTable.addValueChangeListener(tableSelectLister);
		vaeTable.addGeneratedColumn("", new ColumnGenerator() {
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
			    	  listener.doDeleteVente(itemId);
			    	  venteForm.setEnabled(false);
			      }
			    });
			    
			    return new HorizontalLayout(accessButton, removeButton);
			  }
			});
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
	
	public void setFormSource(EntityItem<Vente> item)
	{
		binder.setItemDataSource(item);
		venteForm.setEnabled(true);
	}

	
	private ValueChangeListener tableSelectLister = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			listener.selectVente(event.getProperty().getValue());
		}
	};
}
