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
 * Class of static methods to handle list users requests.
 */

public class ListUsers {
    /**
     * This is the method to handle the list users request.
     *
     * @param parameters a string ArrayList containing a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void listUsers(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 1) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(0)) && !hasTokenExpired(sessions, parameters.get(0))) {
                // check if the user has the "Edit Users" permission
                if (Users.userHasPermission(sessions.get(parameters.get(0)).get(0), ServerPermissions.EDIT_USERS)) {
                    // string ArrayList to store the userNames from the sql query
                    ArrayList<String> userNames = new ArrayList<>();

                    // get the list of users
                    userNames = Users.getUsers();
                    oos.writeObject(new Response(StatusCodes.OK, userNames));
                } else {
                    oos.writeObject(new Response(StatusCodes.FORBIDDEN, "Missing Permissions"));
                }
            } else {
                oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
            }
        }
        // flush oos
        oos.flush();
    }
}
