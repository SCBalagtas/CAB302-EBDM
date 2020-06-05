import javax.swing.*;
import java.awt.*;


/**
 * @author Felix Savins
 *
 * draws default billboard for display
 */

public class DefaultBillboard {
    /**
     * @return default display panel
     */
    public JPanel main() {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("No Scheduled Billboard");
        label.setFont(new Font("Serif", Font.PLAIN, 120));
        panel.add(label);
        return panel;
    }

}
