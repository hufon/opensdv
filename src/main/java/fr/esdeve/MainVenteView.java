package fr.esdeve;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import fr.esdeve.views.VentesView;

public class MainVenteView extends Panel implements View {

	private Button button;
	private Button button2;
	private Long venteId;

	public static final String NAME = "vente";

	public MainVenteView() {
		VerticalLayout layout = new VerticalLayout();
		setCaption("Gestion de la vente : ");
		button = new Button("Retour choix de la vente");
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getUI().getNavigator().navigateTo(VentesView.NAME);
			}
		});

		layout.addComponents(button);
		setContent(layout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		if(event.getParameters() != null){
			venteId = Long.parseLong(event.getParameters());
			setCaption("Vente : "+venteId);
		}
	}

}
