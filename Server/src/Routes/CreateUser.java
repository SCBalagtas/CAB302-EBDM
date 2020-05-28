package Routes;

import Classes.Response;
import Constants.ServerPermissions;
import Constants.StatusCodes;
import Database.Users;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import static Classes.Utility.hasTokenExpired;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle create user requests.
 */

public class CreateUser {
    /**
     * This is the method to handle the logout request.
     *
     * @param parameters a string ArrayList of the parameters to create the new user.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void createUser(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 4) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid
            if (sessions.containsKey(parameters.get(3))) {
                // check if the session token has expired
                if (hasTokenExpired(sessions, parameters.get(3))) {
                    oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
                } else {
                    // check if the user has the "Edit Users" permission
                    if (Users.userHasPermission(sessions.get(parameters.get(3)).get(0), ServerPermissions.EDIT_USERS)) {
                        // try to create the new user
                        if (Users.insertNewUser(parameters.get(0), parameters.get(1), parameters.get(2))) {
                            oos.writeObject(new Response(StatusCodes.CREATED, "User Creation Successful"));
                        } else {
                            oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "User Creation Unsuccessful"));
                        }
                    } else {
                        oos.writeObject(new Response(StatusCodes.FORBIDDEN, "Missing Permissions"));
                    }
                }
            } else {
                oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
            }
        }
        // flush oos
        oos.flush();
    }
}
