import Database.DBSetup;

/**
 * Author: Steven Balagtas
 *
 * This is the main class, the server will be initialised here.
 */

public class Main {
    public static void main(String[] args) {
        // check if database is setup
        try {
            DBSetup.setupTables();
        } catch (Exception e) {
            System.err.println(e);
            System.out.println("FAILURE! Could not connect to database");
            System.out.println("Terminating server now...");
            System.exit(0);
        }

        // infinite loop to simulate the server behaviour
        for (;;) {
            // ...
        }
    }
}
