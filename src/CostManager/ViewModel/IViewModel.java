package CostManager.ViewModel;

import CostManager.Model.*;
import CostManager.View.*;

public interface IViewModel {

    public abstract void setView(IView view);
    public abstract void setModel(IModel model);

    public abstract void addCostItem(CostItem item);

}

