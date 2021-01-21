package il.ac.shenkar.costmanager.viewmodel;

import il.ac.shenkar.costmanager.model.*;
import il.ac.shenkar.costmanager.view.IView;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;
    private final ExecutorService pool;

    public ViewModel() {
        model = null;
        view = null;
        pool = Executors.newFixedThreadPool(10);
    }

    /**
     * Set the view.
     * @param view Variable that holding the view.
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Set the model.
     * @param model Variable that holding the model.
     */
    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * Add new category to the database.
     * @param category Defined at Category class. (constructor without id)
     */
    @Override
    public void addCategory(Category category) {
        pool.submit(() -> {
            try {
                model.addCategory(category);
                List<Category> categories = model.getAllCategories();
                view.showCategories(categories);
                view.showMessage("Category was added successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Update specific category from the database.
     * @param category Defined at Category class.  (constructor with id)
     */
    @Override
    public void updateCategory(Category category) {
        pool.submit(() -> {
            try {
                model.updateCategory(category);
                List<Category> categories = model.getAllCategories();
                view.showCategories(categories);
                view.showMessage("Category was updated successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Delete specific category from the database.
     * @param id Category id (int).
     */
    @Override
    public void deleteCategory(int id) {
        pool.submit(() -> {
            try {
                model.deleteCategory(id);
                List<Category> categories = model.getAllCategories();
                view.showCategories(categories);
                view.showMessage("Category was deleted successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Get list of categories from the database.
     *
     */
    @Override
    public void getAllCategories() {
        pool.submit(() -> {
            try {
                List<Category> categories = model.getAllCategories();
                view.showCategories(categories);
                if(categories.size() != 0)
                    view.showMessage("Categories loaded successfully");
                else view.showMessage("No data to display");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Add new cost item to the database.
     * @param item Defined at CostItem class. (constructor without id)
     */
    @Override
    public void addCostItem(CostItem item) {
        pool.submit(() -> {
            try {
                model.addCostItem(item);
                List<CostItem> items = model.getAllCostItems();
                view.showCostItems(items);
                view.showMessage("Cost item was added successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Update specific cost item from the database.
     * @param item Defined at CostItem class. (constructor with id)
     */
    @Override
    public void updateCostItem(CostItem item) {
        pool.submit(() -> {
            try {
                model.updateCostItem(item);
                List<CostItem> items = model.getAllCostItems();
                view.showCostItems(items);
                view.showMessage("Cost item was updated successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Delete specific cost item from the database.
     * @param id CostItem id (int).
     */
    @Override
    public void deleteCostItem(int id) {
        pool.submit(() -> {
            try {
                model.deleteCostItem(id);
                List<CostItem> items = model.getAllCostItems();
                view.showCostItems(items);
                view.showMessage("Cost item was deleted successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Get list of cost items from the database.
     *
     */
    @Override
    public void getAllCostItems() {
        pool.submit(() -> {
            try {
                List<CostItem> items = model.getAllCostItems();
                view.showCostItems(items);
                if(items.size() != 0)
                    view.showMessage("Cost items loaded successfully");
                else view.showMessage("No data to display");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Get list of cost items between dates from the database.
     * @param fromDate The starting date of the report summary.
     * @param toDate   The ending date of the report summary.
     */
    @Override
    public void getReportSummary(Date fromDate, Date toDate) {
        pool.submit(() -> {
            try {
                List<CostItem> items = model.getReportSummary(fromDate, toDate);
                view.showReportSummary(items);
                if(items.size() != 0)
                    view.showMessage("Report summary loaded successfully");
                else view.showMessage("No data to display");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * Get summary of cost items between dates from the database summed by category,
     * with rates according to the currency param.
     * @param fromDate The starting date of the pie chart summary.
     * @param toDate   The ending date of the pie chart summary.
     * @param currency The currency type (used for rates).
     */
    @Override
    public void getPieChartSummary(Date fromDate, Date toDate, Currency currency) {
        pool.submit(() -> {
            try {
                // TODO: RETURN VALUE FROM THE MODEL FUNCTION
                model.getPieChartSummary(fromDate, toDate, currency);
                // TODO: PASS THE VALUE TO VIEW FUNCTION
                view.showPieChartSummary();
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    /**
     * This method ensures the database connection shuts down safely, any cleans up will be here.
     *
     */
    @Override
    public void shutdownDB() {
        pool.submit(() -> {
            try {
                model.shutdownDB();
            } catch(CostManagerException e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
