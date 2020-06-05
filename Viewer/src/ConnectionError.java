import javax.swing.*;
import java.awt.*;

/**
 * @author Felix Savins
 * returns panel for connection error screen
 */

public class ConnectionError {
    public JPanel main() {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Connection Error");
        label.setFont(new Font("Serif", Font.PLAIN, 120));
        panel.add(label);

        return panel;
    }
}
