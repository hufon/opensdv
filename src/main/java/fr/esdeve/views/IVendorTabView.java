package fr.esdeve.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table;

import fr.esdeve.forms.VaeFieldGroup;

public interface IVendorTabView extends View {

	Button getAddVendorBtn();

	Button getSaveVendorBtn();

	Button getAddArticleBtn();

	Table getVendorTable();

	void buildVendorTable();

	VaeFieldGroup getBinder();

	FormLayout getVendorForm();

	void setRemoveVendorClickListener(ClickListener removeVendorClickListener);

}
