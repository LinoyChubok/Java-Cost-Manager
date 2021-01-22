package il.ac.shenkar.costmanager.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface IModel {

    /**
     * Database implementation
     *
     * createTables - create tables on the database.
     * shutdownDB -  ensures the database connection shuts down safely, any cleans up will be here.
     */
    public abstract void createTables() throws CostManagerException;
    public abstract void shutdownDB() throws CostManagerException;

    /**
     * Categories queries implementation
     *
     * addCategory - add new category to the database.
     * updateCategory - update specific category from the database.
     * deleteCategory - delete specific category from the database.
     * getAllCategories - get list of categories from the database.
     */
    public abstract void addCategory(Category category) throws CostManagerException;
    public abstract void updateCategory(Category category) throws CostManagerException;
    public abstract void deleteCategory(int id) throws CostManagerException;
    public abstract List<Category> getAllCategories() throws CostManagerException;

    /**
     * CostItems queries implementation
     *
     * addCostItem - add new cost item to the database.
     * updateCostItem - update specific cost item from the database.
     * deleteCostItem - delete specific cost item from the database.
     * getAllCostItems - get list of cost items from the database.
     */
    public abstract void addCostItem(CostItem item) throws CostManagerException;
    public abstract void updateCostItem(CostItem item) throws CostManagerException;
    public abstract void deleteCostItem(int id) throws CostManagerException;
    public abstract List<CostItem> getAllCostItems() throws CostManagerException;

    /**
     * Report query implementation
     *
     *  getReportSummary - get list of cost items between dates from the database.
     */
    public abstract List<CostItem> getReportSummary(Date fromDate, Date toDate) throws CostManagerException;

    /**
     * Pie chart query implementation
     *
     * getPieChartSummary - get summary of cost items between dates from the database summed by category,
     *                      with rates according to the currency param.
     */
    public abstract Map<Category, Double> getPieChartSummary(Date fromDate, Date toDate, Currency currency) throws CostManagerException;
}