package Routes;

import Classes.Response;
import Classes.Schedule;
import Constants.ServerPermissions;
import Constants.StatusCodes;
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
 * Class of static methods to handle view schedule requests.
 */

public class ViewSchedule {
    /**
     * This is the method to handle the view schedule request.
     *
     * @param parameters a string ArrayList containing a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void viewSchedule(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 1) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(0)) && !hasTokenExpired(sessions, parameters.get(0))) {
                // check if the user has the "Schedule Billboards" permission
                if (Users.userHasPermission(sessions.get(parameters.get(0)).get(0), ServerPermissions.SCHEDULE_BILLBOARDS)) {
                    // schedule ArrayList to store the schedules from the sql query
                    ArrayList<Schedule> schedules;

                    // get the list of schedules
                    schedules = Schedules.getSchedules();
                    oos.writeObject(new Response(StatusCodes.OK, schedules));
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
