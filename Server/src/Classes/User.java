package Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: Steven Balagtas
 *
 * This class is the user object.
 */

public class User implements Serializable {
    /**
     * Private fields for the user object.
     */
    private String userName;
    private String password;
    private ArrayList<String> permissions;

    /**
     * Constructor to create a new instance of a user.
     *
     * @param userName a string of the user's username.
     * @param password a string of the user's password.
     * @param permissions a string ArrayList of the user's permissions.
     */
    public User(String userName, String password, ArrayList<String> permissions) {
        this.userName = userName;
        this.password = password;
        this.permissions = permissions;
    }

    /**
     * Get the user's username.
     *
     * @return a string of the user's username.
     */
    public String getUserName() { return this.userName; }

    /**
     * Get the user's password.
     *
     * @return a string of the user's password.
     */
    public String getPassword() { return this.password; }

    /**
     * Get the user's permissions.
     *
     * @return a string ArrayList of the user's permissions.
     */
    public ArrayList<String> getPermissions() { return this.permissions; }
}
