package CostManager.ViewModel;

import CostManager.Model.IModel;
import CostManager.View.IView;


public interface IViewModel {

    public void setView(IView view);
    public void setModel(IModel model);

}

