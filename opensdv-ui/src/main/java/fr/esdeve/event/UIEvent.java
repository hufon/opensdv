package fr.esdeve.event;

import org.springframework.context.ApplicationEvent;

import fr.esdeve.presenters.Presenter;

public class UIEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;

	private UIEventTypes eventType;
	
	
	public <T extends Presenter> UIEvent(T source, UIEventTypes eventType) {
		super(source);
		this.eventType = eventType;
	}
	
	public UIEventTypes getEventType() {
		return eventType;
	}
}
