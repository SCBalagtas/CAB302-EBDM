package Routes;

import Classes.Billboard;
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
 * Class of static methods to handle list billboards requests.
 */

public class ListBillboards {
    /**
     * This is the method to handle the list billboards request.
     *
     * @param parameters a string ArrayList containing a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void listBillboards(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 1) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(0)) && !hasTokenExpired(sessions, parameters.get(0))) {
                // billboard ArrayList to store the billboards from the sql query
                ArrayList<Billboard> billboards;

                // get the list of billboards
                billboards = Billboards.getBillboards();
                oos.writeObject(new Response(StatusCodes.OK, billboards));
            } else {
                oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
            }
        }
        // flush oos
        oos.flush();
    }
}
