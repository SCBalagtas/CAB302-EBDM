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
    private String BBPicMode;

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
        submitBtnPanel.setBackground(Color.GREEN);

        getContentPane().add(previewPanel, BorderLayout.NORTH);
        getContentPane().add(mainJPanel, BorderLayout.WEST);
        getContentPane().add(submitBtnPanel, BorderLayout.SOUTH);

        GridBagConstraints gbCons = new GridBagConstraints();
        gbCons.anchor = GridBagConstraints.LINE_START;

        // Preview Panel Widgets:
        JLabel bbPreviewLbl = new JLabel("Preview:");
        previewPanel.add(bbPreviewLbl, BorderLayout.CENTER);

        JTextArea bbPreview = new JTextArea("XML Preview...");
        previewPanel.add(bbPreview, BorderLayout.CENTER);

        // Main Panel Widgets:
        // Billboard Author
        JLabel bbAuthorLbl = new JLabel("Author:");
        gbCons.gridx = 0;
        gbCons.gridy = 0;
        mainJPanel.add(bbAuthorLbl, gbCons);

        JLabel bbAuthor = new JLabel("Test Author");
        gbCons.gridx = 1;
        gbCons.gridy = 0;
        mainJPanel.add(bbAuthor, gbCons);

        // Import and Export Buttons
        JButton bbImportBtn = new JButton("Import");
        gbCons.gridx = 0;
        gbCons.gridy = 1;
        mainJPanel.add(bbImportBtn, gbCons);

        JButton bbExportBtn = new JButton("Export");
        gbCons.gridx = 1;
        gbCons.gridy = 1;
        mainJPanel.add(bbExportBtn, gbCons);

        // Billboard Name
        // label
        JLabel bbNameLbl = new JLabel("Name:");
        gbCons.gridx = 0;
        gbCons.gridy = 2;
        mainJPanel.add(bbNameLbl, gbCons);

        // text field
        JTextField bbNameTf = new JTextField(20);
        gbCons.gridx = 1;
        gbCons.gridy = 2;
        mainJPanel.add(bbNameTf, gbCons);

        // Billboard Message
        // label
        JLabel bbMsgLbl = new JLabel("Message:");
        gbCons.gridx = 0;
        gbCons.gridy = 3;
        mainJPanel.add(bbMsgLbl, gbCons);

        // text field
        JTextField bbMsgTf = new JTextField(20);
        gbCons.gridx = 1;
        gbCons.gridy = 3;
        mainJPanel.add(bbMsgTf, gbCons);

        JButton bbMsgCc = new JButton("Change Colour");
        gbCons.fill = GridBagConstraints.NONE;
        gbCons.gridx = 2;
        gbCons.gridy = 3;
        mainJPanel.add(bbMsgCc, gbCons);

        bbMsgCc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(new JFrame(),"Change Message Colour", Color.WHITE);

                if (color != null) {
                    System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }

            }
        });

        // Billboard Picture
        BBPicMode = "url"; // url mode is the initial value when uploading images

        JPanel bbPicUrlPanel = new JPanel();
        bbPicUrlPanel.setLayout(new GridBagLayout());
        bbPicUrlPanel.setBackground(Color.GREEN);
        gbCons.gridx = 2;
        gbCons.gridy = 4;
        mainJPanel.add(bbPicUrlPanel, gbCons);

        // URL label & text field
        JLabel bbPicUrlLbl = new JLabel("Image URL:");
        gbCons.gridx = 0;
        gbCons.gridy = 0;
        bbPicUrlPanel.add(bbPicUrlLbl, gbCons);

        JTextField bbPicUrlTf = new JTextField("", 40);
        gbCons.fill = GridBagConstraints.BOTH;
        gbCons.gridx = 1;
        gbCons.gridy = 0;
        bbPicUrlPanel.add(bbPicUrlTf, gbCons);


        JPanel bbPicDataPanel = new JPanel();
        bbPicDataPanel.setLayout(new GridBagLayout());
        bbPicDataPanel.setBackground(Color.GREEN);
        gbCons.gridx = 2;
        gbCons.gridy = 4;
        mainJPanel.add(bbPicDataPanel, gbCons);
        bbPicDataPanel.setVisible(false);

        JLabel bbUploadLbl = new JLabel("Image Location...");
        gbCons.gridx = 0;
        gbCons.gridy = 0;
        bbPicDataPanel.add(bbUploadLbl, gbCons);

        JButton bbUploadBtn = new JButton("Upload");
        gbCons.gridx = 1;
        gbCons.gridy = 0;
        bbPicDataPanel.add(bbUploadBtn, gbCons);

        // bbPic Options
        JPanel bbPicPanel = new JPanel();
        bbPicPanel.setLayout(new GridLayout(1,0));
        gbCons.gridx = 1;
        gbCons.gridy = 4;
        mainJPanel.add(bbPicPanel, gbCons);

        JRadioButton bbPicUrlRb = new JRadioButton("URL");
        bbPicUrlRb.setSelected(true);
        bbPicUrlRb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BBPicMode = "url";
                bbPicUrlPanel.setVisible(true);
                bbPicDataPanel.setVisible(false);

            }
        });


        JRadioButton bbPicDataRb = new JRadioButton("Upload");
        bbPicDataRb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BBPicMode = "data";
                bbPicUrlPanel.setVisible(false);
                bbPicDataPanel.setVisible(true);
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(bbPicUrlRb);
        group.add(bbPicDataRb);

        bbPicPanel.add(bbPicUrlRb);
        bbPicPanel.add(bbPicDataRb);


        // Billboard Information
        // label
        JLabel bbInfoLbl = new JLabel("Information:");
        gbCons.gridx = 0;
        gbCons.gridy = 5;
        mainJPanel.add(bbInfoLbl, gbCons);

        // text field
        JTextField bbInfoTf = new JTextField(20);
        gbCons.gridx = 1;
        gbCons.gridy = 5;
        mainJPanel.add(bbInfoTf, gbCons);

        JButton bbInfoCc = new JButton("Change Colour");
        gbCons.fill = GridBagConstraints.NONE;
        gbCons.gridx = 2;
        gbCons.gridy = 5;
        mainJPanel.add(bbInfoCc, gbCons);

        bbInfoCc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(new JFrame(),"Change Information Colour", Color.WHITE);

                if (color != null) {
                    System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }

            }
        });


        // Submit Panel Widgets:
        // Create and Cancel Buttons
        JButton bbCancelBtn = new JButton("Cancel");
        gbCons.gridx = 0;
        gbCons.gridy = 0;
        submitBtnPanel.add(bbCancelBtn, gbCons);

        bbCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                dispose();
            }
        });

        JButton bbCreateBtn = new JButton("Create Billboard");
        gbCons.gridx = 1;
        gbCons.gridy = 0;
        submitBtnPanel.add(bbCreateBtn, gbCons);

        bbCreateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();

                // validate fields

                // exit window
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


