package Routes;

import Classes.Response;
import Constants.StatusCodes;
import Database.Users;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
     * Author: CAB302 Teaching Team
     *
     * Convert byte[] to string.
     */
    public static String bytesToString(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bytes) {
            stringBuffer.append(String.format("%02x", b & 0xFF));
        }
        return stringBuffer.toString();
    }

    /**
     * Author: CAB302 Teaching Team
     *
     * Generate a random session token.
     */
    private static String generateSessionToken() {
        // generate a random string to use as the session token
        Random rng = new Random();
        byte[] tokenBytes = new byte[32];
        rng.nextBytes(tokenBytes);
        return bytesToString(tokenBytes);
    }

    /**
     * This is the method to handle the login request.
     *
     * @param parameters a string ArrayList of the parameters to authenticate the user.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void login(ArrayList<String> parameters, HashMap<String, HashMap<String, LocalDateTime>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 2) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // get userName and password from parameters
            String userName = parameters.get(0);
            String password = parameters.get(1);

            // write a response to the client depending on the result from authenticating the user credentials
            if (authenticateUserCredentials(userName, password)) {
                // create a new inner HashMap to store the session token and it's creation date and time
                HashMap<String, LocalDateTime> userToken = new HashMap<>();

                // generate session token and store it into userToken
                String token = generateSessionToken();
                userToken.put(token, LocalDateTime.now());

                // store userToken into sessions
                sessions.put(userName, userToken);

                oos.writeObject(new Response(StatusCodes.OK, token));
            } else {
                oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Username or Password Invalid"));
            }
        }
        // flush oos
        oos.flush();
    }
}
