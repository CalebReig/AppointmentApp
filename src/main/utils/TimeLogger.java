package main.utils;

import main.objs.User;
import java.io.FileWriter;
import java.time.LocalDateTime;

/**
 * This class keeps track of the current user's last login time and documents
 * all login attempts to the <em>login_activity.txt</em> file.
 */
public class TimeLogger {

    private static LocalDateTime lastLogin;

    /**
     * This method sets the last login time.
     * @param now The moment of the user logging in.
     */
    public static void setLastLogin(LocalDateTime now) {
        lastLogin = now;
    }

    /**
     * This method returns the last login time.
     * @return Returns the last login time.
     */
    public static LocalDateTime getLastLogin() {
        return lastLogin;
    }

    /**
     * This method will document all attempts to login to the application in the file
     * <em>login_activity.txt</em>.
     * @param success The boolean value for whether the login was successful
     */
    public static void logTime(boolean success){
        String loginStatus;
        String username;
        if (success) {
            loginStatus = "successful";
            username = User.getCurrentUser().getUsername();
        }
        else {
            loginStatus = "failed";
            username = "unknown user";
        }
        String message = "User: " + username + ", login " + loginStatus + " at " +
                LocalDateTime.now() + "\n";
        try {
            FileWriter writer = new FileWriter("login_activity.txt", true);
            writer.write(message);
            writer.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

