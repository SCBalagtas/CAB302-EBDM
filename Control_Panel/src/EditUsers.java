import Classes.Response;
import Constants.RequestTypes;
import Constants.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Request;

/**
 * @author Felix Savins
 *
 * This class creates frame that allows users to edit user permissions, and passwords.
 */

public class EditUsers {
    public void main(JFrame frame) throws IOException, ClassNotFoundException {

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel userLabel = new JLabel("Users");
        c.gridy = 0;
        c.gridx = 0;
        panel.add(userLabel,c);


        final List[] users = {generateList()};
        JList<String> userList = new JList(users[0].toArray());
        if(userList.toString().equals("[Missing Permissions]")) {
            System.out.println("missing permissions");
        }


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        panel.add(userList,c);



        JButton deleteUser = new JButton(new AbstractAction("Delete User") {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> parameters = new ArrayList<>();
                parameters.add(userList.getSelectedValue());
                parameters.add(Session.SessionToken);
                System.out.println(parameters);
                if(!userList.isSelectionEmpty()) {
                    try {
                        Response response = SendRequest.serverRequest1(new Request(RequestTypes.DELETE_USER, parameters ));
                        System.out.println(response.getStatusCode());
                        List users = generateList();
                        JList<String> userList = new JList(users.toArray());
                        generateList();


                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(deleteUser,c);





        JRadioButton EditUsersPermission = new JRadioButton("Edit Users");
        c.gridx = 0;
        c.gridy = 3;
        panel.add(EditUsersPermission,c);

        JRadioButton CreateBillboardsPermission = new JRadioButton("Create Billboards");
        c.gridy = 4;
        panel.add(CreateBillboardsPermission,c);

        JRadioButton EditAllPermission = new JRadioButton("Edit All Billboards");
        c.gridy = 5;
        panel.add(EditAllPermission,c);

        JRadioButton SchedulePermission = new JRadioButton("Schedule Billboards");
        c.gridy = 6;
        panel.add(SchedulePermission,c);

        JButton changePermissions = new JButton("Change Permissions");
        c.gridy = 7;
        panel.add(changePermissions,c);

        changePermissions.addActionListener(e -> {
            ArrayList<String> permissions = new ArrayList<>();
            if(EditUsersPermission.isSelected()) {
                permissions.add("0");
            }
            if(CreateBillboardsPermission.isSelected()) {
                permissions.add("1");
            }
            if(EditAllPermission.isSelected()) {
                permissions.add("2");
            }
            if (SchedulePermission.isSelected()) {
                permissions.add("3");
            }
            ArrayList<String> userCredentials = new ArrayList<>();
            userCredentials.add(userList.getSelectedValue());
            userCredentials.add(permissions.toString());
            userCredentials.add(Session.SessionToken);

            try {
                SendRequest.serverRequest1(new Request(RequestTypes.SET_PERMISSIONS, userCredentials));
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });


        JLabel newPassword = new JLabel("New Users password: ");
        c.gridy = 8;
        panel.add(newPassword,c);

        JTextField password = new JTextField(20);
        c.gridx = 1;
        c.gridy = 8;
        panel.add(password,c);

        JLabel passwordChangeLabel = new JLabel();
        c.gridy = 9;
        panel.add(passwordChangeLabel,c);

        JButton changePasswordButton = new JButton("Change Password");  //Currently all users can change the passwords of all users
        changePasswordButton.addActionListener(e -> {
            ArrayList<String> userCredentials = new ArrayList<>();
            userCredentials.add(userList.getSelectedValue());
            userCredentials.add(password.getText());
            userCredentials.add(Session.SessionToken);
            try {
                System.out.println(userCredentials.toString());
                Response response = SendRequest.serverRequest1(new Request(RequestTypes.SET_PASSWORD, userCredentials));
                System.out.println(response.getContent());
                if(response.getStatusCode() == 200) {
                    passwordChangeLabel.setText("Password successfully changed");
                }
                else if(response.getStatusCode() == 401) {
                    passwordChangeLabel.setText("You do not have permission to perform this action");
                }

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        c.gridx = 0;
        c.gridy = 9;
        panel.add(changePasswordButton,c);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);



    }

    public List generateList() throws IOException, ClassNotFoundException {

        ArrayList<String> list = new ArrayList<>();
        list.add(Session.SessionToken);
        Response response = SendRequest.serverRequest1(new Request(RequestTypes.LIST_USERS, list));
        if(response.getStatusCode() == 403) {
            ArrayList<String> missingList = new ArrayList<>();
            missingList.add(Session.Username);
            return missingList;
        }
        return new ArrayList<>(Arrays.asList(response.getContent().toString().split(", ")));
    }

}
