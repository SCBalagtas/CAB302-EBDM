package MockObjects;

import Classes.Request;
import Classes.Response;
import Configs.ServerConfig;
import Constants.RequestTypes;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Author: Steven Balagtas
 *
 * A simple test client class to test the server responses.
 */

public class TestClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        loginRequest();
    }

    public static void loginRequest() throws IOException, ClassNotFoundException {
        // open a connection to the server
        Socket socket = new Socket("localhost", ServerConfig.getPort());

        // write a request to the client
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

        // change parameters for the request to test other types
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("admin");
        parameters.add("admin");

        // change request type to test other types
        Request request = new Request(RequestTypes.LOGIN, parameters);

        oos.writeObject(request);
        oos.flush();

        // read the server's response
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        System.out.println(ois.readObject());

        Response response = (Response) ois.readObject();
        System.out.println(response.getStatusCode());
        System.out.println((response.getContent()).toString());

        // close the streams
        oos.close();
        ois.close();

        // close the connection
        socket.close();
    }
}
