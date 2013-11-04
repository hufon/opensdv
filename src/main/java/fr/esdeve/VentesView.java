package fr.esdeve;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import fr.esdev.dao.GenericDao;
import fr.esdeve.model.Vente;

public class VentesView extends Panel implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button button;
	private Button button2;
	private Button removeBtn;
	private Table table;
	JPAContainer<Vente> container;
	GenericDao<Vente> venteDao;

	public static final String NAME = "";

	public VentesView() {
		venteDao = new GenericDao<Vente>();
	}

	@Override
	public void attach() {
		// TODO Auto-generated method stub
		super.attach();
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout toolbar = new HorizontalLayout();
		setCaption("Gestion des ventes");
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
		table = new Table("This is my Table");// test
		table.setWidth("100%");
		table.setSelectable(true);
		table.setImmediate(true);
		table.setContainerDataSource(container);
		Label gap = new Label();
		gap.setHeight("1em");
		toolbar.addComponents(button, button2);
		layout.addComponents(toolbar,gap, table, removeBtn);
		table.addValueChangeListener(tableSelectLister);
		setContent(layout);
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

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}

}
