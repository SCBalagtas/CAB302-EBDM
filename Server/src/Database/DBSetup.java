package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to create the tables required for the EBDM database.
 */

public class DBSetup {
    /**
     * Creates the users table in the EBDM database if it does not already exist.
     */
    private static void createUsersTable(Statement statement) throws SQLException {
        // sql statement to create the users table
        String createUsers = "CREATE TABLE IF NOT EXISTS users "
                + "(userName    VARCHAR(50) PRIMARY KEY, "
                + "password     VARCHAR(50) NOT NULL, "
                + "creationDate DATETIME DEFAULT NOW(), "
                + "updatedAt    DATETIME DEFAULT NOW() ON UPDATE NOW())";

        // execute create table statement
        statement.execute(createUsers);

        // print a ". " to command line to show progress
        System.out.print(". ");
    }

    /**
     * Creates the billboards table in the EBDM database.
     */
    private static void createBillboardsTable(Statement statement) throws SQLException {
        // sql statement to create the billboards table
        String createBillboards = "CREATE TABLE IF NOT EXISTS billboards "
                + "(billboardName   VARCHAR(255) PRIMARY KEY, "
                + "content          TEXT NOT NULL, "
                + "creator          VARCHAR(50) NOT NULL, "
                + "creationDate DATETIME DEFAULT NOW(), "
                + "updatedAt    DATETIME DEFAULT NOW() ON UPDATE NOW(), "
                + "FOREIGN KEY (creator) REFERENCES users(userName))";

        // execute create table statement
        statement.execute(createBillboards);

        // print a ". " to command line to show progress
        System.out.print(". ");
    }

    /**
     * Creates the schedules table in the EBDM database.
     */
    private static void createSchedulesTable(Statement statement) throws SQLException {
        // sql statement to create the schedules table
        String createSchedules = "CREATE TABLE IF NOT EXISTS schedules "
                + "(scheduleId      INT PRIMARY KEY AUTO_INCREMENT, "
                + "billboardName    VARCHAR(255) NOT NULL, "
                + "scheduledBy      VARCHAR(50) NOT NULL, "
                + "schedule         DATETIME NOT NULL, "
                + "duration         INT NOT NULL, "
                + "freqType         ENUM ('Daily', 'Hourly', 'Minutes') DEFAULT NULL, "
                + "freqInterval     INT DEFAULT NULL, "
                + "FOREIGN KEY (billboardName) REFERENCES billboards(billboardName), "
                + "FOREIGN KEY (scheduledBy) REFERENCES users(userName))";

        // execute create table statement
        statement.execute(createSchedules);

        // print a ". " to command line to show progress
        System.out.print(". ");
    }

    /**
     * Creates the permissions table in the EBDM database.
     */
    private static void createPermissionsTable(Statement statement) throws SQLException {
        // sql statement to create the permissions table
        String createPermissions = "CREATE TABLE IF NOT EXISTS permissions "
                + "(permissionId    INT PRIMARY KEY, "
                + "permission       VARCHAR(50) NOT NULL)";

        // execute create table statement
        statement.execute(createPermissions);

        // if the permissions table is empty, initialise it with the four user permissions
        ResultSet resultSet = statement.executeQuery("SELECT * FROM permissions");
        if (!resultSet.next()) {
            // result set is empty, initialise permissions with the four user permissions
            String insertPermissions = "INSERT INTO permissions VALUES "
                    + "(0, 'Edit Users'), "
                    + "(1, 'Create Billboards'), "
                    + "(2, 'Edit All Billboards'), "
                    + "(3, 'Schedule Billboards')";

            // execute insert values statement
            statement.executeUpdate(insertPermissions);
        }

        // close the result set
        resultSet.close();

        // print a ". " to command line to show progress
        System.out.print(". ");
    }

    /**
     * Creates the userPermissions table in the EBDM database.
     */
    private static void createUserPermissionsTable(Statement statement) throws SQLException {
        // sql statement to create the userPermissions table
        String createUserPermissions = "CREATE TABLE IF NOT EXISTS userPermissions "
                + "(userName        VARCHAR(50), "
                + "permissionId     INT, "
                + "PRIMARY KEY (userName, permissionId), "
                + "FOREIGN KEY (userName) REFERENCES users(userName), "
                + "FOREIGN KEY (permissionId) REFERENCES permissions(permissionId))";

        // execute create table statement
        statement.execute(createUserPermissions);

        // print a ". " to command line to show progress
        System.out.print(". ");
    }

    /**
     * Creates all the tables required for the EBDM database if they do not already exist.
     */
    public static void setupTables() throws SQLException {
        System.out.println("Setting up database");

        // create new connection and statement object
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();

        createUsersTable(statement);
        createBillboardsTable(statement);
        createSchedulesTable(statement);
        createPermissionsTable(statement);
        createUserPermissionsTable(statement);

        // close statement and connection object
        statement.close();
        connection.close();

        System.out.print("SUCCESS!");
    }
}
