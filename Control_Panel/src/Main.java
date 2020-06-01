
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Felix Savins
 *
 * Calls initial log in screen and can be used to create new homescreen instance
 */

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException{

        LoginScreen loginScreen = new LoginScreen();
        loginScreen.main();

    }

    public static void homePage() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        HomePage homePage = new HomePage();
        JFrame frame = new JFrame();
        homePage.main(frame);

    }

}


