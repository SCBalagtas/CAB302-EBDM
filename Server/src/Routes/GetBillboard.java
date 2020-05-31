package Routes;

import Classes.Response;
import Constants.StatusCodes;
import Database.Billboards;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static Classes.Utility.hasTokenExpired;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle get billboard requests.
 */

public class GetBillboard {
    /**
     * This is the method to handle the get billboard request.
     *
     * @param parameters a string ArrayList containing the billboardName of the billboard whose content will be retrieved and a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void getBillboard(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 2) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(1)) && !hasTokenExpired(sessions, parameters.get(1))) {
                // check if the billboard exists
                if (Billboards.doesBillboardExists(parameters.get(0))) {
                    // get the billboard's content
                    String billboardContent = Billboards.getBillboardContent(parameters.get(0));
                    oos.writeObject(new Response(StatusCodes.OK, billboardContent));
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
