package fr.esdeve;

import java.util.Date;
import java.util.logging.Logger;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
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
	private Button button2;

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
		removeBtn = new Button("Supprimer...");
		removeBtn.setEnabled(false);
		removeBtn.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				container.removeItem(table.getValue());
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
		this.addComponents(toolbar,gap, table, removeBtn);
		table.addValueChangeListener(tableSelectLister);
	}
	
	private ValueChangeListener tableSelectLister = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			if (table.getValue() != null) {
				button2.setCaption("Accèder à la vente : " + table.getValue());
				button2.setEnabled(true);
				removeBtn.setEnabled(true);
			}
		}
	};
}
