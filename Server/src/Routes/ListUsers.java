package Routes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle list users requests.
 */

public class ListUsers {
    /**
     * This is the method to handle the list users request.
     *
     * @param parameters a string ArrayList a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void listUsers(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {

    }
}
