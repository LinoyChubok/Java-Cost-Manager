package CostManager.Model;

import java.sql.*;

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

    @Override
    public void closeConnection() throws CostManagerException { }

    @Override
    public void createTables() throws CostManagerException { }
}
