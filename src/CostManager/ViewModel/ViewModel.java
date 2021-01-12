package CostManager.ViewModel;

import CostManager.Model.Category;
import CostManager.Model.CostItem;
import CostManager.Model.CostManagerException;
import CostManager.Model.IModel;
import CostManager.View.IView;

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
    public void getAllCostItems() {
        pool.submit(() -> {
            try {
                ArrayList<CostItem> items = model.getAllCostItems();
                ArrayList<Category> categories = model.getAllCategories();
                view.showItems(items, categories);
                if(items.size() != 0)
                    view.showMessage("Cost items loaded successfully");
                else view.showMessage("No data to display");
            } catch(CostManagerException e) {
                view.showMessage(e.getMessage());
            }
        });
    }
}
