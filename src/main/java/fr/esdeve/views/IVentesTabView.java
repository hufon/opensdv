package fr.esdeve.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickListener;

public interface IVentesTabView extends View {

	public Table getVaeTable();
	public void buildVaeTable();
	public Button getAddVenteBtn();
	public void setRemoveClickListener(ClickListener removeClickListener);
	
}
