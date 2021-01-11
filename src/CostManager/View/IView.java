package CostManager.View;

import CostManager.Model.CostItem;
import CostManager.ViewModel.IViewModel;

import java.util.ArrayList;
import java.util.Map;

public interface IView {

    public abstract void setViewModel(IViewModel vm);

    public abstract void showMessage(String text);
    public abstract void showItems(ArrayList<CostItem> items);

    //public abstract void showPieChart(Map map);

}
