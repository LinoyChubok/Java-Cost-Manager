package il.ac.shenkar.costmanager.view;

import il.ac.shenkar.costmanager.model.Category;
import il.ac.shenkar.costmanager.model.CostItem;
import il.ac.shenkar.costmanager.viewmodel.IViewModel;

import java.util.List;
import java.util.Map;

public interface IView {

    /**
     * Setting the view model.
     * @param vm Variable that holding the view model.
     */
    public abstract void setViewModel(IViewModel vm);

    /**
     * Handle the display of any message at our view.
     * @param text The text message, string representation.
     */
    public abstract void showMessage(String text);

    /**
     * Handle the display of showing categories.
     * @param categories List of categories.
     */
    public abstract void showCategories(List<Category> categories);

    /**
     * Handle the display of showing cost items.
     * @param items List of cost items.
     */
    public abstract void showCostItems(List<CostItem> items);

    /**
     * Handle the display of showing the report of cost items (between dates).
     * @param items List of cost items.
     */
    public abstract void showReportSummary(List<CostItem> items);

    /**
     * Handle the display of showing the pie chart.
     *
     */
    public abstract void showPieChartSummary(Map<Category, Double> pieChartSummary);
}
