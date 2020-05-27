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
        ArrayList<String> workingLogin = new ArrayList<>();
        workingLogin.add("admin");
        workingLogin.add("admin");

        ArrayList<String> wrongLogin = new ArrayList<>();
        wrongLogin.add("admin");
        wrongLogin.add("password");

        ArrayList<String> brokenLogin = new ArrayList<>();
        brokenLogin.add("admin");
        brokenLogin.add("admin");
        brokenLogin.add("admin");

        sendRequest(RequestTypes.LOGIN, workingLogin);
        sendRequest(RequestTypes.LOGIN, wrongLogin);
        sendRequest(RequestTypes.LOGIN, brokenLogin);
    }

    public static void sendRequest(String requestType, ArrayList<String> parameters) throws IOException, ClassNotFoundException {
        // open a connection to the server
        Socket socket = new Socket("localhost", ServerConfig.getPort());

        // write a request to the client
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

        // change request type to test other types
        Request request = new Request(requestType, parameters);

        oos.writeObject(request);
        oos.flush();

        // read the server's response
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        System.out.println(ois.readObject());

        Response response = (Response) ois.readObject();
        System.out.println(response.getStatusCode());
        System.out.println((response.getContent()).toString() + "\n");

        // close the streams
        oos.close();
        ois.close();

        // close the connection
        socket.close();
    }
}
