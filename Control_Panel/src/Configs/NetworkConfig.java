package Configs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class NetworkConfig {

    private static int port = -1;
    private static String url = "";

    private NetworkConfig() {
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("./network.props");
            props.load(in);
            in.close();

            // set the port number
            port = Integer.parseInt(props.getProperty("network.port"));
            url = props.getProperty("network.url");
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static int getPort() {
        if (port == -1) { new NetworkConfig(); }
        return port;
    }

    public static String getUrl() {
        if (url.equals("")) { new NetworkConfig(); }
        return url;
    }
}
