package CostManager.ViewModel;

import CostManager.Model.*;
import CostManager.View.*;

public interface IViewModel {
    public abstract void setView(IView view);
    public abstract void setModel(IModel model);

    public abstract void addCategory(Category category);
    public abstract void updateCategory(Category category);
    public abstract void deleteCategory(int id);
    public abstract void getAllCategories();

    public abstract void addCostItem(CostItem item);
    public abstract void updateCostItem(CostItem item);
    public abstract void deleteCostItem(int id);
    public abstract void getAllCostItems();
}

