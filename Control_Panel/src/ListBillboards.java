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
 * Class creates frame showing list of current billboards on server
 */

public class ListBillboards {


    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        // Show billboards on server
        // Panel with two labels, five buttons, two scroll-panes containing a list each
        // Layout is GridBagLayout


        ArrayList<String> list = new ArrayList<>();
        list.add(Session.SessionToken);


        Response billboardResponse = SendRequest.serverRequest1(new Request(RequestTypes.LIST_BILLBOARDS, list));

        ArrayList<Billboard> billboardArrayList = (ArrayList<Billboard>) billboardResponse.getContent();

        for (Billboard billboard : billboardArrayList) {
            System.out.println(billboard.getBillboardName());
        }


        Response scheduleResponse = SendRequest.serverRequest1(new Request(RequestTypes.VIEW_SCHEDULE, list));

        ArrayList<Schedule> scheduleArrayList = (ArrayList<Schedule>) scheduleResponse.getContent();

        for (Schedule schedule : scheduleArrayList) {
            System.out.println(schedule.getBillboardName());
        }

        System.out.println(scheduleResponse.getContent());

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


        DefaultListModel<String> listModel_s = new DefaultListModel<>();      //Temporary until database is up
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
        });

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(edit_s,c);



        JButton delete_s = new JButton("Delete");
        delete_s.addActionListener(e -> {
            System.out.println(listScheduled.getSelectedValue() + " : Scheduled" + " : Delete");
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


        DefaultListModel<String> listModel_u = new DefaultListModel<>();      //Temporary until database is up


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


            ArrayList<String> parameters = new ArrayList<>();
            parameters.add(listUnscheduled.getSelectedValue());
            parameters.add(Session.SessionToken);

            try {
                Response response = SendRequest.serverRequest1(new Request(RequestTypes.GET_BILLBOARD, parameters));

                if (response.getStatusCode() == 200) {
                    String xml = (String) response.getContent();

                    new EditBillboard(listUnscheduled.getSelectedValue(), xml);

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
        });


        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(delete_u,c);



        for(Billboard billboard : billboardArrayList) {
            String currentBillboard = billboard.getBillboardName();
            for(Schedule schedule : scheduleArrayList) {
                if(currentBillboard.equals(schedule.getBillboardName())) {
                    listModel_s.addElement(currentBillboard);
                    break;
                }
            }
        }

        for(Billboard billboard : billboardArrayList) {
            if(!listModel_s.contains(billboard.getBillboardName())) {
                listModel_u.addElement(billboard.getBillboardName());
            }
        }


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }


    }



