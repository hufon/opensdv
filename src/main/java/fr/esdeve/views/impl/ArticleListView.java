package fr.esdeve.views.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;

import fr.esdeve.views.IArticleListView;

@SuppressWarnings("serial")
@Component
public class ArticleListView implements IArticleListView {

	private HorizontalSplitPanel harticlepanel;
	private Table articleTable;
	
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

}
