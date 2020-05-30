package Routes;

import Classes.Response;
import Constants.ServerPermissions;
import Constants.StatusCodes;
import Database.Billboards;
import Database.Schedules;
import Database.Users;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static Classes.Utility.hasTokenExpired;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle delete billboard requests.
 */

public class DeleteBillboard {
    /**
     * This is the method to handle the delete billboard request.
     *
     * @param parameters a string ArrayList containing the name of the billboard to be deleted and a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void deleteBillboard(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 2) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(1)) && !hasTokenExpired(sessions, parameters.get(1))) {
                // check if the billboard exists
                if (Billboards.doesBillboardExists(parameters.get(0))) {
                    // check if the user has the "Edit All Billboards" permission
                    if (Users.userHasPermission(sessions.get(parameters.get(1)).get(0), ServerPermissions.EDIT_ALL_BILLBOARDS)) {
                        // delete all schedules for this billboard and then the billboard itself, order matters here or SQL error
                        Schedules.deleteSchedulesFromDBByBillboardName(parameters.get(0));
                        Billboards.deleteBillboardFromDB(parameters.get(0));
                        oos.writeObject(new Response(StatusCodes.OK, "Billboard Deleted"));
                    } else {
                        // check if the user is trying to delete their own billboard
                        if (Billboards.getCreator(parameters.get(0)).equals(sessions.get(parameters.get(1)).get(0))) {
                            // check if the user has the "Create Billboards" permission and if the billboard is not scheduled
                            if (Users.userHasPermission(sessions.get(parameters.get(1)).get(0), ServerPermissions.CREATE_BILLBOARDS) && !Schedules.isBillboardScheduled(parameters.get(0))) {
                                // delete the billboard
                                Billboards.deleteBillboardFromDB(parameters.get(0));
                                oos.writeObject(new Response(StatusCodes.OK, "Billboard Deleted"));
                            } else {
                                oos.writeObject(new Response(StatusCodes.FORBIDDEN, "Missing Permissions"));
                            }
                        } else {
                            oos.writeObject(new Response(StatusCodes.FORBIDDEN, "Missing Permissions"));
                        }
                    }
                } else {
                    oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "Billboard Does Not Exist"));
                }
            } else {
                oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
            }
        }
        // flush oos
        oos.flush();
    }
}
