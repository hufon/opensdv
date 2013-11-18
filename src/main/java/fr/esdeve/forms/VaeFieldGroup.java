package fr.esdeve.forms;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Field;

public class VaeFieldGroup extends FieldGroup {
	

    public VaeFieldGroup(Item itemDataSource) {
    	super(itemDataSource);
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 985087519460434165L;

	public Field<?> buildAndBindWithValidator(String caption, Object propertyId, Class<?> validationClass)
			throws BindException {
		Field<?> f = super.buildAndBind(caption, propertyId);
		f.addValidator(new BeanValidator(validationClass,propertyId.toString()));
		return f;
	}

}
