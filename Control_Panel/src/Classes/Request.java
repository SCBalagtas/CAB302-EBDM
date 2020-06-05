package Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: Steven Balagtas
 *
 * This class is the request object that the client will be sending to the server.
 */

public class Request implements Serializable {
    /**
     * Private fields for the request object.
     */
    private String requestType;
    private ArrayList<String> requestParameters;

    /**
     * Constructor to create a new instance of a request.
     *
     * @param requestType a string to indicate the request type.
     * @param requestParameters a string ArrayList of parameters for the request.
     */
    public Request(String requestType, ArrayList<String> requestParameters) {
        this.requestType = requestType;
        this.requestParameters = requestParameters;
    }

    /**
     * Get the request type.
     *
     * @return a string of the request type.
     */
    public String getRequestType() { return this.requestType; }

    /**
     * Get the request parameters.
     *
     * @return a string ArrayList of the request parameters.
     */
    public ArrayList<String> getRequestParameters() { return this.requestParameters; }
}
