package Routes;

import Classes.Response;
import Constants.StatusCodes;
import Database.Users;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

    /**
     * This is the method to handle the login request.
     *
     * @param parameters a string ArrayList of the parameters to authenticate the user.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void login(ArrayList<String> parameters, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 2) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // get userName and password from parameters
            String userName = parameters.get(0);
            String password = parameters.get(1);

            // write a response to the client depending on the result from authenticating the user credentials
            if (authenticateUserCredentials(userName, password)) {
                // generate session token here
                // ...
                oos.writeObject(new Response(StatusCodes.OK, "Login Successful"));
            } else {
                oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Username or Password Invalid"));
            }
        }
        // flush oos
        oos.flush();
    }
}
