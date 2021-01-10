package CostManager.Model;

import java.sql.Date;
import java.util.ArrayList;

public interface IModel {

    /**
     * Database implementation
     */
    public abstract void createTables() throws CostManagerException;

    /**
     * Categories queries implementation
     */
    public abstract void addCategory(Category category) throws CostManagerException;
    public abstract void deleteCategory(int id) throws CostManagerException;
    public abstract ArrayList<Category> getAllCategories() throws CostManagerException;

    /**
     * CostItems queries implementation
     */
    public abstract void addCostItem(CostItem item) throws CostManagerException;
    public abstract void deleteCostItem(int id) throws CostManagerException;
    public abstract ArrayList<CostItem> getAllCostItems() throws CostManagerException;
    public abstract ArrayList<CostItem> getAllCostItemsReport(Date fromDate, Date toDate) throws CostManagerException;
}

