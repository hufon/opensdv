package fr.esdeve.event;

import java.util.Map;

import fr.esdeve.presenters.Presenter;

public class AppEvent extends UIEvent {
	
	private static final long serialVersionUID = 1L;
	
	private Object data;
	private Map<String, Object> dataMap;

	public <T extends Presenter> AppEvent(Object data,T source, UIEventTypes eventType) {
		super(source, eventType);
		this.data = data;
	}
	
	public <T extends Presenter> AppEvent(Map<String, Object> dataMap, T source, UIEventTypes eventType) {
		super(source, eventType);
		this.dataMap = dataMap;
	}

	@SuppressWarnings("unchecked")
	public <X> X getData() {
		return (X) data;
	}

	@SuppressWarnings("unchecked")
	public <X> X getData(String key) {
		if (dataMap == null)
			return null;
		return (X) dataMap.get(key);
	}
}