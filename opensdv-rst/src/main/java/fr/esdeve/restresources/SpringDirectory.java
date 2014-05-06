package fr.esdeve.restresources;

import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Reference;
import org.restlet.resource.Directory;


public class SpringDirectory extends Directory {

	public SpringDirectory() {
		super(null, "clap://class/html/");
		this.setIndexName("index.html");
		// TODO Auto-generated constructor stub
	}
	
	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
		this.setContext(component.getContext().createChildContext());
	}

	private Component component;



}
