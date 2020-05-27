package Routes;

import Classes.Response;
import Constants.StatusCodes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import static Classes.Utility.hasTokenExpired;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle logout requests.
 */

public class Logout {
    /**
     * This is the method to handle the logout request.
     *
     * @param parameters a string ArrayList containing the session token to be expired.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void logout(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 1) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid
            if (sessions.containsKey(parameters.get(0))) {
                // check if the session token has expired
                if (hasTokenExpired(sessions, parameters.get(0))) {
                    oos.writeObject((new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request")));
                } else {
                    oos.writeObject((new Response(StatusCodes.OK, "Logout Successful")));
                }

                // delete this token from sessions
                sessions.remove(parameters.get(0));
            } else {
                oos.writeObject((new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request")));
            }
        }
        // flush oos
        oos.flush();
    }
}
