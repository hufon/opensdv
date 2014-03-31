package fr.esdeve.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table;

import fr.esdeve.forms.VaeFieldGroup;

public interface IArticleListView extends View {

	Table getArticleTable();

	void buildArticleTable();

	void setRemoveArticleClickListener(ClickListener removeArticleClickListener);

	FormLayout getArticleForm();

	VaeFieldGroup getBinder();

	Button getSaveArticleBtn();

	ComboBox getVenteCombo();

}
