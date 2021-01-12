package CostManager.View;

import CostManager.Model.Category;
import CostManager.Model.CostItem;
import CostManager.ViewModel.IViewModel;

import java.util.ArrayList;
import java.util.Map;

public interface IView {

    public abstract void setViewModel(IViewModel vm);

    public abstract void showMessage(String text);
    public abstract void showCategories(ArrayList<Category> categories);
    public abstract void showCostItems(ArrayList<CostItem> items, ArrayList<Category> categories);
    public abstract void showReport(ArrayList<CostItem> items);

    //public abstract void showPieChart(Map map);

}
