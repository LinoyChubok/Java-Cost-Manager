package CostManager.ViewModel;

import CostManager.Model.IModel;
import CostManager.View.IView;


public interface IViewModel {

    public abstract void setView(IView view);
    public abstract void setModel(IModel model);

}

