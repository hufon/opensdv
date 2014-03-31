package fr.esdeve;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class OpenSdvUIProvider extends UIProvider {

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		return OpenSdvUI.class;
	}
	
	@Override
	public UI createInstance(UICreateEvent event) {
		try {
			OpenSdvUI ui = (OpenSdvUI) event.getUIClass().newInstance();
			ConfigurableApplicationContext uiCtx = new ClassPathXmlApplicationContext(
					event.getService().getDeploymentConfiguration().getInitParameters().getProperty("UIContextConfigLocation"));
			
			ui.setUiContext(uiCtx);
			return ui;
		} catch (InstantiationException e) {
			throw new RuntimeException("Could not instantiate UI class", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not access UI class", e);
		}
	}

}
