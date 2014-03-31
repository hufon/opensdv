package fr.esdeve.presenters;

import fr.esdeve.views.IApplicationView;

public abstract class IApplicationPresenter implements Presenter {

	public abstract IApplicationView getDisplay();
}
