package Classes;

import java.io.Serializable;

/**
 * Author: Steven Balagtas
 *
 * This class is the response object that the server will be sending to the client.
 */

public class Response implements Serializable {
    /**
     * Private fields for the response object.
     */
    private int statusCode;
    private Object content;

    /**
     * Constructor to create a new instance of a response.
     *
     * @param statusCode an int of the status code associated with the response.
     * @param content an object containing the content of the response.
     */
    public Response(int statusCode, Object content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    /**
     * Get the response status code.
     *
     * @return an int of the status code.
     */
    public int getStatusCode() { return this.statusCode; }

    /**
     * Get the response content.
     *
     * @return an object of containing the content of the response.
     */
    public Object getContent() { return this.content; }
}
