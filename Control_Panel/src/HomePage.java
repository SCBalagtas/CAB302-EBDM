import Classes.Request;
import Classes.Response;
import Constants.RequestTypes;
import Constants.Session;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Felix Savins
 *
 * Class creates instance of Homepage
 */

public class HomePage {

    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();

        JButton createBillboardButton = new JButton("Create Billboard");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(createBillboardButton, c);


        createBillboardButton.addActionListener(e -> {
            new CreateBillboard();
        });




        JButton listBillboardButton = new JButton("List Billboards");
        c.gridy = 2;
        panel.add(listBillboardButton, c);

        listBillboardButton.addActionListener(e -> {
            ArrayList<String> list = new ArrayList<>();
            list.add(Session.SessionToken);
            try {
                Response scheduleResponse = SendRequest.serverRequest1(new Request(RequestTypes.VIEW_SCHEDULE, list));
                Response listResponse = SendRequest.serverRequest1(new Request(RequestTypes.LIST_BILLBOARDS, list));
                if (scheduleResponse.getStatusCode() == 200 && listResponse.getStatusCode() == 200) {       //check if user has permission to view billboards and schedule
                    ListBillboards listBillboards = new ListBillboards();
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    try {
                        listBillboards.main(frame);     //Draw list of billboards
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | IOException ex) {
                        ex.printStackTrace();
                    }
                }

                else if(scheduleResponse.getStatusCode() == 401){       //If session token has expired
                    JOptionPane.showMessageDialog(null, "Session expired, Log in again",
                            "Session expired!",
                            JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.main();
                }

                if(scheduleResponse.getStatusCode() == 403) {       //If user does not have permission to view schedule show all billboards
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    ListPermissionlessBillboards listPermissionlessBillboards = new ListPermissionlessBillboards();
                    listPermissionlessBillboards.main(frame);           //Open alternate list view of billboards
                }

            } catch (IOException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });




        JButton scheduleBillboardButton = new JButton("Schedule Billboards");
        c.gridy = 3;
        panel.add(scheduleBillboardButton, c);

        scheduleBillboardButton.addActionListener(e -> {
            System.out.println("Schedule");
            ArrayList<String> list = new ArrayList<>();
            list.add(Session.SessionToken);
            try {
                Response scheduleResponse = SendRequest.serverRequest1(new Request(RequestTypes.VIEW_SCHEDULE, list));
                if (scheduleResponse.getStatusCode() == 200) {      //If user has permission to view schedule show schedule
                    ScheduleBillboards scheduleBillboards = new ScheduleBillboards();
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    try {
                        scheduleBillboards.main(frame);
                    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | IOException ex) {
                        ex.printStackTrace();
                    }
                }

                else if(scheduleResponse.getStatusCode() == 401){       //If session token has expired take user back to log in screen
                    JOptionPane.showMessageDialog(null, "Session expired, Log in again",
                            "Session expired!",
                            JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.main();

                }

                if(scheduleResponse.getStatusCode() == 403) {

                    JOptionPane.showMessageDialog(null, "You do not have permission to perform this action",
                            "Missing permissions!",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }


        });


        JButton createUserButton = new JButton("Create User");
        c.gridy = 4;
        panel.add(createUserButton, c);

        createUserButton.addActionListener(e -> {       //Open create user screen
            System.out.println("Create User");
            CreateUser createUser = new CreateUser();
            frame.getContentPane().removeAll();
            frame.repaint();
            try {
                createUser.main(frame);
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | IOException ex) {
                ex.printStackTrace();
            }

        });



        JButton editUserButton = new JButton("Edit User");
        c.gridy = 5;
        panel.add(editUserButton, c);

        editUserButton.addActionListener(e -> {     //Open edit user screen
            System.out.println("Edit");
            EditUsers editUsers = new EditUsers();
            frame.getContentPane().removeAll();
            frame.repaint();
            try {
                editUsers.main(frame);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });



        JButton logoutButton = new JButton("Logout");       //Send log out request to server and return user to the log in screen
        c.gridy = 6;
        panel.add(logoutButton, c);

        logoutButton.addActionListener(e -> {
            ArrayList<String> parameters = new ArrayList<>();
            parameters.add(Session.SessionToken);
            try {
                SendRequest.serverRequest1(new Request(RequestTypes.LOGOUT, parameters ));
                frame.dispose();
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.main();
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | IOException ex) {
                ex.printStackTrace();
            }
        });




        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(panel);
        frame.setVisible(true);
    }

}
