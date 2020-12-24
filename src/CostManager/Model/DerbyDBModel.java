package CostManager.Model;

import java.sql.*;
import java.util.ArrayList;

public class DerbyDBModel implements IModel {
    // !!!!!! Need to open connection and close connection for each function !!!!!!!
    /**
     * DerbyDBModel will implement IModel (different implementations for each database).
     *
     * @param PROTOCOL              Describes the communication type
     * @param DRIVER                Describes the driver type
     * @param DB_NAME               Describes the database name
     */

    private static final String PROTOCOL = "jdbc:derby:";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_NAME = "CostManagerDB";

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
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";
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
            DriverManager.getConnection(PROTOCOL + ";shutdown=true");
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
                throw new CostManagerException("Error closing database connection.", e);
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
                    "totalPrice DOUBLE NOT NULL)");
        } catch (SQLException e) {
            throw new CostManagerException("Error with creating CostItems table", e);
        }
    }

    /**
     * Checking if a specific category object exists in the database (duplicated value)
     */
    @Override
    public int categoryExist(String categoryName) throws CostManagerException {
        int categoryId = -1;
        try {
            rs = statement.executeQuery("SELECT * FROM Categories WHERE name ='" + categoryName + "'");
            if(rs.next())
                categoryId = rs.getInt("id");
            return categoryId;
        } catch (SQLException e) {
            throw new CostManagerException("Error with checking category", e);
        }
    }

    /**
     * Inserts a new Category object to the database.
     * @param category              Represents a new category.
     */
    @Override
    public void addCategory(Category category) throws CostManagerException {
        try {
            String categoryName = category.getCategoryName();
            if(categoryExist(categoryName) == -1)
                statement.execute("INSERT INTO Categories (name) " + "VALUES ('" + categoryName + "')");
            else throw new CostManagerException("Error duplicated category");
        } catch (SQLException e) {
            throw new CostManagerException("Error with adding a new category to the database", e);
        }
    }

    /**
     * Delete a category by id from the database.
     * @param id                    Represents id of a category.
     */
    @Override
    public void deleteCategory(int id) throws CostManagerException {
        try {
            statement.execute("DELETE FROM Categories WHERE id=" + id );
        } catch (SQLException e) {
            throw new CostManagerException("Error with deleting a specific category from the database", e);
        }
    }

    /**
     * Get all the categories from the database.
     * @return categories            Array list of categories.
     */
    @Override
    public ArrayList<Category> getAllCategories() throws CostManagerException {
        ArrayList<Category> categories = new ArrayList<>();

        try {
            rs = statement.executeQuery("SELECT * FROM Categories");
            while (rs.next()) {
                Category category = new Category( rs.getInt("id"), rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new CostManagerException("Error with get all categories", e);
        }

        return categories;
    }

    /**
     * Inserts a new CostItem object to the database.
     * @param item                  Represents a new cost item.
     */
    @Override
    public void addCostItem(CostItem item) throws CostManagerException {
        try {
            statement.execute("INSERT INTO CostItems (date, category, description, currency, totalPrice)" +
                    "VALUES ('" + item.getDate() +
                    "', '" + item.getCategory().getCategoryName() +
                    "','" + item.getDescription() +
                    "', '" + item.getCurrency().name() +
                    "', " + item.getTotalPrice() + ")");
        } catch (SQLException e) {
            throw new CostManagerException("Error with adding a new cost item to database", e);
        }
    }

    /**
     * Delete a cost item by id from the database.
     * @param id                    Represents id of a cost item.
     */
    @Override
    public void deleteCostItem(int id) throws CostManagerException {
        try {
            statement.execute("DELETE FROM CostItems WHERE id=" + id );
        } catch (SQLException e) {
            throw new CostManagerException("Error with deleting a specific cost item from the database", e);
        }
    }

    /**
     * Get all the cost items from the database.
     * @return items                Array list of cost items.
     */
    @Override
    public ArrayList<CostItem> getAllCostItems() throws CostManagerException {
        ArrayList<CostItem> items = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM CostItems");
            while (rs.next()) {
                CostItem item = new CostItem (rs.getInt("id"),
                        rs.getDate("date"),
                        new Category(rs.getString("category")),
                        rs.getString("description"),
                        Currency.valueOf(rs.getString("currency")),
                        rs.getDouble("totalPrice"));
                items.add(item);
            }
        } catch (SQLException | CostManagerException e) {
            throw new CostManagerException("Error with get all the cost items from the database", e);
        }

        return items;
    }

    /**
     * Get all the cost items between two dates from the database.
     * @return items                Array list of cost items.
     */
    @Override
    public ArrayList<CostItem> getAllCostItems(Date fromDate, Date toDate) throws CostManagerException {
        ArrayList<CostItem> items = new ArrayList<>();

        try {
            rs = statement.executeQuery("SELECT * FROM CostItems WHERE date BETWEEN DATE('" + fromDate.toLocalDate() + "') and DATE('" + toDate.toLocalDate() + "')");
            while (rs.next()) {
                CostItem item = new CostItem (rs.getInt("id"),
                        rs.getDate("date"),
                        new Category(rs.getString("category")),
                        rs.getString("description"),
                        Currency.valueOf(rs.getString("currency")),
                        rs.getDouble("totalPrice"));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new CostManagerException("Error with get all cost items between two dates from the database", e);
        }

        return items;
    }
}

