package CostManager.ViewModel;

import CostManager.Model.IModel;
import CostManager.View.IView;

public class ViewModel implements IViewModel{

    private IModel model = null;
    private IView view = null;

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }
}
