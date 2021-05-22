package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates and modifies user objects which are
 * copies of users stored in the application database.
 */
public class User {

    private int id;
    private String username;
    //The user logged into the application
    private static User currentUser;
    //A list of all users
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * This is a constructor method.
     * It creates an instance of the <em>User</em> class.
     * @param id The user id
     * @param username The user username
     */
    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * This method returns the user username.
     * @return Returns the user username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the user id.
     * @return Returns the user id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the current user of the application.
     * @param user The user to set
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * This method returns the current user of the application.
     * @return Returns the current user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Returns the <em>allUsers</em> list containing all users.
     * @return Returns a list of all user.
     */
    public static ObservableList<User> getAllUsers() {return allUsers;}

    /**
     * This method adds a user to the <em>allUsers</em> list.
     * @param user The contact to add
     */
    public static void addUser(User user) {
        allUsers.add(user);
    }

    /**
     * This method returns a user from the <em>allUsers</em>
     * list based on the user id.
     * @param id The id of the user to return
     * @return Returns a user object.
     */
    public static User findUser(int id) {
        for (User user: allUsers) {
            if (id == user.id) {return user;}
        }
        return null;
    }

    /**
     * This method returns a user from the <em>allUsers</em>
     * list based on the user name.
     * @param username The username of the user to return
     * @return Returns a user object.
     */
    public static User findUser(String username) {
        for (User user: allUsers) {
            if (username.equals(user.username)) {
                return user;
            }
        }
        return null;
    }
}
