import Classes.Response;

import java.io.*;
import java.net.Socket;


/**
 * @author Felix Savins
 * Sends request to server
 */
public class SendRequest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    }

    /**
     *
     * @param request
     * @return Response object
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public static Response serverRequest1(Object request) throws IOException, ClassNotFoundException {
        // open a connection to the server
        Socket socket = new Socket("localhost", 3000);

        // write a request to the client

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);


        oos.writeObject(request);
        oos.flush();

        InputStream inputStream = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);

        Object o = ois.readObject();
        System.out.print(o.toString() + "\n");


        Response p = (Response) ois.readObject();

        ois.close();
        oos.close();
        // close the connection
        socket.close();
        return p;
    }

}