import Classes.Request;
import Classes.Response;
import Classes.Billboard;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, InterruptedException, IOException {

        JFrame frame = new JFrame();
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.MOUSE_EVENT_MASK) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        DrawBillboard drawBillboard = new DrawBillboard();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> emptylist = new ArrayList<>();
        for (; ; ) {

            //Response response = SendRequest.serverRequest1(new Request(RequestTypes., list));

            if (false) {       //If response contains billboard display billboard
                //Needs support for long string
                drawBillboard.main("BillboardName", "<billboard background=\"#8996FF\">\n" +
                        "<picture url=\"https://cloudstor.aarnet.edu.au/plus/s/5fhToroJL0nMKvB/download\"/>\n" +
                        "</billboard>");
                frame.add(drawBillboard.DrawWindow());

            } else {       //Display defualt billboard
                DefaultBillboard defaultBillboard = new DefaultBillboard();
                frame.add(defaultBillboard.main(frame));

            }



            //Needs support for long string



            try {

                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationRelativeTo(null);
                frame.setUndecorated(true);
                frame.setVisible(true);
            } catch (Exception ignored) {
            }
            ;


            System.out.println("TIME");
            TimeUnit.SECONDS.sleep(15);

        }

    }
}
