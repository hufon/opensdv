package fr.esdeve.presenters.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

import fr.esdeve.common.ConfirmDialog;
import fr.esdeve.common.ConfirmDialog.ConfirmationDialogCallback;
import fr.esdeve.dao.ArticleDAO;
import fr.esdeve.dao.VendorDAO;
import fr.esdeve.dao.VenteDAO;
import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.model.Article;
import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vente;
import fr.esdeve.presenters.IApplicationPresenter;
import fr.esdeve.presenters.IVendorTabPresenter;
import fr.esdeve.views.IArticleListView;
import fr.esdeve.views.IVendorTabView;
import fr.esdeve.views.IVentesTabView;
import fr.esdeve.views.View;
import fr.esdeve.views.impl.VendorTabView;

@SuppressWarnings("serial")
@Component
public class VendorTabPresenter implements IVendorTabPresenter {

	@Autowired
	private IVendorTabView vendorTabView;

	@Autowired
	private IArticleListView articleListView;

	@Autowired
	private VendorDAO vendorDAO;

	@Autowired
	private ArticleDAO articleDAO;

	@Autowired
	private VenteDAO venteDAO;
	
	private Vendor selectedVendor;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	private BeanItemContainer<Vendor> container;
	private BeanItemContainer<Article> articleContainer;
	
	private Logger LOG = Logger.getGlobal();
	
	@Override
	public View getDisplay() {
		// TODO Auto-generated method stub
		return vendorTabView;
	}

	private void doDeleteVendor(Object itemId) {
		
		Vendor item = (Vendor)itemId;
		vendorDAO.remove(item.getId());
		container.removeItem(itemId);
		selectedVendor = null;
		vendorTabView.getAddArticleBtn().setEnabled(false);
	}

	private void doSelectVendor(Object itemId) {
		BeanItem<Vendor> vendorItem = container
				.getItem(itemId);
		vendorTabView.getBinder().setItemDataSource(vendorItem);
		vendorTabView.getVendorForm().setEnabled(true);
		selectedVendor = vendorItem.getBean();
		vendorTabView.getAddArticleBtn().setEnabled(true);
		Filter filter = new Compare.Equal("vendor", selectedVendor);
		articleContainer.removeAllContainerFilters();
		articleContainer.addContainerFilter(filter);
	}
	
	private void doSelectArticle(Object itemId) {
		// TODO Auto-generated method stub
		BeanItem<Article> articleItem = articleContainer
				.getItem(itemId);
		articleListView.getBinder().setItemDataSource(articleItem);
		articleListView.getArticleForm().setEnabled(true);
		/*articleListView.getVenteCombo().setContainerDataSource(venteDAO.getContainer());
		articleListView.getVenteCombo().setConverter(new SingleSelectConverterCorrected<Vente>(articleListView.getVenteCombo()));*/
	}

	private void doAddNewArticle() {
		if (selectedVendor != null) {
			Article newArticle = new Article();
			newArticle.setDesignation("Nouvel article");
			newArticle.setVendor(selectedVendor);
			articleDAO.add(newArticle);
		}
	}

	private void doRemoveArticle(Object itemId) {
		//articleDAO.remove(itemId);
	}
	

    public Article getArticle(FieldGroup binder) {
        return ((BeanItem<Article>)binder.getItemDataSource()).getBean();
    }

	@Override
	public void bind() {
		container = new BeanItemContainer<Vendor>(Vendor.class);
		articleContainer = new BeanItemContainer<Article>(Article.class);
		container.addAll(vendorDAO.list());
		// TODO Auto-generated method stub
		vendorTabView.getVendorTable().addAttachListener(new AttachListener() {

			@Override
			public void attach(AttachEvent event) {
				((Table) event.getSource()).setContainerDataSource(container);
				vendorTabView.buildVendorTable();
			}
		});

		articleListView.getArticleTable().addAttachListener(
				new AttachListener() {

					@Override
					public void attach(AttachEvent event) {
						((Table) event.getSource())
								.setContainerDataSource(articleContainer);
						articleListView.buildArticleTable();
					}
				});

		articleListView.setRemoveArticleClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				doRemoveArticle(event.getButton().getData());
			}
		});
		
		articleListView.getArticleTable().addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					doSelectArticle(event.getProperty().getValue());
				}
			}

		});
		
		articleListView.getSaveArticleBtn().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					articleListView.getBinder().commit();
					applicationEventPublisher.publishEvent(
							new UIEvent(VendorTabPresenter.this, UIEventTypes.ITEM_SAVED));
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

		vendorTabView.getVendorTable().addValueChangeListener(
				new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						if (event.getProperty().getValue() != null) {
							doSelectVendor(event.getProperty().getValue());
						}
					}
				});

		vendorTabView.getAddArticleBtn().addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				doAddNewArticle();
			}
		});

		vendorTabView.getAddVendorBtn().addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Vendor newVendor = new Vendor();
				newVendor.setName("Nouveau vendeur");
				vendorTabView.getBinder().setItemDataSource(new BeanItem<Vendor>(
						vendorDAO.addBean(newVendor)));
				container.addBean(newVendor);
				vendorTabView.getVendorForm().setEnabled(true);
			}
		});

		vendorTabView.getSaveVendorBtn().addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					vendorTabView.getBinder().commit();
					applicationEventPublisher.publishEvent(
							new UIEvent(VendorTabPresenter.this, UIEventTypes.ITEM_SAVED));
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		articleListView.getBinder().addCommitHandler(new CommitHandler() {
			
			@Override
			public void preCommit(CommitEvent commitEvent) throws CommitException {
				// TODO Auto-generated method stub
				Article articleToCommit  = getArticle(commitEvent.getFieldBinder());
				Field<String> orderField=(Field<String>) commitEvent.getFieldBinder().getField("venteOrder");
				Field<String> venteField=(Field<String>) commitEvent.getFieldBinder().getField("vente");
				if (venteField.getValue() != null)
				{
					Vente linkedVente = venteDAO.get(venteField.getValue());
					LOG.info("Link to a vente : "+linkedVente.getId());
					if (orderField.getValue() == null || orderField.getValue().equals("0"))
					{
						orderField.setValue(articleDAO.getNextArticleOrder(articleToCommit,linkedVente).toString());
					}
				} else
				{
					orderField.setValue("0");
				}
			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {
				// TODO Auto-generated method stub
				
			}
			
		});

		vendorTabView.setRemoveVendorClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				// TODO Auto-generated method stub
				ConfirmDialog confirm = new ConfirmDialog("Supprimer la vente",
						"êtes vous sur?", "Oui", "Non",
						new ConfirmationDialogCallback() {

							@Override
							public void response(boolean ok) {
								if (ok == true) {
									doDeleteVendor(event.getButton().getData());
									vendorTabView.getVendorForm().setEnabled(
											false);
								}
							}
						});
				vendorTabView.getViewRoot().getUI().addWindow(confirm);
			}
		});
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof UIEvent) {
			if (((UIEvent) event).getEventType().equals(
					UIEventTypes.APPLICATION_VIEW_ATTACHED)) {
				handleApplicationViewAttached((UIEvent) event);
			}
		}

	}

	private void handleApplicationViewAttached(UIEvent event) {
		vendorTabView.getArticleListContainer().addComponent(
				articleListView.getViewRoot());
		((IApplicationPresenter) event.getSource()).getDisplay()
				.getApplicationTabContainer()
				.addTab(vendorTabView.getViewRoot());
		bind();
	}

}
