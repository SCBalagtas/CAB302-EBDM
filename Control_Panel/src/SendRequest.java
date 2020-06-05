import Classes.Billboard;
import Classes.Response;
import Configs.NetworkConfig;

import java.io.*;
import java.net.Socket;

public class SendRequest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    }
    public Object o;

    public static Response serverRequest1(Object request) throws IOException, ClassNotFoundException {
        // open a connection to the server
        Socket socket = new Socket(NetworkConfig.getUrl(), NetworkConfig.getPort());

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