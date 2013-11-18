package fr.esdeve.forms;

import java.util.Date;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;

public class VaeFieldFactory extends DefaultFieldGroupFieldFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5929569925563006330L;
	
	@Override
	public <T extends Field> T createField(Class<?> type, Class<T> fieldType) {
		// TODO Auto-generated method stub
		if (type.isAssignableFrom(Date.class))
		{
			PopupDateField field = new PopupDateField();
			field.setResolution(Resolution.MINUTE);
			return (T) field;
		} else {
			T field = super.createField(type, fieldType); 
			field.setWidth("100%");
			return field;
		}
	}

}
