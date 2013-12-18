package fr.esdeve;

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
	
    @Override
    protected void init(VaadinRequest request) {
    	IApplicationPresenter appPresenter = (IApplicationPresenter) uiContext.getBean("applicationPresenter");    	
    	setContent(appPresenter.getDisplay().getViewRoot());
    }
    
    public void setUiContext(ApplicationContext uiContext) {
		this.uiContext = uiContext;
	}

}
