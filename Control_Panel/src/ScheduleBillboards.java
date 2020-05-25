import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SimpleTimeZone;
import java.util.concurrent.Flow;

public class ScheduleBillboards {
    public void main(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
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

        JPanel Sunday = new JPanel();
        Sunday.setLayout(new GridBagLayout());
        Sunday.isOpaque();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(Sunday,c);

        JLabel SundayLabel = new JLabel("Sunday");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        SundayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        Sunday.add(SundayLabel,c);


        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        JScrollPane SundayScroll = new JScrollPane();
        Sunday.add(SundayScroll,c);


        JPanel Monday = new JPanel();
        Monday.setLayout(new GridBagLayout());
        Monday.isOpaque();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(Monday,c);

        JLabel MondayLabel = new JLabel("Monday");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        MondayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        Monday.add(MondayLabel,c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        JScrollPane MondayScroll = new JScrollPane();
        Monday.add(MondayScroll,c);



        JPanel Tuesday = new JPanel();
        Tuesday.setLayout(new GridBagLayout());
        Tuesday.isOpaque();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(Tuesday,c);

        JLabel TuesdayLabel = new JLabel("Tuesday");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        TuesdayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        Tuesday.add(TuesdayLabel,c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        JScrollPane TuesdayScroll = new JScrollPane();
        Tuesday.add(TuesdayScroll,c);



        JPanel Wednesday = new JPanel();
        Wednesday.setLayout(new GridBagLayout());
        Wednesday.isOpaque();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(Wednesday,c);

        JLabel WednesdayLabel = new JLabel("Wednesday");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        WednesdayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        Wednesday.add(WednesdayLabel,c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        JScrollPane WednesdayScroll = new JScrollPane();
        Wednesday.add(WednesdayScroll,c);



        JPanel Thursday = new JPanel();
        Thursday.setLayout(new GridBagLayout());
        Thursday.isOpaque();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 4;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(Thursday,c);

        JLabel ThursdayLabel = new JLabel("Thursday");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        ThursdayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        Thursday.add(ThursdayLabel,c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        JScrollPane ThursdayScroll = new JScrollPane();
        Thursday.add(ThursdayScroll,c);



        JPanel Friday = new JPanel();
        Friday.setLayout(new GridBagLayout());
        Friday.isOpaque();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 5;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(Friday,c);

        JLabel FridayLabel = new JLabel("Friday");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        FridayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        Friday.add(FridayLabel,c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        JScrollPane FridayScroll = new JScrollPane();
        Friday.add(FridayScroll,c);



        JPanel Saturday = new JPanel();
        Saturday.setLayout(new GridBagLayout());
        Saturday.isOpaque();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 6;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(Saturday,c);

        JLabel SaturdayLabel = new JLabel("Saturday");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        SaturdayLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        Saturday.add(SaturdayLabel,c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        JScrollPane SaturdayScroll = new JScrollPane();
        Saturday.add(SaturdayScroll,c);



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
