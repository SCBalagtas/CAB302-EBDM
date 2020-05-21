import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen {
    public void main() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JFrame frame = new JFrame ("Testing Frame");
        frame.setSize(400,400); // for ease of testing. Needs to be fullscreen for final application

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();

        JLabel username = new JLabel( "Username/Email");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(username,c);

        JTextField usernameInput = new JTextField(20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(usernameInput,c);

        JLabel password = new JLabel( "Password");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(password,c);

        JPasswordField passwordInput = new JPasswordField(20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        panel.add(passwordInput,c);

        JButton Submit = new JButton();
        Submit.setText("Submit");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        panel.add(Submit,c);


        SubmitListener submitListener = new SubmitListener(usernameInput, passwordInput, frame);
        Submit.addActionListener(submitListener);   //Calls LoginScreen.SubmitListener when button is pressed. Passes password and username

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }


    /*
    class that will validates credentials with server on submit being pressed
     */

    public static class SubmitListener implements ActionListener {


        private JTextField Username;
        private JPasswordField Password;
        private JFrame Frame;


        public SubmitListener(JTextField usernameInput, JPasswordField passwordInput, JFrame frame) {
            Username = usernameInput;
            Password = passwordInput;
            Frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Frame.dispose();
            Main main = new Main();

            // call a method to validate credentials before calling homepage

            try {
                Main.homePage();
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }


        }
    }

}
