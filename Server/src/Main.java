import Classes.Request;
import Classes.Response;
import Configs.ServerConfig;
import Constants.RequestTypes;
import Constants.StatusCodes;
import Database.DBSetup;
import Routes.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

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

        // instantiate a new session HashMap
        HashMap<String, ArrayList<String>> sessions = new HashMap<>();

        // initialise and run the server
        try {
            ServerSocket serverSocket = new ServerSocket(ServerConfig.getPort());
            System.out.println("Server is now running and listening on port: " + ServerConfig.getPort());

            // infinite loop to keep listening for server requests
            for (;;) {
                // listen for a client connection
                Socket socket = serverSocket.accept();

                // instantiate objects for input and output
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                Request request = (Request) ois.readObject();
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);

                // basic logging
                System.out.println("\nClient connected: " + socket.getInetAddress());
                System.out.println("Client requested: " + request.getRequestType() + " with parameters: " + request.getRequestParameters().toString());

                // only for the TestClient --REMOVE LATER--
                oos.writeObject("Request for: '" + request.getRequestType() + "' received");
                oos.flush();

                // handle requests here
                if (request.getRequestType().equals(RequestTypes.LOGIN)) {
                    Login.login(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.LOGOUT)) {
                    Logout.logout(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.CREATE_USER)) {
                    CreateUser.createUser(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.DELETE_USER)) {
                    DeleteUser.deleteUser(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.SET_PASSWORD)) {
                    SetUserPassword.setUserPassword(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.GET_PERMISSIONS)) {
                    GetUserPermissions.getUserPermissions(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.SET_PERMISSIONS)) {
                    SetUserPermissions.setUserPermissions(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.LIST_USERS)) {
                    ListUsers.listUsers(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.CREATE_BILLBOARD)) {
                    CreateBillboard.createBillboard(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.EDIT_BILLBOARD)) {
                    EditBillboard.editBillboard(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.DELETE_BILLBOARD)) {
                    DeleteBillboard.deleteBillboard(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.GET_BILLBOARD)) {
                    GetBillboard.getBillboard(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.LIST_BILLBOARDS)) {
                    ListBillboards.listBillboards(request.getRequestParameters(), sessions, oos);
                } else if (request.getRequestType().equals(RequestTypes.CREATE_SCHEDULE)) {
                    CreateSchedule.createSchedule(request.getRequestParameters(), sessions, oos);
                } else {
                    oos.writeObject(new Response(StatusCodes.BAD_REQUEST, "Request Type Invalid"));
                    oos.flush();
                }

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
