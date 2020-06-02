import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {


    public void main() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JFrame frame = new JFrame("Testing Frame");
        frame.setSize(800, 800); //Must be fullscreen for final application

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();

        JButton CreateBillboard = new JButton();
        CreateBillboard.setText("Create Billboard");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(CreateBillboard, c);

        CreateBillboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Create");

                CreateBillboard createBillboard = new CreateBillboard("testUser");

                // call a method to validate credentials before calling homepage
                /*
                try {
                    Main.createBillboard();
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }

                 */

                // Close frame using Frame.dispose() and open another or change what's in this frame
            }
        });


        JButton ListBillboard = new JButton();
        ListBillboard.setText("List Billboards");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(ListBillboard, c);

        ListBillboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("List");
                // Close frame using Frame.dispose() and open another or change what's in this frame
            }
        });


        JButton ScheduleBillboard = new JButton();
        ScheduleBillboard.setText("Schedule Billboards");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(ScheduleBillboard, c);

        ScheduleBillboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Schedule");
                // Close frame using Frame.dispose() and open another or change what's in this frame
            }
        });

        JButton EditUsers = new JButton();
        EditUsers.setText("Edit Users");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        panel.add(EditUsers, c);

        EditUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit");
                // Close frame using Frame.dispose() and open another or change what's in this frame
            }
        });


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }


}
