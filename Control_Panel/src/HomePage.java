import Classes.Request;
import Constants.RequestTypes;
import Constants.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Felix Savins
 *
 * Class creates instance of Homepage
 */

public class HomePage {

    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        //frame.setSize(800, 800); //Must be fullscreen for final application

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
            System.out.println("Create");
            // Close frame using Frame.dispose() and open another or change what's in this frame
        });




        JButton listBillboardButton = new JButton("List Billboards");
        c.gridy = 2;
        panel.add(listBillboardButton, c);

        listBillboardButton.addActionListener(e -> {
            System.out.println("List");
            // Close frame using Frame.dispose() and open another or change what's in this frame
            ListBillboards listBillboards = new ListBillboards();
            frame.getContentPane().removeAll();
            frame.repaint();
            try {
                listBillboards.main(frame);
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | IOException ex) {
                ex.printStackTrace();
            }
        });




        JButton scheduleBillboardButton = new JButton("Schedule Billboards");
        c.gridy = 3;
        panel.add(scheduleBillboardButton, c);

        scheduleBillboardButton.addActionListener(e -> {
            System.out.println("Schedule");
            // Close frame using Frame.dispose() and open another or change what's in this frame
            ScheduleBillboards scheduleBillboards = new ScheduleBillboards();
            frame.getContentPane().removeAll();
            frame.repaint();
            try {
                scheduleBillboards.main(frame);
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });




        JButton createUserButton = new JButton("Create User");
        c.gridy = 4;
        panel.add(createUserButton, c);

        createUserButton.addActionListener(e -> {
            System.out.println("Create User");
            // Close frame using Frame.dispose() and open another or change what's in this frame
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

        editUserButton.addActionListener(e -> {
            System.out.println("Edit");
            // Close frame using Frame.dispose() and open another or change what's in this frame
            EditUsers editUsers = new EditUsers();
            frame.getContentPane().removeAll();
            frame.repaint();
            try {
                editUsers.main(frame);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });



        JButton logoutButton = new JButton("Logout");
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
