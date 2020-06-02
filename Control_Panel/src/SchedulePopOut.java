import Classes.Billboard;
import Classes.Request;
import Classes.Response;
import Classes.Schedule;
import Constants.RequestTypes;
import Constants.Session;

import javax.naming.InterruptedNamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.spi.BreakIteratorProvider;
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

        ArrayList<String> list = new ArrayList<>();
        list.add(Session.SessionToken);


        Response listResponse = null;
        try {
            listResponse = SendRequest.serverRequest1(new Request(RequestTypes.LIST_BILLBOARDS, list));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        ArrayList<Billboard> billboardList = (ArrayList<Billboard>) listResponse.getContent();

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

        JLabel billboardWarning = new JLabel("");
        c.gridx = 1;
        panel.add(billboardWarning,c);

        JComboBox<String> billboardCombo = new JComboBox<>();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(billboardCombo,c);

        billboardCombo.addItem("");
        for (Billboard billboard : billboardList) {
            billboardCombo.addItem(billboard.getBillboardName());
        }


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

        repeatCombo.addItem("Don't repeat");
        repeatCombo.addItem("Daily");
        repeatCombo.addItem("Hourly");

        for (int min = 1; min < 60; min++) {
            repeatCombo.addItem("Every: " + String.format("%02d", min) + " min");
        }

        JLabel duration = new JLabel("Duration in minutes");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 8;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(duration,c);

        JLabel durationWarning = new JLabel("");
        c.gridx = 1;
        panel.add(durationWarning,c);

        JTextField durationInput = new JTextField();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 9;
        c.weightx = 0;
        c.weighty = 0;
        panel.add(durationInput,c);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e1 -> {

            String FreqType;

                durationWarning.setText("");
                billboardWarning.setText("");
                String scheduledTime = dayCombo.getSelectedItem() + "T" +  timeHourCombo.getSelectedItem() + ":" + timeMinCombo.getSelectedItem() + ":" + timeSecCombo.getSelectedItem() + ".000000";
                ArrayList<String> schedule = new ArrayList<>();

                if(billboardCombo.getSelectedItem() != "") {

                    if(!durationInput.getText().equals("") && !durationInput.getText().equals("0")){

                        if(repeatCombo.getSelectedItem() == "Don't repeat"){

                            System.out.println("Correct");
                            schedule.add(billboardCombo.getSelectedItem().toString());
                            schedule.add(scheduledTime);
                            schedule.add(durationInput.getText());
                            schedule.add(Session.SessionToken);
                            try {
                                Response response = SendRequest.serverRequest1(new Request(RequestTypes.CREATE_SCHEDULE, schedule));
                                System.out.println(response.getStatusCode());
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }

                        }

                        else {
                            int foo;
                            try {
                                foo = Integer.parseInt(durationInput.getText());
                            }
                            catch (NumberFormatException e2) {
                                foo = -10;
                                durationWarning.setText("Duration must be integer");
                            }

                            System.out.println("INDEX " + (repeatCombo.getSelectedIndex()-2));
                            System.out.println(foo);

                            if ((repeatCombo.getSelectedIndex() - 2) >= foo & foo > 0) {

                                schedule.add(billboardCombo.getSelectedItem().toString());
                                schedule.add(scheduledTime);
                                schedule.add(durationInput.getText());
                                schedule.add(Session.SessionToken);

                                if(repeatCombo.getSelectedItem() == "Hourly") {
                                    schedule.add("Hourly");
                                    schedule.add("");
                                    try {
                                        Response response = SendRequest.serverRequest1(new Request(RequestTypes.CREATE_SCHEDULE, schedule));
                                        System.out.println(response.getStatusCode());
                                    } catch (IOException | ClassNotFoundException ex) {
                                        ex.printStackTrace();
                                    }


                                }
                                if(repeatCombo.getSelectedItem() == "Daily") {
                                    schedule.add("Daily");
                                    schedule.add("");
                                    try {
                                        Response response = SendRequest.serverRequest1(new Request(RequestTypes.CREATE_SCHEDULE, schedule));
                                        System.out.println(response.getStatusCode());
                                    } catch (IOException | ClassNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                else {
                                    schedule.add("Minutes");
                                    schedule.add(repeatCombo.getSelectedItem().toString().substring(7,9));
                                    try {
                                        Response response = SendRequest.serverRequest1(new Request(RequestTypes.CREATE_SCHEDULE, schedule));
                                        System.out.println(response.getStatusCode());
                                    } catch (IOException | ClassNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                            else{
                                durationWarning.setText("duration must be a positive integer less then frequency");
                            }


                        }
                    }
                    else {
                        durationWarning.setText("Duration cannot be zero");
                    }
                }
                else {
                    billboardWarning.setText("You must select a billboard");
                }



//            try {
//                Response response = SendRequest.serverRequest1(new Request(RequestTypes.CREATE_SCHEDULE, schedule));
//                System.out.println(response.getStatusCode());
//            } catch (IOException | ClassNotFoundException ex) {
//                ex.printStackTrace();
//            }


        });

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 10;
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
