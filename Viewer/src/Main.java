import Classes.Billboard;
import Classes.Request;
import Classes.Response;
import Constants.RequestTypes;

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


        DefaultBillboard defaultBillboard = new DefaultBillboard();
        ArrayList<String> list = new ArrayList<>();
        String previouslyDrawn = "unique string";
        for (; ; ) {

            Response response = SendRequest.serverRequest1(new Request(RequestTypes.CURRENT_BILLBOARD, list));
            System.out.println(response.getContent().toString());
            String Content = response.getContent().toString();

            if (!Content.equals("") && !Content.equals(previouslyDrawn)) {
                frame.dispose();
                DrawBillboard drawBillboard = new DrawBillboard();
                drawBillboard.main("BillboardName", "\"" + Content + "\"");
                frame.add(drawBillboard.DrawWindow());
                previouslyDrawn = response.getContent().toString();


            } else if (!Content.equals(previouslyDrawn)) {
                frame.dispose();
                System.out.println("NO RETURN");
                frame.add(defaultBillboard.main(frame));
                previouslyDrawn = "";
            }

            try {
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setSize(1920,1080);
                frame.setLocationRelativeTo(null);
                frame.setUndecorated(true);
                frame.setVisible(true);
            } catch (Exception ignored) { };


            TimeUnit.SECONDS.sleep(15);

        }

    }
}
