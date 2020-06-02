import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
        bbMsgColour = Color.BLACK;
        bbImgType = ImgType.URL;
        bbImgUrl = "";
        bbImgData = "";
        bbInfo = "";
        bbInfoColour = Color.BLACK;
        bbPicMode = "";

        SwingUtilities.invokeLater(this);
    }

    // Update the components of the preview panel
    private JPanel previewPanelComps(JPanel previewPanel) {
        JLabel bbMsgLbl = new JLabel(bbMsg, SwingConstants.CENTER);
        bbMsgLbl.setFont(new Font("San Serif", Font.BOLD, 25));
        bbMsgLbl.setForeground(bbMsgColour); // change the colour of the font
        previewPanel.add(bbMsgLbl);


        if (bbImgUrl != ""){

        Image image = null;
        URL url = null;
        try {
            url = new URL(bbImgUrl);
            image = ImageIO.read(url);

            JLabel bbImgLbl = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
            bbImgLbl.setMaximumSize(new Dimension(480, 360));
            bbImgLbl.setSize(new Dimension(480, 360));
            previewPanel.add(bbImgLbl);

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
        } catch (IOException e) {
            System.out.println("Can not load file");
        }

        }


        JLabel bblInfoLbl = new JLabel(bbInfo, SwingConstants.CENTER);
        bblInfoLbl.setFont(new Font("San Serif", Font.PLAIN, 16));
        bblInfoLbl.setForeground(bbInfoColour); // change the colour of the font
        previewPanel.add(bblInfoLbl);

        return previewPanel;
    }

    // Update the preview panel
    private void updatePreview(JPanel previewPanel) {
        previewPanel.removeAll();

        previewPanelComps(previewPanel);
        previewPanel.setBackground(bbBgColour);

        previewPanel.revalidate();
        previewPanel.repaint();
    }

    // Create the full GUI
    private void createGui() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        setVisible(true);
        setMinimumSize(new Dimension(1280, 720));
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setLayout( new BorderLayout());

        // Preview Panel Components
        JPanel previewPanel = new JPanel( new GridLayout(0, 1));
        previewPanel.setBackground(Color.WHITE);

        Dimension dimension = new Dimension(720, 480);
        previewPanel.setMinimumSize(dimension);
        previewPanel.setPreferredSize(dimension);
        previewPanel.setMaximumSize(dimension);

        add(previewPanel, BorderLayout.PAGE_START);


        GridBagConstraints gbCons = new GridBagConstraints();
        gbCons.anchor = GridBagConstraints.LINE_END;

        // Main Panel Components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel, BorderLayout.PAGE_END);

        // Import and Export Buttons
        gbCons.gridy = 0;

        JButton importBtn = new JButton("Import");
        gbCons.gridx = 3;
        mainPanel.add(importBtn, gbCons);

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
                    bbBgColour = color;
                    updatePreview(previewPanel);
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
                    bbMsgColour = color;
                    updatePreview(previewPanel);
                    System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }

            }
        });


        // Image
        gbCons.gridy = 4;

        JTextField imgUrlTf = new JTextField("",50);
        gbCons.gridx = 1;
        mainPanel.add(imgUrlTf, gbCons);

        imgUrlTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                bbImgUrl = imgUrlTf.getText();
                updatePreview(previewPanel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                bbImgUrl = imgUrlTf.getText();
                updatePreview(previewPanel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                bbImgUrl = imgUrlTf.getText();
                updatePreview(previewPanel);
            }
        });

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
                    bbInfoColour = color;
                    updatePreview(previewPanel);
                    System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }

            }
        });

        gbCons.gridy = 6;
        JButton cancelBtn = new JButton("Cancel");
        gbCons.gridx = 3;
        mainPanel.add(cancelBtn, gbCons);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton createBtn = new JButton("Create");
        gbCons.gridx = 4;
        mainPanel.add(createBtn, gbCons);

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
