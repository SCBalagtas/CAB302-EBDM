import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateBillboard extends JFrame implements Runnable {
    private int WIDTH = 1280;
    private int HEIGHT = 720;

    JPanel mainPanel;

    public CreateBillboard(String title) throws HeadlessException {
        super(title);
    }

    private void createGUI() throws ClassNotFoundException,
            UnsupportedLookAndFeelException,
            InstantiationException,
            IllegalAccessException {

        setVisible(true);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //mainPanel
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GREEN);
        setLayout(new GridBagLayout());

        GridBagConstraints gbCons = new GridBagConstraints();

        /// Swing components

        // XML Preview
        // Import XML Button
        // Export XML Button
        // Background Colour
        // Message Colour
        // Information Colour

        // Billboard Name
        // label
        JLabel bbNameLabel = new JLabel("Name:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 2;
        this.getContentPane().add(bbNameLabel, gbCons);

        // text field
        JTextField bbName = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 2;
        this.getContentPane().add(bbName, gbCons);

        // Message Text
        // label
        JLabel bbMsgLabel = new JLabel("Message:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 3;
        this.getContentPane().add(bbMsgLabel, gbCons);

        // text field
        JTextField bbMsg = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 3;
        this.getContentPane().add(bbMsg, gbCons);

        // Picture URL/DATA
        // label
        JLabel bbImgLabel = new JLabel("URL:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 4;
        this.getContentPane().add(bbImgLabel, gbCons);

        // text field
        JTextField bbImg = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 4;
        this.getContentPane().add(bbImg, gbCons);

        // Information Text
        // label
        JLabel bbInfoLabel = new JLabel("Information:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 5;
        this.getContentPane().add(bbInfoLabel, gbCons);

        // text field
        JTextField bbInfo = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 5;
        this.getContentPane().add(bbInfo, gbCons);

        // Create Button
        JButton createBtn = new JButton("Create Billboard");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 6;
        this.getContentPane().add(createBtn, gbCons);

        // Cancel Button
        JButton cancelBtn = new JButton("Cancel");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 6;
        this.getContentPane().add(cancelBtn, gbCons);

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bbNameValue = bbName.getText();
                String bbMsgValue = bbMsg.getText();
                String bbImgValue = bbImg.getText();
                String bbInfoValue = bbInfo.getText();

                System.out.println(String.format("Creating Billboard...\n" +
                        "Name: %s\n" +
                        "Message: %s\n" +
                        "Image: %s\n" +
                        "Info: %s\n", bbNameValue, bbMsgValue, bbImgValue, bbInfoValue));

            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bbName.setText("");
                bbMsg.setText("");
                bbImg.setText("");
                bbInfo.setText("");

                System.out.println("TextFields Reset!");
            }
        });
    }

    @Override
    public void run() {
        try {
            createGUI();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CreateBillboard("Add Billboard"));
    }

}
