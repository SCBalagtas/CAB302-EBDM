package Routes;

import Classes.Response;
import Constants.ServerPermissions;
import Constants.StatusCodes;
import Database.Billboards;
import Database.Users;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static Classes.Utility.hasTokenExpired;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle create billboard requests.
 */

public class CreateBillboard {
    /**
     * This is the method to handle the create billboard request.
     *
     * @param parameters a string ArrayList of the parameters to create the new billboard.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void createBillboard(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 3) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(2)) && !hasTokenExpired(sessions, parameters.get(2))) {
                // check if the user has the "Create Billboards" permission
                if (Users.userHasPermission(sessions.get(parameters.get(2)).get(0), ServerPermissions.CREATE_BILLBOARDS)) {
                    // create new billboard if it doesn't already exist
                    if (Billboards.doesBillboardExists(parameters.get(0))) {
                        oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "Billboard Already Exists"));
                    } else {
                        // create new billboard
                        Billboards.insertNewBillboard(parameters.get(0), parameters.get(1), sessions.get(parameters.get(2)).get(0));
                        oos.writeObject(new Response(StatusCodes.CREATED, "Billboard Creation Successful"));
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
