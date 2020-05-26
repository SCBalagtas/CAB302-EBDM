package Routes;

import Database.Users;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle login requests.
 */

public class Login {
    /**
     * Authenticates user credentials with data from the database.
     *
     * @param userName of the user.
     * @param password of the user.
     * @return true if the userName and password parameters provided match a record in the database.
     */
    private static boolean authenticateUserCredentials(String userName, String password) {
        // string ArrayList to store sql query results in
        ArrayList<String> userCredentials;

        // execute sql query
        try {
            userCredentials = Users.getUserCredentials(userName);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // check if userName is in the database
        if (!userCredentials.isEmpty()) {
            // Check if password matches the password in userCredentials
            if (password.equals(userCredentials.get(1))) { return true; }
        }
        return false;
    }
}
