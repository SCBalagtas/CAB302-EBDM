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
 * Class of static methods to handle get user permissions requests.
 */

public class GetUserPermissions {
    /**
     * This is the method to handle the get user permissions request.
     *
     * @param parameters a string ArrayList of the userName of the user who's permissions will be retrieved and a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void getUserPermissions(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        ArrayList<Integer> userPermissions;

        // check if correct number of parameters have been provided
        if (parameters.size() != 2) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid
            if (sessions.containsKey(parameters.get(1))) {
                // check if the session token has expired
                if (hasTokenExpired(sessions, parameters.get(1))) {
                    oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
                } else {
                    // check if user is attempting to get their own permissions
                    if (parameters.get(0).equals(sessions.get(parameters.get(1)).get(0))) {
                        // get the user's permissions
                        userPermissions = Users.getUserPermissionsFromDB(parameters.get(0));
                        if (userPermissions.isEmpty()) {
                            oos.writeObject(new Response(StatusCodes.NO_CONTENT, userPermissions));
                        } else {
                            oos.writeObject(new Response(StatusCodes.OK, userPermissions));
                        }
                    } else {
                        // check if the user has the "Edit Users" permission
                        if (Users.userHasPermission(sessions.get(parameters.get(1)).get(0), ServerPermissions.EDIT_USERS)) {
                            // get the user's permissions
                            userPermissions = Users.getUserPermissionsFromDB(parameters.get(0));
                            if (userPermissions.isEmpty()) {
                                oos.writeObject(new Response(StatusCodes.NO_CONTENT, userPermissions));
                            } else {
                                oos.writeObject(new Response(StatusCodes.OK, userPermissions));
                            }
                        } else {
                            oos.writeObject(new Response(StatusCodes.FORBIDDEN, "Missing Permissions"));
                        }
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
