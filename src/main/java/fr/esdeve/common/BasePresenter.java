package fr.esdeve.common;

public abstract class BasePresenter implements BaseViewListener {
	protected BaseView view = null;
    
    public void setView(BaseView view){
            this.view = view;
            view.addListener(this);
    }
}
