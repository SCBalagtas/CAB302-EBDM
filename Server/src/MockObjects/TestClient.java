package MockObjects;

import java.io.*;
import java.net.Socket;

/**
 * Author: Steven Balagtas
 *
 * A simple test client class to test the server responses.
 */

public class TestClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        serverRequest1();
        serverRequest2();
    }

    public static void serverRequest1() throws IOException, ClassNotFoundException {
        // open a connection to the server
        Socket socket = new Socket("localhost", 3000);

        // write a request to the client
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

        oos.writeObject("Hi server!");
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

    public static void serverRequest2() throws IOException, ClassNotFoundException {
        // open a connection to the server
        Socket socket = new Socket("localhost", 3000);

        // write a request to the client
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

        oos.writeObject("How are you?");
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
