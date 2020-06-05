import Classes.Request;
import Classes.Response;
import Constants.RequestTypes;
import Constants.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Felix Savins
 *
 * main class creates frame with input fields for username and password and submit button
 */

public class LoginScreen {

    /**
     *
     * @throws ClassNotFoundException
     * @throws UnsupportedLookAndFeelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void main() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        JFrame loginFrame = new JFrame ("Login Frame");

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();

        JLabel usernameLabel = new JLabel( "Username");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(usernameLabel,c);

        JTextField usernameInput = new JTextField(20);
        c.gridy = 2;
        panel.add(usernameInput,c);

        JLabel passwordLabel = new JLabel( "Password");
        c.gridy = 3;
        panel.add(passwordLabel,c);

        JPasswordField passwordInput = new JPasswordField(20);
        c.gridy = 4;
        panel.add(passwordInput,c);

        JLabel warningLabel = new JLabel( "");
        c.gridy = 6;
        panel.add(warningLabel,c);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {     //When submit button is pressed hash password and send credentials to server for validation
            try {
                ArrayList<String> list = new ArrayList<>();
                list.add(usernameInput.getText());

                PasswordHash passwordHash = new PasswordHash(passwordInput.getText());
                list.add(passwordHash.getHexString());

                System.out.println(passwordHash.getHexString());

                Response response = SendRequest.serverRequest1(new Request(RequestTypes.LOGIN, list));      //Send a log in request to server
                if(response.getStatusCode() == 200) {       //draw homescreen and add current session token to Session constants class
                    loginFrame.setVisible(false);
                    Main.homePage();
                    Session.SessionToken = (response.getContent()).toString();
                    Session.Username = usernameInput.getText();
                }
                else if(response.getStatusCode() == 401) {
                    warningLabel.setText("Incorrect username or password");
                }

            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                warningLabel.setText("Problem connecting to server");
            }


        });

        c.gridy = 5;
        panel.add(submitButton,c);

        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loginFrame.add(panel);
        loginFrame.setVisible(true);

    }

}
