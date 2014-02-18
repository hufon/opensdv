package fr.esdeve.views.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

@SuppressWarnings("serial")
@Component("ArticleVenteListView")
@Scope("prototype")
public class ArticleVenteListView extends ArticleListView {
	
	@Override
	public void buildArticleTable() {
		articleTable.removeGeneratedColumn("actions");
		articleTable.setVisibleColumns("venteOrder", "id", "designation"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		articleTable.setColumnHeader("venteOrder", "N° passage");
		articleTable.setColumnHeader("id", "Numéro"); //$NON-NLS-1$ //$NON-NLS-2$
		articleTable.setColumnHeader("designation", "Designation"); //$NON-NLS-1$ //$NON-NLS-2$
		articleTable.addGeneratedColumn("actions", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				if (removeArticleClickListener!=null) {
					Button removeButton = new Button("Supprimer");
					removeButton.setData(itemId);
					removeButton.addClickListener(removeArticleClickListener);
					return new HorizontalLayout(removeButton);
				} else return new HorizontalLayout();
			}
		});
		articleTable.setSizeFull();
	}
}
