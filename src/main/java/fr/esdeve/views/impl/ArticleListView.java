package fr.esdeve.views.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import fr.esdeve.views.IArticleListView;

@SuppressWarnings("serial")
@Component
public class ArticleListView implements IArticleListView {

	private HorizontalSplitPanel harticlepanel;
	private Table articleTable;
	protected ClickListener removeArticleClickListener;
	
	@Override
	public void setRemoveArticleClickListener(
			ClickListener removeArticleClickListener) {
		this.removeArticleClickListener = removeArticleClickListener;
	}

	@Override
	public ComponentContainer getViewRoot() {
		// TODO Auto-generated method stub
		return harticlepanel;
	}

	@PostConstruct
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		harticlepanel = new HorizontalSplitPanel();
		harticlepanel.setSplitPosition(50, Unit.PERCENTAGE);
		articleTable = new Table();
		articleTable.setWidth("100%"); //$NON-NLS-1$
		articleTable.setSelectable(true);
		articleTable.setImmediate(true);
		harticlepanel.addComponent(articleTable);
	}

	@Override
	public Table getArticleTable() {
		return articleTable;
	}
	
	@Override
	public void buildArticleTable() {
		articleTable.removeGeneratedColumn("actions");
		articleTable.setVisibleColumns("id", "designation"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		articleTable.setColumnHeader("id", "Numéro"); //$NON-NLS-1$ //$NON-NLS-2$
		articleTable.setColumnHeader("designation", "Designation"); //$NON-NLS-1$ //$NON-NLS-2$
		articleTable.addGeneratedColumn("actions", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Button removeButton = new Button("Supprimer");
				removeButton.setData(itemId);
				removeButton.addClickListener(removeArticleClickListener);
				return new HorizontalLayout(removeButton);
			}
		});
		articleTable.setSizeFull();
	}
	
	

}
