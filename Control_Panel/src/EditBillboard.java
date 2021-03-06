import Classes.Request;
import Classes.Response;
import Constants.RequestTypes;
import Constants.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Author: Charles Cruz
 *
 * This class is the "Edit Billboard" form.
 */
public class EditBillboard extends JFrame implements Runnable{

    /**
     * Private fields required by the form.
     */
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

    /**
     * Constructor that pre-sets the initial values of the required fields and launches a new instance of the form.
     *
     * @param billboardName a string of the Billboard's name.
     * @param xmlString a string of the Billboard's XML.
     */
    public EditBillboard(String billboardName, String xmlString) {
        setTitle("Edit Billboard");

        Document xml;
        try {
            xml = loadXmlString(xmlString);
            xml.getDocumentElement().normalize();

            bbName = billboardName;

            Element element = (Element) xml.getElementsByTagName("billboard").item(0);
            if (element.getAttribute("background") != ""){
                bbBgColour = hexStringToRgb(element.getAttribute("background"));
            }

            int elementCount = xml.getElementsByTagName("message").getLength();
            if (elementCount > 0) {
                element = (Element) xml.getElementsByTagName("message").item(0);
                if (element.getTextContent() != "") {
                    bbMsg = element.getTextContent();
                }
                if (element.getAttribute("colour") != ""){
                    bbMsgColour = hexStringToRgb(element.getAttribute("colour"));
                }
            }

            elementCount = xml.getElementsByTagName("picture").getLength();
            if (elementCount > 0) {
                element = (Element) xml.getElementsByTagName("picture").item(0);
                if (element.getAttribute("url") != "") {
                    bbImgUrl = element.getAttribute("url");
                } else if (element.getAttribute("data") != "") {
                    bbImgData = element.getAttribute("data");
                }
            }

            elementCount = xml.getElementsByTagName("information").getLength();
            if (elementCount > 0) {
                element = (Element) xml.getElementsByTagName("information").item(0);
                if (element.getTextContent() != "") {
                    bbInfo = element.getTextContent();
                }
                if (element.getAttribute("colour") != ""){
                    bbInfoColour = hexStringToRgb(element.getAttribute("colour"));
                }
            }

            System.out.println(elementCount);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(this);
    }

    /**
     * Converts an XML String in to an XML Document object.
     *
     * @param xml a string of the Billboard' XML.
     * @return a parsed XML Document object.
     */
    public static Document loadXmlString(String xml) throws Exception
    {
        DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFac.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));

        Document doc = builder.parse(source);
        return doc;
    }

    /**
     * Converts a Hex string in to a Color object.
     *
     * @param string a colour code hex string following the format of "#AABBCC".
     * @return a Color object.
     */
    public static Color hexStringToRgb(String string) {
        return new Color(
                Integer.valueOf( string.substring( 1, 3 ), 16 ),
                Integer.valueOf( string.substring( 3, 5 ), 16 ),
                Integer.valueOf( string.substring( 5, 7 ), 16 ) );
    }

    /**
     * Update the components of the preview panel.
     *
     * @param previewPanel a JPanel object of the previewPanel.
     * @return a JPanel with updated previewPanel components.
     */
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
                //System.out.println("Malformed URL");
            } catch (IOException e) {
                //System.out.println("Can not load file");
            }

        }


        JLabel bblInfoLbl = new JLabel(bbInfo, SwingConstants.CENTER);
        bblInfoLbl.setFont(new Font("San Serif", Font.PLAIN, 16));
        bblInfoLbl.setForeground(bbInfoColour); // change the colour of the font
        previewPanel.add(bblInfoLbl);

        return previewPanel;
    }

    /**
     * Cleans the PreviewPanel and updates the components.
     */
    private void updatePreview(JPanel previewPanel) {
        previewPanel.removeAll();

        previewPanelComps(previewPanel);
        previewPanel.setBackground(bbBgColour);

        previewPanel.revalidate();
        previewPanel.repaint();
    }

    /**
     * Creates the full GUI
     */
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

        updatePreview(previewPanel);
        add(previewPanel, BorderLayout.PAGE_START);


        GridBagConstraints gbCons = new GridBagConstraints();
        gbCons.anchor = GridBagConstraints.LINE_END;

        // Main Panel Components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel, BorderLayout.PAGE_END);

        // Import and Export Buttons
        gbCons.gridy = 0;

        JButton importBtn = new JButton("Import");
        importBtn.setEnabled(false);
        gbCons.gridx = 3;
        mainPanel.add(importBtn, gbCons);

        JButton exportBtn = new JButton("Export");
        exportBtn.setEnabled(false);
        gbCons.gridx = 4;
        mainPanel.add(exportBtn, gbCons);

        gbCons.anchor = GridBagConstraints.LINE_START;

        // Billboard Name
        gbCons.gridy = 1;

        JLabel nameLbl = new JLabel("Name:");
        gbCons.gridx = 0;
        mainPanel.add(nameLbl, gbCons);

        JLabel nameTf = new JLabel(bbName);
        gbCons.gridx = 1;
        mainPanel.add(nameTf, gbCons);

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
                Color color = JColorChooser.showDialog(new JFrame(),"Change Message Colour", bbBgColour);

                if (color != null) {
                    bbBgColour = color;
                    updatePreview(previewPanel);
                    //System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }
            }
        });

        // Message
        gbCons.gridy = 3;

        JLabel msgLbl = new JLabel("Message:");
        gbCons.gridx = 0;
        mainPanel.add(msgLbl, gbCons);

        JTextField msgTf = new JTextField(50);
        msgTf.setText(bbMsg);
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
                Color color = JColorChooser.showDialog(new JFrame(),"Change Message Colour", bbMsgColour);

                if (color != null) {
                    bbMsgColour = color;
                    updatePreview(previewPanel);
                    //System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }

            }
        });


        // Image
        gbCons.gridy = 4;

        JTextField imgUrlTf = new JTextField("",50);
        imgUrlTf.setText(bbImgUrl);
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
        imgDataRb.setEnabled(false);
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
        infoTf.setText(bbInfo);
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
                Color color = JColorChooser.showDialog(new JFrame(),"Change Message Colour", bbInfoColour);

                if (color != null) {
                    bbInfoColour = color;
                    updatePreview(previewPanel);
                    //System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
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

        JButton editBtn = new JButton("Edit");
        gbCons.gridx = 4;
        mainPanel.add(editBtn, gbCons);

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bbXml = String.format(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<billboard background=\"#%02x%02x%02x\">",
                        bbBgColour.getRed(), bbBgColour.getGreen(), bbBgColour.getGreen()
                );

                if (bbMsgColour != null && bbMsg !="") {
                    bbXml = bbXml + String.format(
                            "<message colour=\"#%02x%02x%02x\">%s</message>",
                            bbMsgColour.getRed(), bbMsgColour.getGreen(), bbMsgColour.getGreen(), bbMsg
                    );
                } else if (bbMsg !="") {
                    bbXml = bbXml + String.format(
                            "<message>%s</message>",
                            bbMsg
                    );
                }

                if (bbImgUrl != "" && imgUrlRb.isSelected()) {
                    bbXml = bbXml + String.format(
                            "<picture url=\"%s\"/>",
                            bbImgUrl
                    );
                }

                if (bbInfoColour != null && bbInfo !="") {
                    bbXml = bbXml + String.format(
                            "<information colour=\"#%02x%02x%02x\">%s</information>",
                            bbInfoColour.getRed(), bbInfoColour.getGreen(), bbInfoColour.getGreen(), bbInfo
                    );
                } else if (bbInfo !="") {
                    bbXml = bbXml + String.format(
                            "<information>%s</information>",
                            bbInfo
                    );
                }

                bbXml = bbXml + "</billboard>";
                //System.out.println(bbXml);

                try {
                    ArrayList<String> parameters = new ArrayList<>();
                    parameters.add(bbName);
                    parameters.add(bbXml);
                    parameters.add(Session.SessionToken);

                    Response response = SendRequest.serverRequest1(new Request(RequestTypes.EDIT_BILLBOARD, parameters));

                    if (response.getStatusCode() == 200) {
                        int option = JOptionPane.showConfirmDialog(null, "Billboard Edited Successfully!",
                                "OK!", JOptionPane.DEFAULT_OPTION);

                        if (option == JOptionPane.OK_OPTION) {
                            // exit frame
                            dispose();
                        }

                    } else if (response.getStatusCode() == 400) {
                        // show dialog "Billboard already exists!"
                        JOptionPane.showMessageDialog(null, "Invalid Parameters!",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);

                    } else if (response.getStatusCode() == 500) {
                        // show dialog "Billboard already exists!"
                        JOptionPane.showMessageDialog(null, "Billboard does not exists!",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);

                    } else if (response.getStatusCode() == 403) {
                        // show dialog "Billboard already exists!"
                        JOptionPane.showMessageDialog(null, "Forbidden! Missing Permissions!",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);

                    } else if (response.getStatusCode() == 401) {
                        // show dialog "Unauthorised User"
                        int option = JOptionPane.showConfirmDialog(null, "Unauthorised User!",
                                "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

                        if (option == JOptionPane.OK_OPTION) {
                            // exit frame
                            try {
                                dispose();
                                LoginScreen loginScreen = new LoginScreen();
                                loginScreen.main();
                            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }



                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
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
