import Database.DBSetup;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: Steven Balagtas
 *
 * This is the main class, the server will be initialised here.
 */

public class Main {
    public static void main(String[] args) {
        // check if database is setup
        try {
            DBSetup.setupTables();
        } catch (Exception e) {
            System.err.println(e);
            System.out.println("FAILURE! Could not connect to database");
            System.out.println("Terminating server now...");
            System.exit(0);
        }

        // initialise and run the server
        try {
            ServerSocket serverSocket = new ServerSocket(ServerConfig.getPort());
            System.out.println("Server is now running and listening on port: " + ServerConfig.getPort());

            // infinite loop to keep listening for server requests
            for (;;) {
                // listen for a client connection
                Socket socket = serverSocket.accept();

                // read the client's request
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);

                Object request = ois.readObject();

                System.out.println("\nClient connected: " + socket.getInetAddress());
                System.out.println("Client requested: " + request);

                // write a response to the client
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);

                oos.writeObject("Request for: '" + request + "' received");
                oos.flush();

                // close the streams
                ois.close();
                oos.close();

                // close the client connection
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
