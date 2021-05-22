package main.db;

import com.mysql.cj.jdbc.JdbcConnection;
import java.sql.*;

/**
 * This class contains methods to connect to the application data base
 * and templates for querying the database.
 */
public class DBConnection {
    //Replace the 3 variables below with your database information
    private static final String ipAddress = "DB URL HERE";
    private static final String username = "USER HERE";
    private static final String password = "PASSWORD HERE";

    private static final String protocol = "jdbc";
    private static final String vendorName =":mysql:";
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    private static final String MySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";



    /**
     * This method returns a JdbcConnection with the application database.
     * @return Returns the connection to the application database.
     */
    public static JdbcConnection startConnection() {
        JdbcConnection conn = null;
        try {
            Class.forName(MySQLJDBCDriver);
            conn = (JdbcConnection) DriverManager.getConnection(jdbcURL, username, password);
        }
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * This method closes a given connection.
     * @param conn The connection to the application database
     */
    public static void closeConnection(JdbcConnection conn) {
        try {
            conn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is a template for performing queries on the application database that will load data.
     * discussion of lambda
     * A lambda function is used in this method to allow different types of queries to be executed.
     * This makes the code for inserting values into the database more compact.
     * @param query The query to be run on the database
     * @param loadFunction The function that manipulated the result of the query
     */
    public static void queryDB(String query, Loadable loadFunction) {
        JdbcConnection conn = startConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                loadFunction.apply(rs);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * This method is a template for performing queries on the application database that will insert data.
     * discussion of lambda
     * A lambda function is used in this method to allow different types of insert queries to be executed.
     * This makes the code for inserting values into the database more compact.
     * @param query The query to be run on the database
     * @param vals The list of items to be inserted
     * @param insertFunction The function that inserts the items into the database table
     */
    public static void insertValues(String query, String[] vals, Insertable insertFunction) {
        JdbcConnection conn = startConnection();
        try {
            PreparedStatement prepStmt = conn.prepareStatement(query);
            insertFunction.apply(prepStmt, vals);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    /**
     * This method queries the application database for the id of the most recently
     * created appointment or customer. Then the id is returned.
     * @param isCustomer Boolean signifying if the query is for a customer or not
     * @return Returns the most recently created id.
     */
    public static int idQuery(boolean isCustomer) {
        JdbcConnection conn = startConnection();
        String query;
        if (isCustomer) {
            query = "select Customer_ID from customers order by Create_Date desc limit 1";
        }
        else {
            query = "select Appointment_ID from appointments order by Create_Date desc limit 1";
        }
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return -1;
    }

    /**
     * This method will delete a record from the application database given its primary key (id)
     * and whether the object is a customer or not.
     * @param isCustomer Boolean signifying if the query is for a customer or not
     * @param id The primary key (id) of the object being queried
     */
    public static void deleteObject(boolean isCustomer, int id) {
        JdbcConnection conn = startConnection();
        String query;
        if (isCustomer) {
            query = "delete from customers where Customer_ID = (select cast(? as unsigned))";
        }
        else {
            query = "delete from appointments where Appointment_ID = (select cast(? as unsigned))";
        }
        try {
            PreparedStatement prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, String.valueOf(id));
            prepStmt.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * This method deletes all appointments associated with a given customer.
     * @param id The id of a customer
     */
    public static void deleteObject(int id) {
        JdbcConnection conn = startConnection();
        String query = "delete from appointments where Customer_ID = (select cast(? as unsigned))";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, String.valueOf(id));
            prepStmt.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
}

