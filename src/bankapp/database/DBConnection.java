package bankapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    Connection con;
    private int QUERIES = 0;
    private boolean conFree = true;

    private String DatabaseDRIVER = "com.mysql.jdbc.Driver";
    private String DatabaseURL = "jdbc:mysql://localhost/bank_1";
    private String DatabaseUser = "root";
    private String DatabasePassword = "root";

    /**
     * Constructor of the Database which instantiates the connection
     *
     * @throws Exception
     */
    public DBConnection() throws Exception {
        try {
            // Load the driver
            Class.forName(DatabaseDRIVER).newInstance();

            // Get the connection
            con = DriverManager.getConnection(DatabaseURL, DatabaseUser, DatabasePassword);

        } catch (Exception ex) {
            throw new Exception("Couldn't open connection to database: " + ex.getMessage());
        }
    }

    /**
     * Closing the connection
     *
     */
    public void remove() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Getting the current Connection from pool
     *
     * @return Connection
     */
    protected Connection getConnection() {
        this.QUERIES = ++QUERIES;
        return con;
    }

    /**
     * Releasing the current Database Connection
     *
     */
    protected synchronized void releaseConnection() {
        remove();
    }

    /*
     * Get num of queries
     */
    public int getNumQueries() {
        return QUERIES;
    }
}
