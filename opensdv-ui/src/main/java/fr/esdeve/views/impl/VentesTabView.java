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
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

import fr.esdeve.Messages;
import fr.esdeve.forms.VaeFieldFactory;
import fr.esdeve.forms.VaeFieldGroup;
import fr.esdeve.model.Vente;
import fr.esdeve.views.IVentesTabView;

@SuppressWarnings("serial")
@Component
public class VentesTabView implements IVentesTabView {

	
	public Button getSaveVenteBtn() {
		return saveVenteBtn;
	}

	private VerticalLayout root;
	private Table vaeTable;
	private Button addVenteBtn;
	private Button saveVenteBtn;
	private Logger LOG = Logger.getGlobal();
	private ClickListener removeClickListener;
	private VaeFieldGroup binder;
	private FormLayout vaeEditform;
	private ClickListener viewVenteButtonClickListener;
	
	
	@Override
	public void setViewVenteButtonClickListener(
			ClickListener viewVenteButtonClickListener) {
		this.viewVenteButtonClickListener = viewVenteButtonClickListener;
	}

	@Override
	public FormLayout getVaeEditform() {
		return vaeEditform;
	}

	public void setRemoveClickListener(ClickListener removeClickListener) {
		this.removeClickListener = removeClickListener;
	}

	@Override
	public Table getVaeTable() {
		return vaeTable;
	}

	@Override
	public ComponentContainer getViewRoot() {
		return root;
	}

	@PostConstruct
	@Override
	public void initView() {
		LOG.info("Init VentesTabView");
		// TODO Auto-generated method stub
		root = new VerticalLayout();
		root.setCaption(Messages.getString("VentesTab.0"));
		root.setMargin(true);
		root.setSizeFull();
		HorizontalLayout toolbar = new HorizontalLayout();
		addVenteBtn = new Button(Messages.getString("VentesTab.1"));
		toolbar.addComponents(addVenteBtn);
		toolbar.setHeight("30px");
		vaeTable = new Table(Messages.getString("VentesTab.7"));
		vaeTable.setSizeFull();
		vaeTable.setSelectable(true);
		vaeTable.setImmediate(true);
		vaeTable.setId("vaeTable");
		HorizontalSplitPanel hpanel = new HorizontalSplitPanel();
		hpanel.setSizeFull();
		Panel formPanel = new Panel(buildVaeForm());
		hpanel.setSplitPosition(75, Unit.PERCENTAGE);
		hpanel.addComponents(vaeTable,formPanel);
		root.addComponents(toolbar, hpanel);
		root.setExpandRatio(hpanel, 1.0f);
	}
	
	@Override
	public VaeFieldGroup getBinder() {
		return binder;
	}

	@Override
	public Button getAddVenteBtn() {
		return addVenteBtn;
	}
	
	private FormLayout buildVaeForm()
	{
		vaeEditform = new FormLayout();
		vaeEditform.setCaption(Messages.getString("VentesTab.17")); //$NON-NLS-1$
		vaeEditform.addStyleName("bordered"); // Custom style //$NON-NLS-1$
		vaeEditform.setWidth("420px"); //$NON-NLS-1$
		vaeEditform.setEnabled(false);
		saveVenteBtn = new Button(Messages.getString("VentesTab.21")); //$NON-NLS-1$
		binder = new VaeFieldGroup(new BeanItem<Vente>(new Vente()));
		binder.setFieldFactory(new VaeFieldFactory());
		vaeEditform.removeAllComponents();
		vaeEditform.addComponent(binder.buildAndBindWithValidator(Messages.getString("VentesTab.23"), "name",Vente.class)); //$NON-NLS-1$ //$NON-NLS-2$
		vaeEditform.addComponent(binder.buildAndBind(Messages.getString("VentesTab.25"), "date")); //$NON-NLS-1$ //$NON-NLS-2$
		vaeEditform.addComponent(binder.buildAndBind(Messages.getString("VentesTab.27"), "location")); //$NON-NLS-1$ //$NON-NLS-2$
		vaeEditform.addComponents(saveVenteBtn);
		vaeEditform.setMargin(true);
		return vaeEditform;
	}

	public void buildVaeTable()
	{
		vaeTable.removeGeneratedColumn("actions");
		vaeTable.setVisibleColumns("date","name","location"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		vaeTable.setColumnHeader("name", Messages.getString("VentesTab.13")); //$NON-NLS-1$ //$NON-NLS-2$
		vaeTable.setColumnHeader("location", Messages.getString("VentesTab.15")); //$NON-NLS-1$ //$NON-NLS-2$
		vaeTable.addGeneratedColumn("actions", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Button removeButton = new Button("Supprimer");
				removeButton.setData(itemId);
				removeButton.addClickListener(removeClickListener);
				Button viewVenteButton = new Button("Fiche vente");
				viewVenteButton.setData(itemId);
				viewVenteButton.addClickListener(viewVenteButtonClickListener);
				return new HorizontalLayout(removeButton, viewVenteButton);
			}
		});
	}
	
}
