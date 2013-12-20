package fr.esdeve.presenters.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

import fr.esdeve.common.ConfirmDialog;
import fr.esdeve.common.ConfirmDialog.ConfirmationDialogCallback;
import fr.esdeve.dao.ArticleDAO;
import fr.esdeve.dao.VendorDAO;
import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.model.Article;
import fr.esdeve.model.Vendor;
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

	private Vendor selectedVendor;

	@Override
	public View getDisplay() {
		// TODO Auto-generated method stub
		return vendorTabView;
	}

	private void doDeleteVendor(Object itemId) {
		vendorDAO.remove(itemId);
		articleDAO.getContainer().refresh();
		selectedVendor = null;
		vendorTabView.getAddArticleBtn().setEnabled(false);
	}

	private void doSelectVendor(Object itemId) {
		EntityItem<Vendor> vendorItem = vendorDAO.getContainer()
				.getItem(itemId);
		vendorTabView.getBinder().setItemDataSource(vendorItem);
		vendorTabView.getVendorForm().setEnabled(true);
		selectedVendor = vendorItem.getEntity();
		vendorTabView.getAddArticleBtn().setEnabled(true);
		Filter filter = new Compare.Equal("vendor", selectedVendor);
		articleDAO.getContainer().removeAllContainerFilters();
		articleDAO.getContainer().addContainerFilter(filter);
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
		articleDAO.remove(itemId);
	}

	@PostConstruct
	@Override
	public void bind() {
		// TODO Auto-generated method stub
		vendorTabView.getVendorTable().addAttachListener(new AttachListener() {

			@Override
			public void attach(AttachEvent event) {
				((Table) event.getSource()).setContainerDataSource(vendorDAO
						.getContainer());
				vendorTabView.buildVendorTable();
			}
		});

		articleListView.getArticleTable().addAttachListener(
				new AttachListener() {

					@Override
					public void attach(AttachEvent event) {
						((Table) event.getSource())
								.setContainerDataSource(articleDAO
										.getContainer());
						articleListView.buildArticleTable();
					}
				});

		articleListView.setRemoveArticleClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				doRemoveArticle(event.getButton().getData());
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
				vendorTabView.getBinder().setItemDataSource(
						vendorDAO.add(newVendor));
				vendorTabView.getVendorForm().setEnabled(true);
			}
		});

		vendorTabView.getSaveVendorBtn().addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					vendorTabView.getBinder().commit();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	}

}
