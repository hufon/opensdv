package fr.esdeve.views;

import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;

public interface IArticleListView extends View {

	Table getArticleTable();

	void buildArticleTable();

	void setRemoveArticleClickListener(ClickListener removeArticleClickListener);

}
