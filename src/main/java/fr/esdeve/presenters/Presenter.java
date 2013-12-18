package fr.esdeve.presenters;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import fr.esdeve.views.View;

public interface Presenter extends ApplicationListener<ApplicationEvent> {
	View getDisplay();
	
	@PostConstruct
	public void bind();
}
