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
 * Class of static methods to handle delete schedule requests.
 */

public class DeleteSchedule {
    /**
     * This is the method to handle the delete schedule request.
     *
     * @param parameters a string ArrayList containing the name of the billboard and the DateTime of the schedule to be deleted along with a valid session token.
     * @param sessions a HashMap of the active session tokens for the server.
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void deleteSchedule(ArrayList<String> parameters, HashMap<String, ArrayList<String>> sessions, ObjectOutputStream oos) throws IOException {
        // check if correct number of parameters have been provided
        if (parameters.size() != 3) {
            oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Parameters Invalid"));
        } else {
            // check if token from parameters is valid and if session token has not yet expired
            if (sessions.containsKey(parameters.get(2)) && !hasTokenExpired(sessions, parameters.get(2))) {
                // check if the user has the "Schedule Billboards" permission
                if (Users.userHasPermission(sessions.get(parameters.get(2)).get(0), ServerPermissions.SCHEDULE_BILLBOARDS)) {
                    // check if the billboard in the schedule being deleted exists
                    if (Billboards.doesBillboardExists(parameters.get(0))) {
                        // delete the schedule
                        Schedules.deleteScheduleFromDB(parameters.get(0), LocalDateTime.parse(parameters.get(1)));
                        oos.writeObject(new Response(StatusCodes.OK, "Schedule Deletion Successful"));
                    } else {
                        oos.writeObject(new Response(StatusCodes.INTERNAL_ERROR, "Billboard Does Not Exist"));
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
