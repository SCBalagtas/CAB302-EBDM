import Classes.Request;
import Classes.Response;
import Constants.RequestTypes;
import Constants.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Felix Savins
 *
 * Creates frame to allow scheduling of a new billboard
 */

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


        LocalDate now = LocalDate.now();
        for (int day = 0; day < 7; day++) {
            dayCombo.addItem(now.plusDays(day).toString());
        }



        JLabel SelectTime = new JLabel("Select Time");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(SelectTime,c);


        JComboBox<String> timeHourCombo = new JComboBox<>();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.weighty = 0;
        panel.add(timeHourCombo,c);

        for (int hour = 0; hour < 24; hour++) {
            timeHourCombo.addItem(String.format("%02d", hour));
        }

        JComboBox<String> timeMinCombo = new JComboBox<>();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 5;
        c.weightx = 1;
        c.weighty = 0;
        panel.add(timeMinCombo,c);

        for (int min = 0; min < 60; min++) {
            timeMinCombo.addItem(String.format("%02d", min));
        }

        JComboBox<String> timeSecCombo = new JComboBox<>();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 5;
        c.weightx = 1;
        c.weighty = 0;
        panel.add(timeSecCombo,c);

        for (int sec = 0; sec < 60; sec++) {
            timeSecCombo.addItem(String.format("%02d", sec));
        }

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


        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e1 -> {
                scheduleFrame.dispose();
                String scheduledTime = dayCombo.getSelectedItem() + "T" +  timeHourCombo.getSelectedItem() + ":" + timeMinCombo.getSelectedItem() + ":" + timeSecCombo.getSelectedItem() + ".000000";
                System.out.println(scheduledTime);

            ArrayList<String> list = new ArrayList<>();
            list.add(Session.SessionToken);
            list.add(scheduledTime);

            try {
                Response response = SendRequest.serverRequest1(new Request(RequestTypes.CREATE_SCHEDULE, list));
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }


        });

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 8;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(confirmButton,c);

        scheduleFrame.setVisible(true);
        scheduleFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        scheduleFrame.setLocationRelativeTo(null);
        scheduleFrame.setPreferredSize(new Dimension(400,400));
        scheduleFrame.pack();

    }
}
