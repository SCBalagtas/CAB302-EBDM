package Routes;

import Classes.Response;
import Constants.ServerPermissions;
import Constants.StatusCodes;
import Database.Billboards;
import Database.Schedules;
import Database.Users;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static Classes.Utility.hasTokenExpired;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle create schedule requests.
 */

public class CreateSchedule {
    /**
     * This is the method to handle the create schedule request.
     *
     * @param parameters a string ArrayList of the parameters to create the new schedule.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void createSchedule(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() == 4 || parameters.size() == 6) {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(3)) && !hasTokenExpired(sessions, parameters.get(3))) {
                // check if the user has the "Schedule Billboards" permission
                if (Users.userHasPermission(sessions.get(parameters.get(3)).get(0), ServerPermissions.SCHEDULE_BILLBOARDS)) {
                    // check if the billboard being scheduled exists
                    if (Billboards.doesBillboardExists(parameters.get(0))) {
                        // check if the schedule has frequency options
                        if (parameters.size() == 6) {
                            // add schedule with frequency options
                            // ...
                            oos.writeObject(new Response(StatusCodes.OK, "Still Working On It :)"));
                        } else {
                            // add schedule without frequency options
                            Schedules.insertSchedule(parameters.get(0), sessions.get(parameters.get(3)).get(0), LocalDateTime.parse(parameters.get(1)), Integer.parseInt(parameters.get(2)));
                            oos.writeObject(new Response(StatusCodes.OK, "Schedule Creation Successful"));
                        }
                    } else {
                        oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "Billboard Does Not Exist"));
                    }
                } else {
                    oos.writeObject(new Response(StatusCodes.FORBIDDEN, "Missing Permissions"));
                }
            } else {
                oos.writeObject(new Response(StatusCodes.UNAUTHORISED, "Unauthorised Request"));
            }
        } else {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        }
        // flush oos
        oos.flush();
    }
}
