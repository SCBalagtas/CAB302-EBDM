import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Author: Steven Balagtas
 *
 * This class will read the server.props file for the port number that the server will serve on.
 * Change the server.props file to change which port the server will serve on.
 */

public class ServerConfig {
    /**
     * The port number the server will serve on.
     */
    private static int port = -1;

    /**
     * Constructor initialises the port number.
     */
    private ServerConfig() {
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("./server.props");
            props.load(in);
            in.close();

            // set the port number
            port = Integer.parseInt(props.getProperty("server.port"));
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Provides global access to the port number that the server will serve on.
     *
     * @return the port number that the server will serve on.
     */
    public static int getPort() {
        if (port == -1) { new ServerConfig(); }
        return port;
    }
}
