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
 * A simple test client class to test the server responses. Comment out the necessary tests to view test results.
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

        // create user request tests
        ArrayList<String> workingCreateUser = new ArrayList<>();
        workingCreateUser.add("testUser1");
        workingCreateUser.add("[1]");
        workingCreateUser.add("password");
        workingCreateUser.add(token);

        ArrayList<String> wrongCreateUser = new ArrayList<>();
        wrongCreateUser.add("testUser1");
        wrongCreateUser.add("[1]");
        wrongCreateUser.add("password");
        wrongCreateUser.add("Fake Token");

        ArrayList<String> brokenCreateUser = new ArrayList<>();
        brokenCreateUser.add("testUser1");
        brokenCreateUser.add("[1]");
        brokenCreateUser.add("password");

        sendRequest(RequestTypes.CREATE_USER, workingCreateUser);
        sendRequest(RequestTypes.CREATE_USER, wrongCreateUser);
        sendRequest(RequestTypes.CREATE_USER, brokenCreateUser);

        // list users request tests
        ArrayList<String> workingListUsers = new ArrayList<>();
        workingListUsers.add(token);

        ArrayList<String> wrongListUsers = new ArrayList<>();
        wrongListUsers.add("Fake Token");

        ArrayList<String> brokenListUsers = new ArrayList<>();
        brokenListUsers.add("Fake Token");
        brokenListUsers.add("admin");

        sendRequest(RequestTypes.LIST_USERS, workingListUsers);
        sendRequest(RequestTypes.LIST_USERS, wrongListUsers);
        sendRequest(RequestTypes.LIST_USERS, brokenListUsers);

        // get user permissions request tests
        ArrayList<String> workingGetPermissions = new ArrayList<>();
        workingGetPermissions.add("testUser1");
        workingGetPermissions.add(token);

        ArrayList<String> nonexistentGetPermissions = new ArrayList<>();
        nonexistentGetPermissions.add("anotherTestUser");
        nonexistentGetPermissions.add(token);

        ArrayList<String> wrongGetPermissions = new ArrayList<>();
        wrongGetPermissions.add("testUser1");
        wrongGetPermissions.add("Fake Token");

        ArrayList<String> brokenGetPermissions = new ArrayList<>();
        brokenGetPermissions.add("testUser1");

        sendRequest(RequestTypes.GET_PERMISSIONS, workingGetPermissions);
        sendRequest(RequestTypes.GET_PERMISSIONS, nonexistentGetPermissions);
        sendRequest(RequestTypes.GET_PERMISSIONS, wrongGetPermissions);
        sendRequest(RequestTypes.GET_PERMISSIONS, brokenGetPermissions);

        // set user permissions request tests
        ArrayList<String> workingSetPermissions = new ArrayList<>();
        workingSetPermissions.add("testUser1");
        workingSetPermissions.add("[1, 2, 3]");
        workingSetPermissions.add(token);

        ArrayList<String> nonexistentSetPermissions = new ArrayList<>();
        nonexistentSetPermissions.add("anotherTestUser");
        nonexistentSetPermissions.add("[1, 2, 3]");
        nonexistentSetPermissions.add(token);

        ArrayList<String> wrongSetPermissions = new ArrayList<>();
        wrongSetPermissions.add("admin");
        wrongSetPermissions.add("[1, 2, 3]");
        wrongSetPermissions.add(token);

        ArrayList<String> brokenSetPermissions = new ArrayList<>();
        brokenSetPermissions.add("admin");
        brokenSetPermissions.add("[1, 2, 3]");

        sendRequest(RequestTypes.SET_PERMISSIONS, workingSetPermissions);
        sendRequest(RequestTypes.SET_PERMISSIONS, nonexistentSetPermissions);
        sendRequest(RequestTypes.SET_PERMISSIONS, wrongSetPermissions);
        sendRequest(RequestTypes.SET_PERMISSIONS, brokenSetPermissions);

        // set user password request tests
        ArrayList<String> workingSetOwnPassword = new ArrayList<>();
        workingSetOwnPassword.add("admin");
        workingSetOwnPassword.add("admin");
        workingSetOwnPassword.add(token);

        ArrayList<String> workingSetUserPassword = new ArrayList<>();
        workingSetUserPassword.add("testUser1");
        workingSetUserPassword.add("password123");
        workingSetUserPassword.add(token);

        ArrayList<String> nonexistentSetUserPassword = new ArrayList<>();
        nonexistentSetUserPassword.add("anotherTestUser");
        nonexistentSetUserPassword.add("password123");
        nonexistentSetUserPassword.add(token);

        ArrayList<String> wrongSetUserPassword = new ArrayList<>();
        wrongSetUserPassword.add("testUser1");
        wrongSetUserPassword.add("password123");
        wrongSetUserPassword.add("Fake Token");

        ArrayList<String> brokenSetUserPassword = new ArrayList<>();
        brokenSetUserPassword.add("testUser1");
        brokenSetUserPassword.add("password123");

        sendRequest(RequestTypes.SET_PASSWORD, workingSetOwnPassword);
        sendRequest(RequestTypes.SET_PASSWORD, workingSetUserPassword);
        sendRequest(RequestTypes.SET_PASSWORD, nonexistentSetUserPassword);
        sendRequest(RequestTypes.SET_PASSWORD, wrongSetUserPassword);
        sendRequest(RequestTypes.SET_PASSWORD, brokenSetUserPassword);

        // delete user request tests
        ArrayList<String> workingDeleteUser = new ArrayList<>();
        workingDeleteUser.add("testUser1");
        workingDeleteUser.add(token);

        ArrayList<String> nonexistentDeleteUser = new ArrayList<>();
        nonexistentDeleteUser.add("anotherTestUser");
        nonexistentDeleteUser.add(token);

        ArrayList<String> wrongDeleteUser = new ArrayList<>();
        wrongDeleteUser.add("admin");
        wrongDeleteUser.add(token);

        ArrayList<String> brokenDeleteUser = new ArrayList<>();
        brokenDeleteUser.add("testUser1");

        sendRequest(RequestTypes.DELETE_USER, workingDeleteUser);
        sendRequest(RequestTypes.DELETE_USER, nonexistentDeleteUser);
        sendRequest(RequestTypes.DELETE_USER, wrongDeleteUser);
        sendRequest(RequestTypes.DELETE_USER, brokenDeleteUser);

        // create billboard request tests
        ArrayList<String> workingCreateBillboard = new ArrayList<>();
        workingCreateBillboard.add("testBillboard1");
        workingCreateBillboard.add("XMLcode");
        workingCreateBillboard.add(token);

        ArrayList<String> wrongCreateBillboard = new ArrayList<>();
        wrongCreateBillboard.add("testBillboard1");
        wrongCreateBillboard.add("XMLcode");
        wrongCreateBillboard.add("Fake Token");

        ArrayList<String> brokenCreateBillboard = new ArrayList<>();
        brokenCreateBillboard.add("testBillboard1");
        brokenCreateBillboard.add("XMLcode");

        sendRequest(RequestTypes.CREATE_BILLBOARD, workingCreateBillboard);
        sendRequest(RequestTypes.CREATE_BILLBOARD, wrongCreateBillboard);
        sendRequest(RequestTypes.CREATE_BILLBOARD, brokenCreateBillboard);

        // edit billboard request tests
        ArrayList<String> workingEditBillboard = new ArrayList<>();
        workingEditBillboard.add("testBillboard1");
        workingEditBillboard.add("moreXMLcode");
        workingEditBillboard.add(token);

        ArrayList<String> nonexistentEditBillboard = new ArrayList<>();
        nonexistentEditBillboard.add("admin");
        nonexistentEditBillboard.add("moreXMLcode");
        nonexistentEditBillboard.add(token);

        ArrayList<String> wrongEditBillboard = new ArrayList<>();
        wrongEditBillboard.add("testBillboard1");
        wrongEditBillboard.add("moreXMLcode");
        wrongEditBillboard.add("Fake Token");

        ArrayList<String> brokenEditBillboard = new ArrayList<>();
        brokenEditBillboard.add("testBillboard1");
        brokenEditBillboard.add("moreXMLcode");

        sendRequest(RequestTypes.EDIT_BILLBOARD, workingEditBillboard);
        sendRequest(RequestTypes.EDIT_BILLBOARD, nonexistentEditBillboard);
        sendRequest(RequestTypes.EDIT_BILLBOARD, wrongEditBillboard);
        sendRequest(RequestTypes.EDIT_BILLBOARD, brokenEditBillboard);

        // get billboard request tests
        ArrayList<String> workingGetBillboard = new ArrayList<>();
        workingGetBillboard.add("testBillboard1");
        workingGetBillboard.add(token);

        ArrayList<String> nonexistentGetBillboard = new ArrayList<>();
        nonexistentGetBillboard.add("admin");
        nonexistentGetBillboard.add(token);

        ArrayList<String> wrongGetBillboard = new ArrayList<>();
        wrongGetBillboard.add("testBillboard1");
        wrongGetBillboard.add("Fake Token");

        ArrayList<String> brokenGetBillboard = new ArrayList<>();
        brokenGetBillboard.add("testBillboard1");

        sendRequest(RequestTypes.GET_BILLBOARD, workingGetBillboard);
        sendRequest(RequestTypes.GET_BILLBOARD, nonexistentGetBillboard);
        sendRequest(RequestTypes.GET_BILLBOARD, wrongGetBillboard);
        sendRequest(RequestTypes.GET_BILLBOARD, brokenGetBillboard);

        // list billboard request tests
        ArrayList<String> workingListBillboards = new ArrayList<>();
        workingListBillboards.add(token);

        ArrayList<String> wrongListBillboards = new ArrayList<>();
        wrongListBillboards.add("Fake Token");

        ArrayList<String> brokenListBillboards = new ArrayList<>();
        brokenListBillboards.add("Fake Token");
        brokenListBillboards.add("admin");

        sendRequest(RequestTypes.LIST_BILLBOARDS, workingListBillboards);
        sendRequest(RequestTypes.LIST_BILLBOARDS, wrongListBillboards);
        sendRequest(RequestTypes.LIST_BILLBOARDS, brokenListBillboards);

        // create schedule request tests
        ArrayList<String> workingCreateSchedule = new ArrayList<>();
        workingCreateSchedule.add("testBillboard1");
        workingCreateSchedule.add("2020-05-31T23:30:00");
        workingCreateSchedule.add("30");
        workingCreateSchedule.add(token);

        ArrayList<String> workingCreateScheduleWithFreq = new ArrayList<>();
        workingCreateScheduleWithFreq.add("testBillboard1");
        workingCreateScheduleWithFreq.add("2020-05-31T23:30:00");
        workingCreateScheduleWithFreq.add("30");
        workingCreateScheduleWithFreq.add(token);
        workingCreateScheduleWithFreq.add("Minutes");
        workingCreateScheduleWithFreq.add("30");

        ArrayList<String> nonexistentBillboardCreateSchedule = new ArrayList<>();
        nonexistentBillboardCreateSchedule.add("anotherTestBillboard");
        nonexistentBillboardCreateSchedule.add("2020-05-31T23:30:00");
        nonexistentBillboardCreateSchedule.add("30");
        nonexistentBillboardCreateSchedule.add(token);

        ArrayList<String> wrongCreateSchedule = new ArrayList<>();
        wrongCreateSchedule.add("testBillboard1");
        wrongCreateSchedule.add("2020-05-31T23:30:00");
        wrongCreateSchedule.add("30");
        wrongCreateSchedule.add("Fake Token");

        ArrayList<String> brokenCreateSchedule = new ArrayList<>();
        brokenCreateSchedule.add("testBillboard1");
        brokenCreateSchedule.add("2020-05-31T23:30:00");
        brokenCreateSchedule.add("30");

        sendRequest(RequestTypes.CREATE_SCHEDULE, workingCreateSchedule);
        sendRequest(RequestTypes.CREATE_SCHEDULE, workingCreateScheduleWithFreq);
        sendRequest(RequestTypes.CREATE_SCHEDULE, nonexistentBillboardCreateSchedule);
        sendRequest(RequestTypes.CREATE_SCHEDULE, wrongCreateSchedule);
        sendRequest(RequestTypes.CREATE_SCHEDULE, brokenCreateSchedule);

        // view schedule request tests
        ArrayList<String> workingViewSchedule = new ArrayList<>();
        workingViewSchedule.add(token);

        ArrayList<String> wrongViewSchedule = new ArrayList<>();
        wrongViewSchedule.add("Fake Token");

        ArrayList<String> brokenViewSchedule = new ArrayList<>();
        brokenViewSchedule.add("Fake Token");
        brokenViewSchedule.add("admin");

        sendRequest(RequestTypes.VIEW_SCHEDULE, workingViewSchedule);
        sendRequest(RequestTypes.VIEW_SCHEDULE, wrongViewSchedule);
        sendRequest(RequestTypes.VIEW_SCHEDULE, brokenViewSchedule);

        // delete schedule request tests
        ArrayList<String> workingDeleteSchedule = new ArrayList<>();
        workingDeleteSchedule.add("testBillboard1");
        workingDeleteSchedule.add("2020-05-31T23:30:00");
        workingDeleteSchedule.add(token);

        ArrayList<String> nonexistentDeleteSchedule = new ArrayList<>();
        nonexistentDeleteSchedule.add("anotherTestBillboard");
        nonexistentDeleteSchedule.add("2020-05-31T23:30:00");
        nonexistentDeleteSchedule.add(token);

        ArrayList<String> wrongDeleteSchedule = new ArrayList<>();
        wrongDeleteSchedule.add("anotherTestBillboard");
        wrongDeleteSchedule.add("2020-05-31T23:30:00");
        wrongDeleteSchedule.add("Fake Token");

        ArrayList<String> brokenDeleteSchedule = new ArrayList<>();
        brokenDeleteSchedule.add("anotherTestBillboard");
        brokenDeleteSchedule.add("2020-05-31T23:30:00");

        sendRequest(RequestTypes.DELETE_SCHEDULE, workingDeleteSchedule);
        sendRequest(RequestTypes.DELETE_SCHEDULE, nonexistentDeleteSchedule);
        sendRequest(RequestTypes.DELETE_SCHEDULE, wrongDeleteSchedule);
        sendRequest(RequestTypes.DELETE_SCHEDULE, brokenDeleteSchedule);

        // delete billboard request tests
        ArrayList<String> workingDeleteBillboard = new ArrayList<>();
        workingDeleteBillboard.add("testBillboard1");
        workingDeleteBillboard.add(token);

        ArrayList<String> nonexistentDeleteBillboard = new ArrayList<>();
        nonexistentDeleteBillboard.add("admin");
        nonexistentDeleteBillboard.add(token);

        ArrayList<String> wrongDeleteBillboard = new ArrayList<>();
        wrongDeleteBillboard.add("testBillboard1");
        wrongDeleteBillboard.add("Fake Token");

        ArrayList<String> brokenDeleteBillboard = new ArrayList<>();
        brokenDeleteBillboard.add("testBillboard1");

        sendRequest(RequestTypes.DELETE_BILLBOARD, workingDeleteBillboard);
        sendRequest(RequestTypes.DELETE_BILLBOARD, nonexistentDeleteBillboard);
        sendRequest(RequestTypes.DELETE_BILLBOARD, wrongDeleteBillboard);
        sendRequest(RequestTypes.DELETE_BILLBOARD, brokenDeleteBillboard);

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
