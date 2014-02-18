package fr.esdeve.presenters.impl;

import java.text.DateFormat;
import java.util.Locale;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.TableDragMode;

import fr.esdeve.dao.ArticleDAO;
import fr.esdeve.event.AppEvent;
import fr.esdeve.event.UIEvent;
import fr.esdeve.event.UIEventTypes;
import fr.esdeve.model.Vente;
import fr.esdeve.presenters.IVenteDetailsPresenter;
import fr.esdeve.views.IArticleListView;
import fr.esdeve.views.IVenteDetailsView;
import fr.esdeve.views.View;

@SuppressWarnings("serial")
@Component
public class VenteDetailsPresenter implements IVenteDetailsPresenter {

	@Autowired
	IVenteDetailsView venteDetailsView;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	@Qualifier("ArticleVenteListView")
	private IArticleListView articleListView;
	
	@Autowired
	private ArticleDAO articleDAO;
	
	private Vente currentVente;
	
	private Logger LOG = Logger.getGlobal();
	
	@Override
	public View getDisplay() {
		// TODO Auto-generated method stub
		return venteDetailsView;
	}

	@PostConstruct
	@Override
	public void bind() {
		// TODO Auto-generated method stub
		venteDetailsView.getReturnListButton().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				applicationEventPublisher.publishEvent(
						new UIEvent(VenteDetailsPresenter.this, UIEventTypes.LISTVENTES_DISPLAY));
			}
		});
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AppEvent) {
			if (((((AppEvent) event)).getSource() instanceof ApplicationPresenter) &&
			(((AppEvent) event)).getEventType().equals(UIEventTypes.VENTE_DISPLAY))
			{
				currentVente = ((AppEvent) event).getData();
				DateFormat shortDateFormatFR = DateFormat.getDateTimeInstance(
						DateFormat.SHORT,
						DateFormat.SHORT, new Locale("FR","fr"));
				getDisplay().getViewRoot().setCaption("Vente du "+shortDateFormatFR.format(currentVente.getDate()));
				((ApplicationPresenter)((AppEvent) event).getSource()).getDisplay().getVentesLayout().removeAllComponents();
				((ApplicationPresenter)((AppEvent) event).getSource()).getDisplay().getVentesLayout().addComponent(venteDetailsView.getViewRoot());
			}
		}
		if (event instanceof UIEvent) {
			if (((UIEvent) event).getEventType().equals(
					UIEventTypes.APPLICATION_VIEW_ATTACHED)) {
				handleApplicationViewAttached((UIEvent) event);
			}
		}

	}

	private void handleApplicationViewAttached(UIEvent event) {
		venteDetailsView.getArticleListContainer().addComponent(
				articleListView.getViewRoot());
		articleListView.getArticleTable().addAttachListener(
				new AttachListener() {

					@Override
					public void attach(AttachEvent event) {
						LOG.info("Attaching article table...");
						((Table) event.getSource())
								.setContainerDataSource(articleDAO
										.getArticleVentecontainer());
						Filter filter = new Compare.Equal("vente", currentVente);
						articleDAO.getArticleVentecontainer().removeAllContainerFilters();
						articleDAO.getArticleVentecontainer().addContainerFilter(filter);
						articleDAO.getArticleVentecontainer().sort(new String[]{"venteOrder"}, new boolean[]{true});
						articleListView.buildArticleTable();
						articleDAO.getArticleVentecontainer().refresh();
						articleListView.getArticleTable().setDragMode(TableDragMode.ROW);
						articleListView.getArticleTable().setDropHandler(new DropHandler() {
							
							@Override
							public AcceptCriterion getAcceptCriterion() {
								// TODO Auto-generated method stub
								return new And(new SourceIs(articleListView.getArticleTable()), AcceptItem.ALL);
							}
							
							@Override
							public void drop(DragAndDropEvent dropEvent) {
								// TODO Auto-generated method stub
								LOG.info("DROP!!!");
								DataBoundTransferable t = (DataBoundTransferable)dropEvent.getTransferable();
								
								AbstractSelectTargetDetails dropData = ((AbstractSelectTargetDetails) dropEvent.getTargetDetails());
								LOG.info(t.getItemId()+ " -> " + dropData.getItemIdOver().toString());
								
							}
						});
					}
				});
		
	}

}
