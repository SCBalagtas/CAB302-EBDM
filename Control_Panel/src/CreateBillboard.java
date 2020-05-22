import javax.swing.*;
import java.awt.*;

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
        JLabel xmlPreview = new JLabel("XML Preview");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 0;
        //gbCons.weightx = 1;
        this.getContentPane().add(xmlPreview, gbCons);

        // Import XML Button
        JButton xmlImportBtn = new JButton("Import XML");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 1;
        this.getContentPane().add(xmlImportBtn, gbCons);

        // Export XML Button
        JButton xmlExportBtn = new JButton("Export XML");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 1;
        this.getContentPane().add(xmlExportBtn, gbCons);

        // Billboard Text
        // label
        JLabel billboardLabel = new JLabel("Billboard Text:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 2;
        this.getContentPane().add(billboardLabel, gbCons);

        // text field
        JTextField billboardTextField = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 2;
        this.getContentPane().add(billboardTextField, gbCons);

        // Text Colour
        // label
        JLabel textColorLabel = new JLabel("Text Colour:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 3;
        this.getContentPane().add(textColorLabel, gbCons);

        // color chooser
        JColorChooser textColor = new JColorChooser();
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 3;
        this.getContentPane().add(textColor, gbCons);

        // Background Colour
        // label
        JLabel billboardColorLabel = new JLabel("Text Colour:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 4;
        this.getContentPane().add(billboardColorLabel, gbCons);

        // color chooser
        JColorChooser billboardColor = new JColorChooser();
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 4;
        this.getContentPane().add(billboardColor, gbCons);

        // Set Image

        // Create Button
        JButton createBtn = new JButton("Create Billboard");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 5;
        this.getContentPane().add(createBtn, gbCons);

        // Cancel Button
        JButton cancelBtn = new JButton("Cancel");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 5;
        this.getContentPane().add(cancelBtn, gbCons);
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
