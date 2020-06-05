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
 * Class creates frame showing list of current billboards on server. Only used if user has permission to view all billboards and edit
 */

public class ListBillboards {


    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        // Show billboards on server
        // Panel with two labels, five buttons, two scroll-panes containing a list each
        // Layout is GridBagLayout


        ArrayList<String> list = new ArrayList<>();
        list.add(Session.SessionToken);


        Response billboardResponse = SendRequest.serverRequest1(new Request(RequestTypes.LIST_BILLBOARDS, list));       //make request to server for all billboards

        if(billboardResponse.getStatusCode() == 200) {

        }
        else {
            JOptionPane.showMessageDialog(null, "Session expired, Log in again",
                    "Session expired!",
                    JOptionPane.ERROR_MESSAGE);
        }

        ArrayList<Billboard> billboardArrayList = (ArrayList<Billboard>) billboardResponse.getContent();        //Create arraylist of returned billboards

        for (Billboard billboard : billboardArrayList) {
            System.out.println(billboard.getBillboardName());
        }


        Response scheduleResponse = SendRequest.serverRequest1(new Request(RequestTypes.VIEW_SCHEDULE, list));      //make request to server for the schedule

        ArrayList<Schedule> scheduleArrayList = (ArrayList<Schedule>) scheduleResponse.getContent();        //Create arraylist of returned schedule


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c;

        JLabel scheduledLabel =  new JLabel("Scheduled Billboards");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        scheduledLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(scheduledLabel,c);

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
            // Close frame using Frame.dispose() and open another or change what's in this frame

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
            editSelectedBB(listScheduled.getSelectedValue(), frame);        //open frame to edit selected billboard
        });

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(edit_s,c);



        JButton delete_s = new JButton("Delete");
        delete_s.addActionListener(e -> {
            System.out.println(listScheduled.getSelectedValue() + " : Scheduled" + " : Delete");
            try {
                ArrayList<String> deleteList = new ArrayList<>();
                deleteList.add(listScheduled.getSelectedValue());
                deleteList.add(Session.SessionToken);

                System.out.println();

                Response deleteResponse = SendRequest.serverRequest1(new Request(RequestTypes.DELETE_BILLBOARD, deleteList));           //Send request to server to delete selected billboard

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














        JLabel Unscheduled =  new JLabel("Unscheduled Billboards");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        Unscheduled.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(Unscheduled,c);


        DefaultListModel<String> listModel_u = new DefaultListModel<>();


        ScrollPane scrollPaneUnscheduled = new ScrollPane();
        JList<String> listUnscheduled = new JList<>(listModel_u);
        scrollPaneUnscheduled.add(listUnscheduled);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.gridwidth = 3;
        panel.add(scrollPaneUnscheduled,c);


        JButton edit_u = new JButton("Edit");
        edit_u.addActionListener(e -> {
            System.out.println(listUnscheduled.getSelectedValue() + " : Unscheduled" + " : Edit");
            editSelectedBB(listUnscheduled.getSelectedValue(), frame);          //Open frame to edit selected billboard
        });

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(edit_u,c);


        JButton delete_u = new JButton("Delete");
        delete_u.addActionListener(e -> {
            System.out.println(listUnscheduled.getSelectedValue() + " : Unscheduled" + " : Delete");
            try {
                ArrayList<String> deleteList = new ArrayList<>();
                deleteList.add(listUnscheduled.getSelectedValue());
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
        c.gridy = 5;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(delete_u,c);



        for(Billboard billboard : billboardArrayList) {     //Add scheduled billboards to scheduled billboard list
            String currentBillboard = billboard.getBillboardName();
            for(Schedule schedule : scheduleArrayList) {
                if(currentBillboard.equals(schedule.getBillboardName())) {
                    listModel_s.addElement(currentBillboard);
                    break;
                }
            }
        }

        for(Billboard billboard : billboardArrayList) {         //add non scheduled billboards to non scheduled list
            if(!listModel_s.contains(billboard.getBillboardName())) {
                listModel_u.addElement(billboard.getBillboardName());
            }
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

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    }



