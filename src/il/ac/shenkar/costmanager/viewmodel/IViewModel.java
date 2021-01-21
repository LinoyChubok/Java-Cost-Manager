package il.ac.shenkar.costmanager.viewmodel;

import il.ac.shenkar.costmanager.model.Category;
import il.ac.shenkar.costmanager.model.CostItem;
import il.ac.shenkar.costmanager.model.Currency;
import il.ac.shenkar.costmanager.model.IModel;
import il.ac.shenkar.costmanager.view.IView;

import java.sql.Date;

public interface IViewModel {

    /**
     * Set the view.
     * @param view Variable that holding the view.
     */
    public abstract void setView(IView view);

    /**
     * Set the model.
     * @param model Variable that holding the model.
     */
    public abstract void setModel(IModel model);

    /**
     * Add new category to the database.
     * @param category Defined at Category class. (constructor without id)
     */
    public abstract void addCategory(Category category);

    /**
     * Update specific category from the database.
     * @param category Defined at Category class.  (constructor with id)
     */
    public abstract void updateCategory(Category category);

    /**
     * Delete specific category from the database.
     * @param id Category id (int).
     */
    public abstract void deleteCategory(int id);

    /**
     * Get list of categories from the database.
     *
     */
    public abstract void getAllCategories();

    /**
     * Add new cost item to the database.
     * @param item Defined at CostItem class. (constructor without id)
     */
    public abstract void addCostItem(CostItem item);

    /**
     * Update specific cost item from the database.
     * @param item Defined at CostItem class. (constructor with id)
     */
    public abstract void updateCostItem(CostItem item);

    /**
     * Delete specific cost item from the database.
     * @param id CostItem id (int).
     */
    public abstract void deleteCostItem(int id);

    /**
     * Get list of cost items from the database.
     *
     */
    public abstract void getAllCostItems();

    /**
     * Get list of cost items between dates from the database.
     *
     */
    public abstract void getReportSummary(Date fromDate, Date toDate);

    /**
     * Get summary of cost items between dates from the database summed by category,
     * with rates according to the currency param.
     * @param fromDate The starting date of the pie chart summary.
     * @param toDate   The ending date of the pie chart summary.
     * @param currency The currency type (used for rates).
     */
    public abstract void getPieChartSummary(Date fromDate, Date toDate, Currency currency);

    /**
     * Shut down database, any cleans up on exit will be implemented here.
     *
     */
    public abstract void shutdownDB();
}

