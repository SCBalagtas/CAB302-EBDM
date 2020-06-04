import Classes.Billboard;
import Classes.Request;
import Classes.Response;
import Constants.RequestTypes;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * @author Felix Savins
 * Gets current scheduled billboard from server and draws it in fullscreen frame,
 * custom frame when no connection or no billboard scheduled
 */

public class Main {

    /**
     * @param args
     * @throws ClassNotFoundException
     * @throws InterruptedException
     * @throws IOException
     */

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, IOException {

        JFrame frame = new JFrame();
        frame.addMouseListener(new MouseListener() {        //Mouse listener used to close window when mouse1 is pressed
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);     //Exit program

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
        frame.addKeyListener(new KeyListener() {    //KeyListener used to close window when escape is pressed
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.MOUSE_EVENT_MASK) {
                    System.exit(0);     //Exit program
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        DefaultBillboard defaultBillboard = new DefaultBillboard();     //new default window instance
        ConnectionError connectionError = new ConnectionError();        //new connection error window instance
        ArrayList<String> list = new ArrayList<>();     //empty list to send to server
        String previouslyDrawn = "unique string";       //String that cannot be returned by server to compare initial response to

        for (; ; ) {
            try {
                Response response = SendRequest.serverRequest1(new Request(RequestTypes.CURRENT_BILLBOARD, list));      //ask server for currently scheduled billboard content
                String Content = response.getContent().toString();

                if (!Content.equals("") && !Content.equals(previouslyDrawn)) {      //if content is returned and is not what is currently being displayed
                    frame.dispose();
                    frame.getContentPane().removeAll();
                    DrawBillboard drawBillboard = new DrawBillboard();
                    drawBillboard.main("BillboardName", Content);   // Create variables for drawing billboard
                    frame.add(drawBillboard.DrawWindow());          //Add panel returned from drawbillboard to frame
                    previouslyDrawn = response.getContent().toString();


                } else if (!Content.equals(previouslyDrawn)) {      //If no billboard is returned
                    frame.dispose();
                    frame.getContentPane().removeAll();
                    System.out.println("NO RETURN");
                    frame.add(defaultBillboard.main());        //add default billboard panel to frame
                    previouslyDrawn = "";
                }

            }

            catch(ConnectException e) {         //if there is a connection error
                System.out.println("Connection error");
                frame.dispose();
                frame.add(connectionError.main());     //add connection panel to frame
            }

                try {
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setSize(1920, 1080);
                    frame.setLocationRelativeTo(null);
                    frame.setUndecorated(true);
                    frame.setVisible(true);
                } catch (Exception ignored) {
                }
                ;



            TimeUnit.SECONDS.sleep(15);     //15 seconds between calls to server

        }

    }
}
