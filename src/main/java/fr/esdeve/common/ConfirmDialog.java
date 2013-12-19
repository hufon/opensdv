package fr.esdeve.common;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Used to confirm events.
 * 
 * @author Patrick Oberg, Joonas Lehtinen, Tommi Laukkanen
 */
public final class ConfirmDialog extends Window implements Button.ClickListener {

	private static final long serialVersionUID = 1L;
	private static final int ONE_HUNDRED_PERCENT = 100;
	private static final int CONFIRMATION_DIALOG_WIDTH = 400;

	private final ConfirmationDialogCallback callback;
	private final Button okButton;
	private final Button cancelButton;

	/**
	 * * Constructor for configuring confirmation dialog. * @param caption the
	 * dialog caption. * @param question the question. * @param okLabel the Ok
	 * button label. * @param cancelLabel the cancel button label. * @param
	 * callback the callback.
	 */
	public ConfirmDialog(final String caption, final String question,
			final String okLabel, final String cancelLabel,
			final ConfirmationDialogCallback callback) {

		super(caption);
		VerticalLayout layout = new VerticalLayout();
		setWidth(CONFIRMATION_DIALOG_WIDTH, ConfirmDialog.UNITS_PIXELS);
		okButton = new Button(okLabel, this);
		cancelButton = new Button(cancelLabel, this);
		cancelButton.focus();
		setModal(true);

		this.callback = callback;

		Label label = new Label(question, Label.CONTENT_XHTML);

		if (question != null) {
			layout.addComponent(label);
		}

		final HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.addComponent(okButton);
		buttonLayout.addComponent(cancelButton);
		layout.addComponent(buttonLayout);
		setHeight(100,
				Unit.PIXELS);
		layout.setComponentAlignment(buttonLayout,
				Alignment.BOTTOM_CENTER);
		setContent(layout);
	}

	/** * Interface for confirmation dialog callbacks. */
	public interface ConfirmationDialogCallback {

		/** * The user response. * @param ok True if user clicked ok. */
		void response(boolean ok);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		close();
		callback.response(event.getSource() == okButton);

	}
}