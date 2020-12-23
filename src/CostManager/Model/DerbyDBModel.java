package CostManager.Model;

import java.sql.*;
import java.util.ArrayList;

public class DerbyDBModel implements IModel {

    /**
     * DerbyDBModel will implement IModel (Each Database type has different implementations).
     *
     * @param PROTOCOL         Describes the communication type
     * @param DRIVER           Describes the driver type
     * @param connection       Holds the database connection details
     * @param statement        Used for executing requests to the database
     * @param rs               Holds the information that comes back from the database
     */

    private static final String PROTOCOL = "jdbc:derby:";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;

    /**
     * The constructor calls the startConnection method that initializes the connection.
     *
     * @throws CostManagerException if there any problem at the connection creating.
     */
    public DerbyDBModel() throws CostManagerException {
        startConnection();
    }

    /**
     * Start the Derby database connection.
     */
    @Override
    public void startConnection() throws CostManagerException {
        String connectionUrl = PROTOCOL + "CostManagerDB"+";create=true";
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            createTables();
        } catch (SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch (ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }
    }

    /**
     * Closes the Derby database connection.
     *
     * @throws CostManagerException if there any problem at the connection closing.
     */
    @Override
    public void closeConnection() throws CostManagerException {
        try {
            DriverManager.getConnection(    PROTOCOL+";shutdown=true");
        } catch (SQLTimeoutException e) {
            throw new CostManagerException("A timeout has been exceeded.", e);
        } catch (SQLException e) {
            if (!((e.getErrorCode() == 50000) && ("XJ015".equals(e.getSQLState()))))
                throw new CostManagerException("Database access error or the url is null.", e);
        }

        if (statement != null)
            try {
                statement.close();
            } catch (SQLException e) {
                throw new CostManagerException("Error releasing Statement.", e);
            }

        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                throw new CostManagerException("Error closing DB connection.", e);
            }

        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                throw new CostManagerException("Error releasing ResultSet.", e);
            }
    }

    /**
     * createTables methods creates the CostItems table and the Categories table on the database.
     *
     * @throws CostManagerException if there any problem at the creating the tables.
     */
    @Override
    public void createTables() throws CostManagerException {
        // Categories Table
        try {
            statement.execute("create table Categories(" +
                    "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "name VARCHAR(30) UNIQUE)");
        } catch (SQLException e) {
            throw new CostManagerException("Error with creating Categories Category", e);
        }
        // CostItems Table
        try {
            statement.execute("create table CostItems(" +
                    "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "date DATE, " +
                    "category VARCHAR(30), " +
                    "FOREIGN KEY (category) REFERENCES Categories(name), " +
                    "description VARCHAR(100), " +
                    "currency VARCHAR(5) NOT NULL, " +
                    "totalprice DOUBLE NOT NULL)");
        } catch (SQLException e) {
            throw new CostManagerException("Error with creating CostItems table", e);
        }
    }

    /**
     * Inserts a new Category object to the database
     * @param category      Represents a new category
     */
    @Override
    public void addCategory(Category category) throws CostManagerException { }

    /**
     * Delete a category by id from the database
     * @param id            Represents id of a category
     */
    @Override
    public void deleteCategory(int id) throws CostManagerException { }

    /**
     * Get all the categories from the database
     */
    @Override
    public ArrayList<Category> getAllCategories() throws CostManagerException { }

    /**
     * Inserts a new CostItem object to the database
     * @param item          Represents a new cost item
     */
    @Override
    public void addCostItem(CostItem item) throws CostManagerException { }

    /**
     * Delete a cost item by id from the database
     * @param id            Represents id of a cost item
     */
    @Override
    public void deleteCostItem(int id) throws CostManagerException { }

    /**
     * Get all the cost items from the database
     */
    @Override
    public ArrayList<CostItem> getAllCostItems() throws CostManagerException { }

    /**
     * Get all the cost items between two dates from the database
     */
    @Override
    public ArrayList<CostItem> getAllCostItems(Date fromDate, Date toDate) throws CostManagerException { }
}

