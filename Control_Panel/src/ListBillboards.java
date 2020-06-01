import Classes.Request;
import Classes.Response;
import Constants.RequestTypes;
import Constants.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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
        Response response = SendRequest.serverRequest1(new Request(RequestTypes.LIST_BILLBOARDS, list));
        System.out.println(response.getContent());

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
            System.out.println(listScheduled.getSelectedIndex() + " : Scheduled" + " : Edit");
        });

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(edit_s,c);



        JButton delete_s = new JButton("Delete");
        delete_s.addActionListener(e -> {
            System.out.println(listScheduled.getSelectedIndex() + " : Scheduled" + " : Delete");
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
            System.out.println(listUnscheduled.getSelectedIndex() + " : Unscheduled" + " : Edit");
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
            System.out.println(listUnscheduled.getSelectedIndex() + " : Unscheduled" + " : Delete");
        });


        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(delete_u,c);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }


    }



