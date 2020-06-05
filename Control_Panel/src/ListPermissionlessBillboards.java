import Classes.Billboard;
import Classes.Billboard.*;
import Classes.Request;
import Classes.Response;
import Classes.Schedule;
import Constants.RequestTypes;
import Constants.Session;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author Felix Savins
 *
 * Class creates frame showing list of current billboards on server alternative to EditBillboards when user lacks permissions
 */

public class ListPermissionlessBillboards {


    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        // Show billboards on server
        // Layout is GridBagLayout


        ArrayList<String> list = new ArrayList<>();
        list.add(Session.SessionToken);


        Response billboardResponse = SendRequest.serverRequest1(new Request(RequestTypes.LIST_BILLBOARDS, list));       //Ask server for list of billboards

        ArrayList<Billboard> billboardArrayList = (ArrayList<Billboard>) billboardResponse.getContent();        // Add billboards to ArrayList

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c;

        JLabel billboards =  new JLabel("Billboards");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        billboards.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(billboards,c);

        JLabel deletionPrompt = new JLabel();
        c.gridx = 1;

        panel.add(deletionPrompt,c);


        JButton homeButton = new JButton();
        homeButton.setText("home");
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        panel.add(homeButton);

        homeButton.addActionListener(e -> {       //Returns to home Screen
            System.out.println("home");

            frame.getContentPane().removeAll();
            frame.repaint();

            HomePage homePage = new HomePage();
            try {
                homePage.main(frame);
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }

        });


        DefaultListModel<String> listModel_s = new DefaultListModel<>();
        ScrollPane scrollPane_s = new ScrollPane();
        JList<String> listScheduled = new JList<>(listModel_s);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.gridwidth = 3;
        panel.add(scrollPane_s,c);
        scrollPane_s.add(listScheduled);


        JButton edit_s = new JButton("Edit");
        edit_s.addActionListener(e -> {
            System.out.println(listScheduled.getSelectedValue() + " : Scheduled" + " : Edit");
            editSelectedBB(listScheduled.getSelectedValue(), frame);
        });

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(edit_s,c);



        JButton delete_s = new JButton("Delete");
        delete_s.addActionListener(e -> {
            try {
                ArrayList<String> deleteList = new ArrayList<>();
                deleteList.add(listScheduled.getSelectedValue());
                deleteList.add(Session.SessionToken);

                System.out.println();

                Response deleteResponse = SendRequest.serverRequest1(new Request(RequestTypes.DELETE_BILLBOARD, deleteList));       //Send request to server to delete selected billboard

                System.out.println(deleteResponse.getStatusCode());
                if(deleteResponse.getStatusCode() == 200) {

                    JOptionPane.showMessageDialog(null, "Billboard deleted!",
                            "Billboard deleted!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else if (deleteResponse.getStatusCode() == 403) {
                    JOptionPane.showMessageDialog(null, "You do not have permission to perform this action",
                            "Missing permission!",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (deleteResponse.getStatusCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Billboard does not exist",
                            "Billboard missing!",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (deleteResponse.getStatusCode() == 401) {
                    JOptionPane.showMessageDialog(null, "Session expired, Log in again",
                            "Session expired!",
                            JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.main();

                }

            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        });


        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(delete_s,c);








        for(Billboard billboard : billboardArrayList) {     //Add all billboards to viewable list
            String currentBillboard = billboard.getBillboardName();
                listModel_s.addElement(currentBillboard);
        }



        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void editSelectedBB(String selectedValue, JFrame frame) {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(selectedValue);
        parameters.add(Session.SessionToken);

        try {
            Response response = SendRequest.serverRequest1(new Request(RequestTypes.GET_BILLBOARD, parameters));

            if (response.getStatusCode() == 200) {
                String xml = (String) response.getContent();

                new EditBillboard(selectedValue, xml);

            } else if (response.getStatusCode() == 401) {
                // show dialog "Unauthorised! Please Log In!"
                int option = JOptionPane.showConfirmDialog(null, "Unauthorised! Please Log In!",
                        "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

                if (option == JOptionPane.OK_OPTION) {
                    // exit frame
                    try {
                        frame.dispose();
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

}
