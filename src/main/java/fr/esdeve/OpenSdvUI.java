package fr.esdeve;

import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import fr.esdeve.presenters.IApplicationPresenter;

@Theme("mytheme")
@SuppressWarnings("serial")
@Title("OpenSDV")
public class OpenSdvUI extends UI
{
    private transient ApplicationContext uiContext;
    private Logger LOG = Logger.getGlobal();
	
    @Override
    protected void init(VaadinRequest request) {
    	LOG.info("INIT : OpenSdvUI");
    	IApplicationPresenter appPresenter = (IApplicationPresenter) uiContext.getBean("applicationPresenter");    	
    	setContent(appPresenter.getDisplay().getViewRoot());
    }
    
    @Override
    public void attach(){
    	super.attach();
    	LOG.info("ATTACH : OpenSdvUI");
    	
    }
    
    public void setUiContext(ApplicationContext uiContext) {
		this.uiContext = uiContext;
	}

}
