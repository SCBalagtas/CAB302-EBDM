import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.css.Rect;
import org.xml.sax.InputSource;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.lang.*;

public class DrawBillboard {

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

    public static Document loadXmlString(String xml) throws Exception
    {
        DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFac.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));
        return builder.parse(source);
    }

    public static Color hexStringToRgb(String string) {
        return new Color(
                Integer.valueOf( string.substring( 1, 3 ), 16 ),
                Integer.valueOf( string.substring( 3, 5 ), 16 ),
                Integer.valueOf( string.substring( 5, 7 ), 16 ) );
    }

    public void main(String billboardName, String xmlString) {

        Document xml;
        try {
            xml = loadXmlString(xmlString);
            xml.getDocumentElement().normalize();

            bbName = billboardName;
            Element element = (Element) xml.getElementsByTagName("billboard").item(0);
            System.out.println(element.getAttribute("background"));
            System.out.println(xml.getTextContent());

            try {
                bbBgColour = hexStringToRgb(element.getAttribute("background"));
            }
            catch (IndexOutOfBoundsException ignored) {
                bbBgColour = Color.WHITE;
            }
            System.out.println("Billboard Colour " + bbBgColour);

            try {
                bbMsg = xml.getElementsByTagName("message").item(0).getTextContent();
            } catch (NullPointerException ignored) { }


            element = (Element) xml.getElementsByTagName("message").item(0);

            try {
                bbMsgColour = hexStringToRgb(element.getAttribute("colour"));
            }
            catch (IndexOutOfBoundsException | NullPointerException ignored ) {
                bbMsgColour = Color.BLACK;
            }

            element = (Element) xml.getElementsByTagName("picture").item(0);

            try {
                if (element.getAttribute("url") != "") {
                    bbImgUrl = element.getAttribute("url");
                    System.out.println(bbImgUrl);
                } else if (element.getAttribute("data") != "") {
                    bbImgData = element.getAttribute("data");
                    System.out.println(bbImgData);
                }
            } catch (NullPointerException ignored){}

            try {
                bbInfo = xml.getElementsByTagName("information").item(0).getTextContent();
            } catch (NullPointerException ignored){}

            element = (Element) xml.getElementsByTagName("information").item(0);

            try {
                bbInfoColour = hexStringToRgb(element.getAttribute("colour"));
            } catch(Exception ignored){bbInfoColour = Color.BLACK;}


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JPanel DrawWindow() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));

        JLabel bbMsgLbl = new JLabel(bbMsg, SwingConstants.CENTER);

        int messageFont = 100;

        try {
            if (bbMsg.length() < 20) {
                messageFont = 200;
            } else if (bbMsg.length() < 30) {
                messageFont = 110;
            } else {
                messageFont = (200 / (bbMsg.length() / 15));
                System.out.println((bbMsg.length() / 15));
                System.out.println("200 / " + bbMsg.length() + " / 15");
            }

            bbMsgLbl.setFont(new Font("San Serif", Font.BOLD, messageFont));
            bbMsgLbl.setForeground(bbMsgColour); // change the colour of the font

            panel.add(bbMsgLbl);

        } catch(Exception ignored) {
            JLabel blankLabel = new JLabel();
            panel.add(blankLabel);
        }


        panel.setBackground(bbBgColour);




        if (bbImgUrl != ""){

            Image image = null;
            URL url = null;
            try {
                url = new URL(bbImgUrl);
                image = ImageIO.read(url);


                Image newImage = image.getScaledInstance(100,100,Image.SCALE_DEFAULT);


                JLabel picture = new JLabel((new ImageIcon(newImage)));

                panel.add(picture);


            } catch (MalformedURLException e) {
                //System.out.println("Malformed URL");
            } catch (IOException e) {
                //System.out.println("Can not load file");
            }

        }

        try {
            JLabel bblInfoLbl = new JLabel(bbInfo, SwingConstants.CENTER);
            bblInfoLbl.setFont(new Font("San Serif", Font.PLAIN, messageFont / 2));
            System.out.println(bbInfoColour);
            bblInfoLbl.setForeground(bbInfoColour); // change the colour of the font

            panel.add(bblInfoLbl);
        }
        catch (Exception ignored) {}


        return panel;

    }
}
