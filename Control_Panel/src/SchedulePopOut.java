import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchedulePopOut implements ActionListener {

    public SchedulePopOut() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        JFrame scheduleFrame = new JFrame("ScheduleFrame");

        JPanel panel = new JPanel();
        scheduleFrame.add(panel);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel SelectBillboard =  new JLabel("Select Billboard");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(SelectBillboard,c);

        JComboBox<String> billboardCombo = new JComboBox<>();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(billboardCombo,c);


        JLabel SelectDay = new JLabel("Select Day");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(SelectDay,c);

        JComboBox<String> dayCombo = new JComboBox<>();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(dayCombo,c);

        JLabel SelectTime = new JLabel("Select Time");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(SelectTime,c);

        JComboBox<String> timeCombo = new JComboBox<>();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(timeCombo,c);

        JLabel SelectRepeating = new JLabel("Select repeating interval");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(SelectRepeating,c);

        JComboBox<String> repeatCombo = new JComboBox<>();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(repeatCombo,c);

        JButton confirm = new JButton(new AbstractAction("Confirm") {
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduleFrame.dispose();
            }
        });

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 8;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(confirm,c);



        scheduleFrame.setVisible(true);
        scheduleFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        scheduleFrame.setLocationRelativeTo(null);
        scheduleFrame.setPreferredSize(new Dimension(400,400));
        scheduleFrame.pack();

    }
}
