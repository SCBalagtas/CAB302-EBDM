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
 * Class of static methods to handle set user password requests.
 */

public class SetUserPassword {
    /**
     * This is the method to handle the set user password request.
     *
     * @param parameters a string ArrayList of the userName of the user who's password will be changed, the new password and a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void setUserPassword(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 3) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(2)) && !hasTokenExpired(sessions, parameters.get(2))) {
                // check if the user exists
                if (Users.doesUserExists(parameters.get(0))) {
                    // check if the user is attempting to change their own password
                    if (parameters.get(0).equals(sessions.get(parameters.get(2)).get(0))) {
                        // set the new password
                        if (Users.setUserPasswordInDB(parameters.get(0), parameters.get(1))) {
                            oos.writeObject(new Response(StatusCodes.OK, "New Password Set"));
                        } else {
                            oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "New Password Not Set"));
                        }
                    } else {
                        // check if the user has the "Edit Users" permission
                        if (Users.userHasPermission(sessions.get(parameters.get(2)).get(0), ServerPermissions.EDIT_USERS)) {
                            // set the new password
                            if (Users.setUserPasswordInDB(parameters.get(0), parameters.get(1))) {
                                oos.writeObject(new Response(StatusCodes.OK, "New Password Set"));
                            } else {
                                oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "New Password Not Set"));
                            }
                        } else {
                            oos.writeObject(new Response(StatusCodes.FORBIDDEN, "Missing Permissions"));
                        }
                    }
                } else {
                    oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "User Does Not Exist"));
                }
            } else {
                oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
            }
        }
        // flush oos
        oos.flush();
    }
}
