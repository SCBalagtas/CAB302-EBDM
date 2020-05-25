import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListBillboards {

    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        // Show billboards on server
        // Panel with two labels, five buttons, two scroll-panes containing a list each
        // Layout is GridBagLayout

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel Scheduled =  new JLabel("Scheduled Billboards");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        Scheduled.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(Scheduled,c);

        JButton home = new JButton();
        home.setText("home");
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        panel.add(home);

        home.addActionListener(e -> {       //Returns to home Screen
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

        List("ITem One", listModel_s);
        List("ITem two", listModel_s);
        List("ITem three", listModel_s);
        List("ITem four", listModel_s);
        List("ITem One", listModel_s);
        List("ITem two", listModel_s);
        List("ITem three", listModel_s);


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


        JButton edit_s = new JButton(new AbstractAction("Edit") {       //Will return the selected item from Scheduled list with "edit" tag --Temporary
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(listScheduled.getSelectedIndex() + " : Scheduled" + " : Edit");
            }
        });

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(edit_s,c);


        JButton delete_s = new JButton(new AbstractAction("Delete") {       //Will return the selected item from Scheduled list with "delete" tag --Temporary
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(listScheduled.getSelectedIndex() + " : Scheduled" + " : Delete");
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


        DefaultListModel<String> listModel_u = new DefaultListModel<>();      //Temporary until database is up


        List("ITem One u", listModel_u);
        List("ITem two u", listModel_u);
        List("ITem three u", listModel_u);
        List("ITem four u", listModel_u);


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


        JButton edit_u = new JButton(new AbstractAction("Edit") {       //Will return the selected item from Unscheduled list with "edit" tag --Temporary
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(listUnscheduled.getSelectedIndex() + " : Unscheduled" + " : Edit");
            }
        });

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.gridwidth = 1;
        panel.add(edit_u,c);


        JButton delete_u = new JButton(new AbstractAction("Delete") {           //Will return the selected item from Unscheduled list with "delete" tag --Temporary
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(listUnscheduled.getSelectedIndex() + " : Unscheduled" + " : Delete");
            }
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

    public void List(String billboardName, DefaultListModel<String> model) {
        for(int i = 0; i < model.getSize(); i++) {      //Will read all entries in database and put in right list
                                                            // therefore index returned will be correct database index
        }
        model.addElement(billboardName);
    }

    }



