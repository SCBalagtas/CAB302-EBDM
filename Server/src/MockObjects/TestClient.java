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
        // login request tests
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

        String token = sendRequest(RequestTypes.LOGIN, workingLogin); // This is the only request that will return a token
        sendRequest(RequestTypes.LOGIN, wrongLogin);
        sendRequest(RequestTypes.LOGIN, brokenLogin);

        // create new user request tests
        ArrayList<String> workingCreateUser = new ArrayList<>();
        workingCreateUser.add("testUser1");
        workingCreateUser.add("{1}");
        workingCreateUser.add("password");
        workingCreateUser.add(token);

        ArrayList<String> wrongCreateUser = new ArrayList<>();
        wrongCreateUser.add("testUser1");
        wrongCreateUser.add("{1}");
        wrongCreateUser.add("password");
        wrongCreateUser.add("Fake Token");

        ArrayList<String> brokenCreateUser = new ArrayList<>();
        brokenCreateUser.add("testUser1");
        brokenCreateUser.add("{1}");
        brokenCreateUser.add("password");

        sendRequest(RequestTypes.CREATE_USER, workingCreateUser);
        sendRequest(RequestTypes.CREATE_USER, wrongCreateUser);
        sendRequest(RequestTypes.CREATE_USER, brokenCreateUser);

        // logout request tests
        ArrayList<String> workingLogout = new ArrayList<>();
        workingLogout.add(token);

        ArrayList<String> wrongLogout = new ArrayList<>();
        wrongLogout.add("Fake Token");

        ArrayList<String> brokenLogout = new ArrayList<>();
        brokenLogout.add("admin");
        brokenLogout.add("admin");

        sendRequest(RequestTypes.LOGOUT, workingLogout);
        sendRequest(RequestTypes.LOGOUT, wrongLogout);
        sendRequest(RequestTypes.LOGOUT, brokenLogout);
    }

    public static String sendRequest(String requestType, ArrayList<String> parameters) throws IOException, ClassNotFoundException {
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

        String content = response.getContent().toString();

        // close the streams
        oos.close();
        ois.close();

        // close the connection
        socket.close();

        // return the content from the response
        return content;
    }
}
