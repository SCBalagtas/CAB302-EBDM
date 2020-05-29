package Routes;

import Classes.Response;
import Constants.ServerPermissions;
import Constants.StatusCodes;
import Database.Users;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static Classes.Utility.hasTokenExpired;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle set user permissions requests.
 */

public class SetUserPermissions {
    /**
     * This is the method to handle the set user permissions request.
     *
     * @param parameters a string ArrayList of the userName of the user who's permissions will be set, a list of the new permissions and a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void setUserPermissions(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 3) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid
            if (sessions.containsKey(parameters.get(2))) {
                // check if the session token has expired
                if (hasTokenExpired(sessions, parameters.get(2))) {
                    oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
                } else {
                    // check if the user has the "Edit Users" permission
                    if (Users.userHasPermission(sessions.get(parameters.get(2)).get(0), ServerPermissions.EDIT_USERS)) {
                        // check if user is trying to change their own permissions, users can't remove their own "Edit Users" permission.
                        if (parameters.get(0).equals(sessions.get(parameters.get(2)).get(0))) {
                            // convert permissions string from parameters into a list
                            List<String> permissionsList = Arrays.asList(parameters.get(1).substring(1, parameters.get(1).length() - 1).split(", "));

                            // if the new set of permissions contains the "Edit Users" permission set the new permissions, else cancel the operation
                            if (permissionsList.contains(Integer.toString(ServerPermissions.EDIT_USERS))) {
                                // set the new permissions
                                Users.setPermissionsInDB(parameters.get(0), parameters.get(1));
                                oos.writeObject(new Response(StatusCodes.OK, "New Permissions Set"));
                            } else {
                                oos.writeObject(new Response(StatusCodes.FORBIDDEN, "User attempted to remove their own 'Edit Users' permission"));
                            }
                        } else {
                            // set the new permissions
                            Users.setPermissionsInDB(parameters.get(0), parameters.get(1));
                            oos.writeObject(new Response(StatusCodes.OK, "New Permissions Set"));
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
