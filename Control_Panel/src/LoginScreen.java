import Classes.Request;
import Classes.Response;
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
//        loginFrame.setSize(400,400); // sized for ease of testing. Needs to be fullscreen for final application

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

        submitButton.addActionListener(e -> {
            try {
                ArrayList<String> list = new ArrayList<>();
                list.add(usernameInput.getText());
                list.add(passwordInput.getText());
                Response response = SendRequest.serverRequest1(new Request(RequestTypes.LOGIN, list));
                if(response.getStatusCode() == 200) {
                    loginFrame.setVisible(false);
                    Main.homePage();
                    Session.SessionToken = (response.getContent()).toString();
                    Session.Username = usernameInput.getText();
                }
                else if(response.getStatusCode() == 401) {
                    warningLabel.setText("Incorrect username or password");
                }
            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
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
