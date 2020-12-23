package CostManager.Model;

public class DerbyDBModel implements IModel {
    @Override
    public void startConnection() throws CostManagerException { }

    @Override
    public void closeConnection() throws CostManagerException { }

    @Override
    public void createTables() throws CostManagerException { }
}
