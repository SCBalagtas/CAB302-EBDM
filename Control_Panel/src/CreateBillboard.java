import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateBillboard extends JFrame implements Runnable {
    private String BBAuthor;
    private String BBName;
    private String BBMsg;
    private String BBImgUrl;
    private String BBImgData;
    private String BBInfo;

    public CreateBillboard(String title) throws HeadlessException {
        super(title);
    }

    private void CreateGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setVisible(true);
        setMinimumSize(new Dimension(1280, 720));
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        setLayout(new BorderLayout());

        JPanel previewPanel = new JPanel();
        previewPanel.setLayout(new BorderLayout());

        JPanel mainJPanel = new JPanel();
        mainJPanel.setLayout(new GridBagLayout());

        JPanel submitBtnPanel = new JPanel();
        submitBtnPanel.setLayout(new GridBagLayout());

        previewPanel.setBackground(Color.BLUE);
        mainJPanel.setBackground(Color.RED);
        submitBtnPanel.setBackground(Color.GREEN);

        getContentPane().add(previewPanel, BorderLayout.NORTH);
        getContentPane().add(mainJPanel, BorderLayout.CENTER);
        getContentPane().add(submitBtnPanel, BorderLayout.SOUTH);

        GridBagConstraints gbCons = new GridBagConstraints();

        // Preview Panel Widgets:
        JLabel bbPreviewLbl = new JLabel("Preview:");
        previewPanel.add(bbPreviewLbl, BorderLayout.CENTER);

        JTextArea bbPreview = new JTextArea("XML Preview...");
        previewPanel.add(bbPreview, BorderLayout.CENTER);

        // Main Panel Widgets:
        // Billboard Author
        JLabel bbAuthorLbl = new JLabel("Author:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 0;
        mainJPanel.add(bbAuthorLbl, gbCons);

        JLabel bbAuthor = new JLabel("Test Author");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 0;
        mainJPanel.add(bbAuthor, gbCons);

        // Import and Export Buttons
        JButton bbImportBtn = new JButton("Import");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 1;
        mainJPanel.add(bbImportBtn, gbCons);

        JButton bbExportBtn = new JButton("Export");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 1;
        mainJPanel.add(bbExportBtn, gbCons);

        // Billboard Name
        // label
        JLabel bbNameLbl = new JLabel("Name:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 2;
        mainJPanel.add(bbNameLbl, gbCons);

        // text field
        JTextField bbNameTf = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 2;
        mainJPanel.add(bbNameTf, gbCons);

        // Billboard Message
        // label
        JLabel bbMsgLbl = new JLabel("Message:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 3;
        mainJPanel.add(bbMsgLbl, gbCons);

        // text field
        JTextField bbMsgTf = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 3;
        mainJPanel.add(bbMsgTf, gbCons);

        // Billboard Picture (URL)
        // label
        JLabel bbPicUrlLbl = new JLabel("Image Url:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 4;
        mainJPanel.add(bbPicUrlLbl, gbCons);

        // text field
        JTextField bbPicUrlTf = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 4;
        mainJPanel.add(bbPicUrlTf, gbCons);

        // Billboard Picture (URL)
        // label
        JLabel bbInfoLbl = new JLabel("Information:");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 5;
        mainJPanel.add(bbInfoLbl, gbCons);

        // text field
        JTextField bbInfoTf = new JTextField(20);
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 5;
        mainJPanel.add(bbInfoTf, gbCons);

        // Submit Panel Widgets:
        // Create and Cancel Buttons
        JButton bbCancelBtn = new JButton("Cancel");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 0;
        gbCons.gridy = 0;
        submitBtnPanel.add(bbCancelBtn, gbCons);

        bbCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                JButton sourceJButton = (JButton) source;
                System.out.println(sourceJButton.getText());
                dispose();
            }
        });

        JButton bbCreateBtn = new JButton("Create Billboard");
        gbCons.fill = GridBagConstraints.HORIZONTAL;
        gbCons.gridx = 1;
        gbCons.gridy = 0;
        submitBtnPanel.add(bbCreateBtn, gbCons);

        bbCreateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                JButton sourceJButton = (JButton) source;
                System.out.println(sourceJButton.getText());
                dispose();
            }
        });
    }

    @Override
    public void run() {
        try {
            CreateGUI();
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
        SwingUtilities.invokeLater(new CreateBillboard("Create Billboard"));
    }
}


