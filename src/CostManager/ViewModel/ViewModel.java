package CostManager.ViewModel;

import CostManager.Model.Category;
import CostManager.Model.CostItem;
import CostManager.Model.CostManagerException;
import CostManager.Model.IModel;
import CostManager.View.IView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel{

    private IModel model = null;
    private IView view = null;
    private ExecutorService pool;

    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void addCategory(Category category) {
        pool.submit(() -> {
            try {
                model.addCategory(category);
                getAllCategories();
                view.showMessage("Category was added successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void updateCategory(Category category) {
        pool.submit(() -> {
            try {
                model.updateCategory(category);
                getAllCategories();
                view.showMessage("Category was updated successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void deleteCategory(int id) {
        pool.submit(() -> {
            try {
                model.deleteCategory(id);
                getAllCategories();
                view.showMessage("Category was deleted successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void getAllCategories() {
        pool.submit(() -> {
            try {
                ArrayList<Category> categories = model.getAllCategories();
                view.showCategories(categories);
                if(categories.size() != 0)
                    view.showMessage("Categories loaded successfully");
                else view.showMessage("No data to display");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void addCostItem(CostItem item) {
        pool.submit(() -> {
            try {
                model.addCostItem(item);
                getAllCostItems();
                view.showMessage("Cost item was added successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void updateCostItem(CostItem item) {
        pool.submit(() -> {
            try {
                model.updateCostItem(item);
                getAllCostItems();
                view.showMessage("Cost item was updated successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void deleteCostItem(int id) {
        pool.submit(() -> {
            try {
                model.deleteCostItem(id);
                getAllCostItems();
                view.showMessage("Cost item was deleted successfully");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void getAllCostItems() {
        pool.submit(() -> {
            try {
                ArrayList<CostItem> items = model.getAllCostItems();
                ArrayList<Category> categories = model.getAllCategories();
                view.showCostItems(items, categories);
                if(items.size() != 0)
                    view.showMessage("Cost items loaded successfully");
                else view.showMessage("No data to display");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void getReportSummary(Date fromDate, Date toDate) {
        pool.submit(() -> {
            try {
                ArrayList<CostItem> items = model.getReportSummary(fromDate, toDate);
                view.showReportSummary(items);
                if(items.size() != 0)
                    view.showMessage("Report summary loaded successfully");
                else view.showMessage("No data to display");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }
}
