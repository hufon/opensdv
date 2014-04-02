package fr.esdeve.forms;

import java.util.Date;
import java.util.Locale;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.TextField;

import fr.esdeve.model.Vente;

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
		}
		else if (type.isAssignableFrom(Vente.class))
		{
			ComboBox field = new ComboBox();
			field.setConverter(Vente.class);
			field.setItemCaptionMode(ItemCaptionMode.ITEM);
			return (T) field;
		}
		else if (type.isAssignableFrom(Integer.class))
		{
			TextField field = new TextField();
			return (T)field;
		}
		else {
			T field = super.createField(type, fieldType); 
			field.setWidth("100%");
			return field;
		}
	}

}
