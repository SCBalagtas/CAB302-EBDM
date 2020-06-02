import Classes.Response;
import Classes.Request;
import Constants.RequestTypes;
import Classes.*;
import Constants.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateUser {
    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        ArrayList<String> permissions = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel newUserName = new JLabel("New Users name: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(newUserName,c);

        JTextField username = new JTextField(50);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(username,c);

        JLabel newPassword = new JLabel("New Users password: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(newPassword,c);

        JTextField password = new JTextField(50);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(password,c);

        JRadioButton EditUsersPermission = new JRadioButton("Edit Users");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        panel.add(EditUsersPermission,c);

        JRadioButton CreateBillboardsPermission = new JRadioButton("Create Billboards");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        panel.add(CreateBillboardsPermission,c);

        JRadioButton EditAllPermission = new JRadioButton("Edit All Billboards");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        panel.add(EditAllPermission,c);

        JRadioButton SchedulePermission = new JRadioButton("Schedule Billboards");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 6;
        panel.add(SchedulePermission,c);


        System.out.println(Session.SessionToken + "\n" + Session.Username);


        JButton addUser = new JButton(new AbstractAction("AddUser") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

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
                    System.out.println(permissions.toString());

                    ArrayList<String> userCredentials = new ArrayList<>();
                    userCredentials.add(username.getText());
                    userCredentials.add(permissions.toString());
                    PasswordHash passwordHash = new PasswordHash(password.getText());
                    userCredentials.add(passwordHash.getHexString());
                    userCredentials.add(Session.SessionToken);
                    System.out.println(userCredentials);

                    Response response = SendRequest.serverRequest1(new Request(RequestTypes.CREATE_USER, userCredentials));
                    System.out.println(response.getStatusCode());
                } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });

        c.gridx = 0;
        c.gridy = 7;
        panel.add(addUser,c);

        JButton home = new JButton();
        home.setText("home");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        panel.add(home);

        home.addActionListener(e -> {       //Returns to home Screen
            System.out.println("home");
            // Close frame using Frame.dispose() and open another or change what's in this frame

            frame.getContentPane().removeAll();
            frame.repaint();

            HomePage homePage = new HomePage();
            try {
                homePage.main(frame);
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }

}
