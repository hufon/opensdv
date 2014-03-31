package fr.esdeve.presenters;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import fr.esdeve.views.View;

public interface Presenter extends ApplicationListener<ApplicationEvent>, Serializable {
	View getDisplay();
	
	@PostConstruct
	public void bind();
}
