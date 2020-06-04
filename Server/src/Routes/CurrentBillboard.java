package Routes;

import Classes.Response;
import Constants.StatusCodes;
import Database.Schedules;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to handle current billboard requests.
 */

public class CurrentBillboard {
    /**
     * This is the method to handle the current billboard request.
     *
     * @param oos an ObjectOutputStream object to write a response to the client.
     */
    public static void currentBillboard(ObjectOutputStream oos) throws IOException {
        // string to store the billboard's content that will be sent to the client
        String billboardContent;

        // get the current billboard
        billboardContent = Schedules.getCurrentlyScheduledBillboard();

        if (billboardContent.equals("")) {
            oos.writeObject(new Response(StatusCodes.NO_CONTENT, billboardContent));
        } else {
            oos.writeObject(new Response(StatusCodes.OK, billboardContent));
        }

        // flush oos
        oos.flush();
    }
}
