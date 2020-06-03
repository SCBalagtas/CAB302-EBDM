import javax.swing.*;
import java.awt.*;

public class DefaultBillboard {
    public JPanel main(JFrame frame) {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("DEFAULT");
        label.setFont(new Font("Serif", Font.PLAIN, 120));
        panel.add(label);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setVisible(true);
        return panel;
    }

}
