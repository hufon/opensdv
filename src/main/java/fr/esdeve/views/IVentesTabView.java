package fr.esdeve.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickListener;

import fr.esdeve.forms.VaeFieldGroup;

public interface IVentesTabView extends View {

	public Table getVaeTable();
	public void buildVaeTable();
	public Button getAddVenteBtn();
	public void setRemoveClickListener(ClickListener removeClickListener);
	public VaeFieldGroup getBinder();
	public Button getSaveVenteBtn();
	public FormLayout getVaeEditform();
	void setViewVenteButtonClickListener(
	ClickListener viewVenteButtonClickListener);
}
