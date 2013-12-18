package fr.esdeve.views;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import com.vaadin.ui.ComponentContainer;

public interface View extends Serializable {
	ComponentContainer getViewRoot(); 
	
	@PostConstruct
	void initView();
}
