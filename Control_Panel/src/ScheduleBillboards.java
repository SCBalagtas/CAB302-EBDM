import Classes.Billboard;
import Classes.Request;
import Classes.Response;
import Classes.Schedule;
import Constants.RequestTypes;
import Constants.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import java.util.concurrent.Flow;

/**
 * @author Felix Savins
 *
 * Creates frame that shows scheduled billboards and allows billboards to be scheduled
 */

public class ScheduleBillboards {
    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toString().substring(0,10));

        ArrayList<String> userCredentials = new ArrayList<>();
        userCredentials.add(Session.SessionToken);
        Response response = SendRequest.serverRequest1(new Request(RequestTypes.VIEW_SCHEDULE, userCredentials));

        ArrayList<Schedule> arrayList = (ArrayList<Schedule>) response.getContent();


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton home = new JButton();
        home.setText("home");
        c.gridx = 0;
        c.gridy = 1;
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





        JPanel D1 = new JPanel();
        JLabel D1Label = new JLabel(now.getDayOfWeek().toString());
        DefaultListModel<String> D1_listModel = new DefaultListModel<>();
        JList D1List = new JList<>(D1_listModel);
        ScrollPane D1Scroll = new ScrollPane();

        D1.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(D1,c);

        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        D1Label.setFont(new Font("Serif", Font.PLAIN, 20));
        D1.add(D1Label,c);

        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        D1Scroll.add(D1List);
        D1.add(D1Scroll,c);

        for(Schedule schedule : arrayList) {
            if(schedule.getSchedule().substring(0, 10).equals(now.toString().substring(0, 10))) {
                D1_listModel.addElement(schedule.getBillboardName());
            }
        }


        JPanel D2 = new JPanel();
        JLabel D2Label = new JLabel(now.plusDays(1).getDayOfWeek().toString());
        DefaultListModel<String> D2_listModel = new DefaultListModel<>();
        JList D2List = new JList<>(D2_listModel);
        ScrollPane D2Scroll = new ScrollPane();

        D2.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(D2,c);

        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        D2Label.setFont(new Font("Serif", Font.PLAIN, 20));
        D2.add(D2Label,c);

        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        D2Scroll.add(D2List);
        D2.add(D2Scroll,c);

        for(Schedule schedule : arrayList) {
            if(schedule.getSchedule().substring(0, 10).equals(now.plusDays(1).toString().substring(0, 10))) {
                System.out.println(schedule.getBillboardName());
                System.out.println(schedule.getSchedule());
                D2_listModel.addElement(schedule.getBillboardName());
            }
        }


        JPanel D3 = new JPanel();
        JLabel D3Label = new JLabel(now.plusDays(2).getDayOfWeek().toString());
        DefaultListModel<String> D3_listModel = new DefaultListModel<>();
        JList D3List = new JList<>(D3_listModel);
        ScrollPane D3Scroll = new ScrollPane();

        D3.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(D3,c);

        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        D3Label.setFont(new Font("Serif", Font.PLAIN, 20));
        D3.add(D3Label,c);

        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        D3Scroll.add(D3List);
        D3.add(D3Scroll,c);

        for(Schedule schedule : arrayList) {
            if(schedule.getSchedule().substring(0, 10).equals(now.plusDays(2).toString().substring(0, 10))) {
                D3_listModel.addElement(schedule.getBillboardName());
            }
        }

        JPanel D4 = new JPanel();
        JLabel D4Label = new JLabel(now.plusDays(3).getDayOfWeek().toString());
        DefaultListModel<String> D4_listModel = new DefaultListModel<>();
        JList D4List = new JList<>(D4_listModel);
        ScrollPane D4Scroll = new ScrollPane();

        D4.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 4;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(D4,c);

        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        D4Label.setFont(new Font("Serif", Font.PLAIN, 20));
        D4.add(D4Label,c);

        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        D4Scroll.add(D4List);
        D4.add(D4Scroll,c);

        for(Schedule schedule : arrayList) {
            if(schedule.getSchedule().substring(0, 10).equals(now.plusDays(3).toString().substring(0, 10))) {
                D4_listModel.addElement(schedule.getBillboardName());
            }
        }

        JPanel D5 = new JPanel();
        JLabel D5Label = new JLabel(now.plusDays(4).getDayOfWeek().toString());
        DefaultListModel<String> D5_listModel = new DefaultListModel<>();
        JList D5List = new JList<>(D5_listModel);
        ScrollPane D5Scroll = new ScrollPane();

        D5.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 5;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(D5,c);

        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        D5Label.setFont(new Font("Serif", Font.PLAIN, 20));
        D5.add(D5Label,c);

        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        D5Scroll.add(D5List);
        D5.add(D5Scroll,c);

        for(Schedule schedule : arrayList) {
            if(schedule.getSchedule().substring(0, 10).equals(now.plusDays(4).toString().substring(0, 10))) {
                D5_listModel.addElement(schedule.getBillboardName());
            }
        }



        JPanel D6 = new JPanel();
        JLabel D6Label = new JLabel(now.plusDays(5).getDayOfWeek().toString());
        DefaultListModel<String> D6_listModel = new DefaultListModel<>();
        JList D6List = new JList<>(D6_listModel);
        ScrollPane D6Scroll = new ScrollPane();

        D6.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 6;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(D6,c);

        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        D6Label.setFont(new Font("Serif", Font.PLAIN, 20));
        D6.add(D6Label,c);

        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        D6Scroll.add(D6List);
        D6.add(D6Scroll,c);

        for(Schedule schedule : arrayList) {
            if(schedule.getSchedule().substring(0, 10).equals(now.plusDays(5).toString().substring(0, 10))) {
                System.out.println(schedule.getBillboardName());
                D6_listModel.addElement(schedule.getBillboardName());
            }
        }

        JPanel D7 = new JPanel();
        JLabel D7Label = new JLabel(now.plusDays(6).getDayOfWeek().toString());
        DefaultListModel<String> D7_listModel = new DefaultListModel<>();
        JList D7List = new JList<>(D7_listModel);
        ScrollPane D7Scroll = new ScrollPane();

        D7.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 7;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(D7,c);

        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        D7Label.setFont(new Font("Serif", Font.PLAIN, 20));
        D7.add(D7Label,c);

        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        D7Scroll.add(D7List);
        D7.add(D7Scroll,c);

        for(Schedule schedule : arrayList) {
            if(schedule.getSchedule().substring(0, 10).equals(now.plusDays(6).toString().substring(0, 10))) {
                System.out.println(schedule.getBillboardName());
                D7_listModel.addElement(schedule.getBillboardName());
            }
        }


















//        JPanel Monday = new JPanel();
//        Monday.setLayout(new GridBagLayout());
//        Monday.isOpaque();
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 1;
//        c.gridy = 2;
//        c.weightx = 1;
//        c.weighty = 1;
//        panel.add(Monday,c);
//
//        JLabel MondayLabel = new JLabel(now.plusDays(1).getDayOfWeek().toString());
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0;
//        c.weighty = 0;
//        MondayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
//        Monday.add(MondayLabel,c);
//
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weightx = 1;
//        c.weighty = 1;
//        JScrollPane MondayScroll = new JScrollPane();
//        Monday.add(MondayScroll,c);
//
//
//
//        JPanel Tuesday = new JPanel();
//        Tuesday.setLayout(new GridBagLayout());
//        Tuesday.isOpaque();
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 2;
//        c.gridy = 2;
//        c.weightx = 1;
//        c.weighty = 1;
//        panel.add(Tuesday,c);
//
//        JLabel TuesdayLabel = new JLabel(now.plusDays(2).getDayOfWeek().toString());
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0;
//        c.weighty = 0;
//        TuesdayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
//        Tuesday.add(TuesdayLabel,c);
//
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weightx = 1;
//        c.weighty = 1;
//        JScrollPane TuesdayScroll = new JScrollPane();
//        Tuesday.add(TuesdayScroll,c);
//
//
//
//        JPanel Wednesday = new JPanel();
//        Wednesday.setLayout(new GridBagLayout());
//        Wednesday.isOpaque();
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 3;
//        c.gridy = 2;
//        c.weightx = 1;
//        c.weighty = 1;
//        panel.add(Wednesday,c);
//
//        JLabel WednesdayLabel = new JLabel(now.plusDays(3).getDayOfWeek().toString());
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0;
//        c.weighty = 0;
//        WednesdayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
//        Wednesday.add(WednesdayLabel,c);
//
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weightx = 1;
//        c.weighty = 1;
//        JScrollPane WednesdayScroll = new JScrollPane();
//        Wednesday.add(WednesdayScroll,c);
//
//
//
//        JPanel Thursday = new JPanel();
//        Thursday.setLayout(new GridBagLayout());
//        Thursday.isOpaque();
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 4;
//        c.gridy = 2;
//        c.weightx = 1;
//        c.weighty = 1;
//        panel.add(Thursday,c);
//
//        JLabel ThursdayLabel = new JLabel(now.plusDays(4).getDayOfWeek().toString());
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0;
//        c.weighty = 0;
//        ThursdayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
//        Thursday.add(ThursdayLabel,c);
//
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weightx = 1;
//        c.weighty = 1;
//        JScrollPane ThursdayScroll = new JScrollPane();
//        Thursday.add(ThursdayScroll,c);
//
//
//
//        JPanel Friday = new JPanel();
//        Friday.setLayout(new GridBagLayout());
//        Friday.isOpaque();
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 5;
//        c.gridy = 2;
//        c.weightx = 1;
//        c.weighty = 1;
//        panel.add(Friday,c);
//
//        JLabel FridayLabel = new JLabel(now.plusDays(5).getDayOfWeek().toString());
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0;
//        c.weighty = 0;
//        FridayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
//        Friday.add(FridayLabel,c);
//
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weightx = 1;
//        c.weighty = 1;
//        JScrollPane FridayScroll = new JScrollPane();
//        Friday.add(FridayScroll,c);
//
//
//
//        JPanel Saturday = new JPanel();
//        Saturday.setLayout(new GridBagLayout());
//        Saturday.isOpaque();
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 6;
//        c.gridy = 2;
//        c.weightx = 1;
//        c.weighty = 1;
//        panel.add(Saturday,c);
//
//        JLabel SaturdayLabel = new JLabel(now.plusDays(6).getDayOfWeek().toString());
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0;
//        c.weighty = 0;
//        SaturdayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
//        Saturday.add(SaturdayLabel,c);
//
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weightx = 1;
//        c.weighty = 1;
//        JScrollPane SaturdayScroll = new JScrollPane();
//        Saturday.add(SaturdayScroll,c);



        JLabel title = new JLabel("Scheduled");
        title.setFont(new Font("Serif", Font.PLAIN, 30));
        c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 3;
        panel.add(title,c);

        JButton Schedule = new JButton("Schedule");
        panel.add(Schedule);

        SchedulePopOut schedulePopOut = new SchedulePopOut();
        Schedule.addActionListener(schedulePopOut);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);

    }

}
