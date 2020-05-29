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
 * Class of static methods to handle delete user requests.
 */

public class DeleteUser {
    /**
     * This is the method to handle the delete user request.
     *
     * @param parameters a string ArrayList of the userName of the user to delete and a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void deleteUser(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 2) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(1)) && !hasTokenExpired(sessions, parameters.get(1))) {
                // check if the user has the "Edit Users" permission
                if (Users.userHasPermission(sessions.get(parameters.get(1)).get(0), ServerPermissions.EDIT_USERS)) {
                    // check if the user is trying to delete themselves
                    if (parameters.get(0).equals(sessions.get(parameters.get(1)).get(0))) {
                        oos.writeObject(new Response(StatusCodes.FORBIDDEN, "Users Cannot Delete Themselves"));
                    } else {
                        // delete user
                        if (Users.deleteUserFromDB(parameters.get(0))) {
                            oos.writeObject(new Response(StatusCodes.OK, "User Deletion Successful"));
                        } else {
                            oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "User Deletion Unsuccessful"));
                        }
                    }
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
