package MockObjects;

import Classes.Request;
import Configs.ServerConfig;
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
        serverRequest1();
    }

    public static void serverRequest1() throws IOException, ClassNotFoundException {
        // open a connection to the server
        Socket socket = new Socket("localhost", ServerConfig.getPort());

        // write a request to the client
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("admin");
        parameters.add("admin");

        Request request = new Request("Login", parameters);

        oos.writeObject(request);
        oos.flush();

        // read the server's response
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);

        System.out.println(ois.readObject());

        // close the streams
        oos.close();
        ois.close();

        // close the connection
        socket.close();
    }
}
