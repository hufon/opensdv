package fr.esdeve;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@Component("OpenSdvUI")
@Scope("prototype")
@SuppressWarnings("serial")
public class OpenSdvUI extends UI
{
    @Autowired
    private transient ApplicationContext applicationContext;
	
    @Override
    protected void init(VaadinRequest request) {
    	setSizeFull();
    	DiscoveryNavigator navigator = new DiscoveryNavigator(this, this);
    }

}
