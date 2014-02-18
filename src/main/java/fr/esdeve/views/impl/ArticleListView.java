package fr.esdeve.views.impl;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import fr.esdeve.Messages;
import fr.esdeve.forms.VaeFieldFactory;
import fr.esdeve.forms.VaeFieldGroup;
import fr.esdeve.model.Article;
import fr.esdeve.model.Vendor;
import fr.esdeve.views.IArticleListView;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class ArticleListView implements IArticleListView {

	private HorizontalSplitPanel harticlepanel;
	protected Table articleTable;
	protected ClickListener removeArticleClickListener;
	private FormLayout articleForm;
	private Button saveArticleBtn;
	private ComboBox venteCombo;


	private VaeFieldGroup binder;

	@Override
	public Button getSaveArticleBtn() {
		return saveArticleBtn;
	}
	
	@Override
	public VaeFieldGroup getBinder() {
		return binder;
	}

	@Override
	public FormLayout getArticleForm() {
		return articleForm;
	}

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
		Panel formPanel = new Panel(buildarticleForm());
		formPanel.setCaption("Edition article");
		harticlepanel.addComponent(formPanel);
	}

	private FormLayout buildarticleForm() {
		// TODO Auto-generated method stub
		articleForm = new FormLayout();
		articleForm.addStyleName("bordered"); // Custom style //$NON-NLS-1$
		//articleForm.setWidth("420px"); //$NON-NLS-1$
		articleForm.setEnabled(false);
		articleForm.setMargin(true);

		HorizontalLayout btnLayout = new HorizontalLayout();
	    saveArticleBtn = new Button(Messages.getString("VentesTab.21"));
	    btnLayout.addComponent(saveArticleBtn);//$NON-NLS-1$
		binder = new VaeFieldGroup(new BeanItem<Article>(new Article()));
		binder.setFieldFactory(new VaeFieldFactory());
		articleForm.removeAllComponents();
		articleForm.addComponent(binder.buildAndBind("Numéro", "id")); //$NON-NLS-1$ //$NON-NLS-2$
		articleForm.addComponent(binder.buildAndBind("Désignation", "designation")); //$NON-NLS-1$ //$NON-NLS-2$
		articleForm.addComponent(binder.buildAndBind("Mise à prix (€)", "initialPrice"));
		articleForm.addComponent(binder.buildAndBind("Prix de reserve (€)", "minimumPrice"));
		articleForm.addComponent(binder.buildAndBind("Prix d'adjudication(€) ", "sellingPrice"));
		articleForm.addComponent(binder.buildAndBind("Retiré ?", "retired"));
		binder.buildAndBind("venteOrder");
		venteCombo = (ComboBox) binder.buildAndBind("Vente", "vente");
		articleForm.addComponent(venteCombo);
		articleForm.addComponents(btnLayout);
		return articleForm;
	}

	@Override
	public ComboBox getVenteCombo() {
		return venteCombo;
	}

	@Override
	public Table getArticleTable() {
		return articleTable;
	}
	
	@Override
	public void buildArticleTable() {
		articleTable.removeGeneratedColumn("actions");
		articleTable.setVisibleColumns("id", "designation", "vente"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		articleTable.setColumnHeader("id", "Numéro"); //$NON-NLS-1$ //$NON-NLS-2$
		articleTable.setColumnHeader("designation", "Designation"); //$NON-NLS-1$ //$NON-NLS-2$
		articleTable.setColumnHeader("vente", "Vente");
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
