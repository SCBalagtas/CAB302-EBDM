
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public int validation;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException{

        //Calls login screen on start. Loginscreen not static as we may need to call different instances
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.main();

    }

    public static void homePage() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        HomePage homePage = new HomePage();
        JFrame frame = new JFrame();
        homePage.main(frame);

    }

}


