import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateBillboard extends JFrame implements Runnable{
    private String author;

    private String bbName;
    private Color bbBgColour;
    private String bbMsg;
    private Color bbMsgColour;

    private enum ImgType {DATA, URL}

    private ImgType bbImgType;
    private String bbImgUrl;
    private String bbImgData;
    private String bbInfo;
    private Color bbInfoColour;
    private String bbPicMode;

    public CreateBillboard(String username, String title) throws HeadlessException {
        super(title);

        // Initial Values
        author = username;

        bbName = "";
        bbBgColour = Color.WHITE;
        bbMsg = "";
        bbMsgColour = Color.WHITE;
        bbImgType = ImgType.URL;
        bbImgUrl = "";
        bbImgData = "";
        bbInfo = "";
        bbInfoColour = Color.WHITE;
        bbPicMode = "";

        SwingUtilities.invokeLater(this);
    }

    // Update the components of the preview panel
    private JPanel previewPanelComps(JPanel previewPanel) {
        JLabel bbNameLbl = new JLabel(bbName, SwingConstants.CENTER);
        previewPanel.add(bbNameLbl);

        JLabel bbMsgLbl = new JLabel(bbMsg);
        previewPanel.add(bbMsgLbl);

        JLabel bblInfoLbl = new JLabel(bbInfo);
        previewPanel.add(bblInfoLbl);

        return previewPanel;
    }

    // Update the preview panel
    private void updatePreview(JPanel previewPanel) {
        previewPanel.removeAll();

        previewPanelComps(previewPanel);

        previewPanel.revalidate();
        previewPanel.repaint();
    }

    // Create the full GUI
    private void createGui() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        setVisible(true);
        setMinimumSize(new Dimension(1280, 720));
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setLayout( new GridLayout(0,1) );

        // Preview Panel Components
        JPanel previewPanel = new JPanel( new GridLayout(0, 1));
        previewPanel.setBackground(Color.WHITE);
        add(previewPanel);


        GridBagConstraints gbCons = new GridBagConstraints();
        gbCons.anchor = GridBagConstraints.LINE_END;

        // Main Panel Components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);

        // Import and Export Buttons
        gbCons.gridy = 0;

        JButton importBtn = new JButton("Import");
        gbCons.gridx = 3;
        mainPanel.add(importBtn, gbCons);

        /*
        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML", "xml");
                fileChooser.setFileFilter(xmlFilter);

                int result = fileChooser.showOpenDialog(fileChooser);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });
         */

        JButton exportBtn = new JButton("Export");
        gbCons.gridx = 4;
        mainPanel.add(exportBtn, gbCons);

        gbCons.anchor = GridBagConstraints.LINE_START;

        // Billboard Name
        gbCons.gridy = 1;

        JLabel nameLbl = new JLabel("Name:");
        gbCons.gridx = 0;
        mainPanel.add(nameLbl, gbCons);

        JTextField nameTf = new JTextField(30);
        gbCons.gridx = 1;
        mainPanel.add(nameTf, gbCons);

        nameTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                bbName = nameTf.getText();
                updatePreview(previewPanel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                bbName = nameTf.getText();
                updatePreview(previewPanel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                bbName = nameTf.getText();
                updatePreview(previewPanel);
            }
        });

        // Background Colour
        gbCons.gridy = 2;

        JLabel bgColLbl = new JLabel("Background Colour:");
        gbCons.gridx = 0;
        mainPanel.add(bgColLbl, gbCons);

        JButton bgColCc = new JButton("Change Colour");
        gbCons.gridx = 1;
        mainPanel.add(bgColCc, gbCons);

        bgColCc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(new JFrame(),"Change Message Colour", Color.WHITE);

                if (color != null) {
                    System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }
            }
        });

        // Message
        gbCons.gridy = 3;

        JLabel msgLbl = new JLabel("Message:");
        gbCons.gridx = 0;
        mainPanel.add(msgLbl, gbCons);

        JTextField msgTf = new JTextField(50);
        gbCons.gridx = 1;
        mainPanel.add(msgTf, gbCons);

        msgTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                bbMsg = msgTf.getText();
                updatePreview(previewPanel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                bbMsg = msgTf.getText();
                updatePreview(previewPanel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                bbMsg = msgTf.getText();
                updatePreview(previewPanel);
            }

        });

        JButton msgColCc = new JButton("Change Colour");
        gbCons.gridx = 2;
        mainPanel.add(msgColCc, gbCons);

        msgColCc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(new JFrame(),"Change Message Colour", Color.WHITE);

                if (color != null) {
                    System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }

            }
        });


        // Image
        gbCons.gridy = 4;

        JTextField imgUrlTf = new JTextField("",50);
        gbCons.gridx = 1;
        mainPanel.add(imgUrlTf, gbCons);

        JTextField imgDataTf = new JTextField("",50);
        gbCons.gridx = 1;
        mainPanel.add(imgDataTf, gbCons);
        imgDataTf.setVisible(false);

        JButton imgDataBtn = new JButton("Upload");
        gbCons.gridx = 2;
        mainPanel.add(imgDataBtn, gbCons);
        imgDataBtn.setVisible(false);


        JPanel imgOptionsPanel = new JPanel();
        imgOptionsPanel.setLayout(new GridLayout(1,0));
        gbCons.gridx = 0;
        mainPanel.add(imgOptionsPanel, gbCons);

        JRadioButton imgUrlRb = new JRadioButton("URL");
        imgUrlRb.setSelected(true);
        imgUrlRb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgUrlTf.setVisible(true);
                imgDataTf.setVisible(false);
                imgDataBtn.setVisible(false);
            }
        });


        JRadioButton imgDataRb = new JRadioButton("Upload");
        imgDataRb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgUrlTf.setVisible(false);
                imgDataTf.setVisible(true);
                imgDataBtn.setVisible(true);
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(imgUrlRb);
        group.add(imgDataRb);

        imgOptionsPanel.add(imgUrlRb);
        imgOptionsPanel.add(imgDataRb);

        // Information
        gbCons.gridy = 5;

        JLabel infoLbl = new JLabel("Information:");
        gbCons.gridx = 0;
        mainPanel.add(infoLbl, gbCons);

        JTextField infoTf = new JTextField(50);
        gbCons.gridx = 1;
        mainPanel.add(infoTf, gbCons);

        infoTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                bbInfo = infoTf.getText();
                updatePreview(previewPanel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                bbInfo = infoTf.getText();
                updatePreview(previewPanel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                bbInfo = infoTf.getText();
                updatePreview(previewPanel);
            }

        });

        JButton infoColCc = new JButton("Change Colour");
        gbCons.gridx = 2;
        mainPanel.add(infoColCc, gbCons);

        infoColCc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(new JFrame(),"Change Message Colour", Color.WHITE);

                if (color != null) {
                    System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }

            }
        });

        // Main Panel Components
        JPanel submitPanel = new JPanel(new FlowLayout());
        add(submitPanel);


        JButton cancelBtn = new JButton("Cancel");
        submitPanel.add(cancelBtn);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton createBtn = new JButton("Create");
        submitPanel.add(createBtn);

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // validate variables

                // Validate with the server

                // show success/fail dialogues

                //dispose
                dispose();
            }
        });
    }

    @Override
    public void run() {
        try {
            createGui();
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

}
