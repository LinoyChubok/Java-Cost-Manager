package CostManager.Model;

public interface IModel {
    public abstract void startConnection() throws CostManagerException;
    public abstract void closeConnection() throws CostManagerException;
    public abstract void createTables() throws CostManagerException;
}

