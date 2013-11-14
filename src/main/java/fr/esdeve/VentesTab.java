package fr.esdeve;

import java.util.Date;
import java.util.logging.Logger;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import fr.esdeve.model.Vente;

public class VentesTab extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Table table;
	private Button button;
	private JPAContainer<Vente> container;
	private Button removeBtn;
	private Button saveBtn;
	private Button button2;
	private FormLayout venteForm;
	private HorizontalLayout btnLayout;
	private TextField vaeFormName;
	private PopupDateField vaeFormDate;

	public VentesTab()
	{
		HorizontalLayout toolbar = new HorizontalLayout();
		setCaption("Gestion des ventes");
		setMargin(true);
		button = new Button("Ajouter une nouvelle vente");
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Vente vae = new Vente();
				vae.setName("Nouvelle vente");
				vae.setDate(new Date());
				container.addEntity(vae);
				Logger.getLogger("main").info("inserted");
				// container.refresh();
			}
		});
		button2 = new Button("Accèder à cette vente");
		button2.setEnabled(false);
		button2.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainVenteView.NAME+"/"+table.getValue());
			}
		});
		
		container = JPAContainerFactory.make(Vente.class, "ventes");
		table = new Table("Liste des ventes");// test
		table.setWidth("100%");
		table.setSelectable(true);
		table.setImmediate(true);
		table.setContainerDataSource(container);
		Label gap = new Label();
		gap.setHeight("1em");
		toolbar.addComponents(button, button2);
		venteForm  = new FormLayout();
		venteForm.setCaption("Vente Editor");
		venteForm.addStyleName("bordered"); // Custom style
		venteForm.setWidth("420px");
		venteForm.setEnabled(false);
		vaeFormName = new TextField("Nom de la vente");
		vaeFormDate = new PopupDateField("Date de la vente");
		vaeFormDate.setResolution(Resolution.MINUTE);
		btnLayout = new HorizontalLayout();
		venteForm.addComponents(vaeFormName,vaeFormDate,btnLayout);
		
		this.addComponents(toolbar,gap, table,venteForm,btnLayout);
		table.addValueChangeListener(tableSelectLister);
	}
	
	private ValueChangeListener tableSelectLister = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			if (table.getValue() != null) {
				button2.setCaption("Accèder à la vente : " + table.getValue());
				button2.setEnabled(true);
				final FieldFactory fieldFactory = new FieldFactory();
				EntityItem<Vente> venteItem =
			            container.getItem(event.getProperty().getValue());
				final FieldGroup binder = new FieldGroup(venteItem);
				binder.bind(vaeFormName, "name");
				binder.bind(vaeFormDate, "date");
		        venteForm.setEnabled(true);
		        removeBtn = new Button("Supprimer...");
				removeBtn.setEnabled(true);
				removeBtn.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub
						container.removeItem(table.getValue());
						venteForm.setEnabled(false);
					}
				});
				saveBtn = new Button("Enregistrer");
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
				btnLayout.removeAllComponents();
				btnLayout.addComponents(removeBtn,saveBtn);
			}
		}
	};
}
