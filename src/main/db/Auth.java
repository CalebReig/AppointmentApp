package main.db;

import com.mysql.cj.jdbc.JdbcConnection;
import main.utils.TimeLogger;
import main.objs.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This class handles database queries authenticating the user at login.
 */
public class Auth {

    /**
     * This method verifies if the credentials provided by the user match a record of the users table in
     * the application database.
     * @param username The username input by the user
     * @param password The password input by the user
     * @return Returns true if the login was successful, otherwise returns false.
     */
    public static boolean verifyLogin(String username, String password) {
        boolean verify;
        String passwordQuery = null;
        int id = 0;
        JdbcConnection conn = main.db.DBConnection.startConnection();
        String query = "select * from users where User_Name = ?";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, username);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                passwordQuery = rs.getString("Password");
                id = rs.getInt("User_ID");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null) {
                main.db.DBConnection.closeConnection(conn);
            }
        }
        if ((passwordQuery != null) && (passwordQuery.equals(password))){
            verify =  true;
            TimeLogger.setLastLogin(LocalDateTime.now());
            User user = new User(id, username);
            User.setCurrentUser(user);
        }
        else {
            verify = false;
        }
        TimeLogger.logTime(verify);
        return verify;
    }
}
