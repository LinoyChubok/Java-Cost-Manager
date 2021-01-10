package CostManager.Model;

import java.sql. * ;
import java.util.ArrayList;

public class DerbyDBModel implements IModel {

    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String PROTOCOL = "jdbc:derby:";
    private static final String DB_NAME = "CostManagerDB";

    /**
     * DerbyDBModel will implement IModel (different implementations for each database)
     * The constructor will call createTables method.
     *
     */
    public DerbyDBModel() throws CostManagerException {
        createTables();
    }

    /**
     * This method will create CostItems table and also Categories table,
     * if they do not exist on our database.
     *
     */
    @Override
    public void createTables() throws CostManagerException {
        // Start the Derby database connection.
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

            boolean foundCategoriesTbl = false;
            boolean foundCostItemsTbl = false;

            DatabaseMetaData meta = connection.getMetaData();
            rs = meta.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                if (rs.getString("TABLE_NAME").equals("CATEGORIES")) {
                    foundCategoriesTbl = true;
                }
                else if (rs.getString("TABLE_NAME").equals("COSTITEMS")) {
                    foundCostItemsTbl = true;
                }
            }

            if (!foundCategoriesTbl) {
                try {
                    statement.execute("create table Categories(" +
                            "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                            "name VARCHAR(30) UNIQUE)");
                } catch(SQLException e) {
                    throw new CostManagerException("Error with creating Categories Category", e);
                }
            }

            if (!foundCostItemsTbl) {
                try {
                    statement.execute("create table CostItems(" +
                            "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                            "date DATE, " +
                            "category VARCHAR(30), " +
                            "FOREIGN KEY (category) REFERENCES Categories(name), " +
                            "description VARCHAR(100), " +
                            "currency VARCHAR(5) NOT NULL, " +
                            "totalPrice DOUBLE NOT NULL)");
                } catch(SQLException e) {
                    throw new CostManagerException("Error with creating CostItems table", e);
                }
            }

        } catch(SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch(ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }

        // Closes the Derby database connection.
        try {
            statement.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing Statement.", e);
        }

        try {
            connection.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error closing database connection.", e);
        }

        try {
            rs.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing ResultSet.", e);
        }
    }

    /**
     * Inserts a new Category object into the database,
     * if the category do not exist on our database.
     *
     */
    @Override
    public void addCategory(Category category) throws CostManagerException {
        // Start the Derby database connection.
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

            String categoryName = category.getCategoryName();
            try {
                // Check if specific category object exists in the database (duplicated value)
                int categoryId = -1;
                rs = statement.executeQuery("SELECT * FROM Categories WHERE name ='" + categoryName + "'");
                if (rs.next()) {
                    categoryId = rs.getInt("id");
                }
                if (categoryId == -1) statement.execute("INSERT INTO Categories (name) " + "VALUES ('" + categoryName + "')");
                else throw new CostManagerException("Error duplicated category");
            } catch(SQLException e) {
                throw new CostManagerException("Error with checking category", e);
            }

        } catch(SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch(ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }

        // Closes the Derby database connection.
        try {
            statement.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing Statement.", e);
        }

        try {
            connection.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error closing database connection.", e);
        }

        try {
            rs.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing ResultSet.", e);
        }
    }

    /**
     * Delete a category according to specific id from the database.
     *
     */
    @Override
    public void deleteCategory(int id) throws CostManagerException {
        // Start the Derby database connection.
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";

        Connection connection;
        Statement statement;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

            try {
                statement.execute("DELETE FROM Categories WHERE id=" + id);
            } catch(SQLException e) {
                throw new CostManagerException("Error with deleting a specific category from the database", e);
            }

        } catch(SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch(ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }

        // Closes the Derby database connection.
        try {
            statement.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing Statement.", e);
        }

        try {
            connection.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error closing database connection.", e);
        }
    }

    /**
     * Get all the categories from the database.
     *
     */
    @Override
    public ArrayList < Category > getAllCategories() throws CostManagerException {
        ArrayList < Category > categories = new ArrayList < >();

        // Start the Derby database connection.
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

            try {
                rs = statement.executeQuery("SELECT * FROM Categories");
                while (rs.next()) {
                    Category category = new Category(rs.getInt("id"), rs.getString("name"));
                    categories.add(category);
                }
            } catch(SQLException e) {
                throw new CostManagerException("Error with get all categories", e);
            }

        } catch(SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch(ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }

        // Closes the Derby database connection.
        try {
            statement.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing Statement.", e);
        }

        try {
            connection.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error closing database connection.", e);
        }

        try {
            rs.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing ResultSet.", e);
        }

        return categories;
    }

    /**
     * Inserts a new CostItem object into the database.
     *
     */
    @Override
    public void addCostItem(CostItem item) throws CostManagerException {
        // Start the Derby database connection.
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";

        Connection connection;
        Statement statement;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

            try {
                statement.execute("INSERT INTO CostItems (date, category, description, currency, totalPrice)" +
                        "VALUES ('" + item.getDate() +
                        "', '" + item.getCategory().getCategoryName() +
                        "','" + item.getDescription() +
                        "', '" + item.getCurrency().name() +
                        "', " + item.getTotalPrice() + ")");
            } catch(SQLException e) {
                throw new CostManagerException("Error with adding a new cost item to database", e);
            }

        } catch(SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch(ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }

        // Closes the Derby database connection.
        try {
            statement.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing Statement.", e);
        }

        try {
            connection.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error closing database connection.", e);
        }
    }

    /**
     * Delete a cost item according to specific id from the database.
     *
     */
    @Override
    public void deleteCostItem(int id) throws CostManagerException {
        // Start the Derby database connection.
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";

        Connection connection;
        Statement statement;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

            try {
                statement.execute("DELETE FROM CostItems WHERE id=" + id);
            } catch(SQLException e) {
                throw new CostManagerException("Error with deleting a specific cost item from the database", e);
            }

        } catch(SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch(ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }

        // Closes the Derby database connection.
        try {
            statement.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing Statement.", e);
        }

        try {
            connection.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error closing database connection.", e);
        }
    }

    /**
     * Get all the cost items from the database.
     *
     */
    @Override
    public ArrayList < CostItem > getAllCostItems() throws CostManagerException {
        ArrayList < CostItem > items = new ArrayList < >();

        // Start the Derby database connection.
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

            try {
                rs = statement.executeQuery("SELECT * FROM CostItems");
                while (rs.next()) {
                    CostItem item = new CostItem (rs.getInt("id"),
                            rs.getDate("date"),
                            new Category(rs.getString("category")),
                            rs.getString("description"),
                            Currency.valueOf(rs.getString("currency")),
                            rs.getDouble("totalPrice"));
                    items.add(item);
                }
            } catch(SQLException | CostManagerException e) {
                throw new CostManagerException("Error with get all the cost items from the database", e);
            }

        } catch(SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch(ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }

        // Closes the Derby database connection.
        try {
            statement.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing Statement.", e);
        }

        try {
            connection.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error closing database connection.", e);
        }

        try {
            rs.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing ResultSet.", e);
        }

        return items;
    }

    /**
     * Get all the cost items between two dates from the database.
     *
     */
    @Override
    public ArrayList < CostItem > getAllCostItemsReport(Date fromDate, Date toDate) throws CostManagerException {
        ArrayList < CostItem > items = new ArrayList < >();

        // Start the Derby database connection.
        String connectionUrl = PROTOCOL + DB_NAME + ";create=true";

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();

            try {
                rs = statement.executeQuery("SELECT * FROM CostItems WHERE date BETWEEN DATE('" + fromDate.toLocalDate() + "') and DATE('" + toDate.toLocalDate() + "')");
                while (rs.next()) {
                    CostItem item = new CostItem(rs.getInt("id"), rs.getDate("date"), new Category(rs.getString("category")), rs.getString("description"), Currency.valueOf(rs.getString("currency")), rs.getDouble("totalPrice"));
                    items.add(item);
                }
            } catch(SQLException e) {
                throw new CostManagerException("Error with get all cost items between two dates from the database", e);
            }

        } catch(SQLException e) {
            throw new CostManagerException("Could not open Derby DB at " + connectionUrl, e);
        } catch(ClassNotFoundException e) {
            throw new CostManagerException("Could not find Derby driver " + DRIVER, e);
        }

        // Closes the Derby database connection.
        try {
            statement.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing Statement.", e);
        }

        try {
            connection.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error closing database connection.", e);
        }

        try {
            rs.close();
        } catch(SQLException e) {
            throw new CostManagerException("Error releasing ResultSet.", e);
        }

        return items;
    }
}