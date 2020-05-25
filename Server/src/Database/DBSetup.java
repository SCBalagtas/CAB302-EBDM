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

        // print table created message to command line
        System.out.println("OK! Users table created");
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

        // print table created message to command line
        System.out.println("OK! Billboards table created");
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

        // print table created message to command line
        System.out.println("OK! Schedules table created");
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

        // print table created message to command line
        System.out.println("OK! Permissions table created");
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

        // print table created message to command line
        System.out.println("OK! User Permissions table created");
    }

    /**
     * Populates the permissions table with the four user permissions.
     */
    private static void populatePermissions(Statement statement) throws SQLException {
        // initialise permissions with the four user permissions
        String insertPermissions = "INSERT INTO permissions VALUES "
                + "(0, 'Edit Users'), "
                + "(1, 'Create Billboards'), "
                + "(2, 'Edit All Billboards'), "
                + "(3, 'Schedule Billboards')";

        // execute insert values statement
        statement.executeUpdate(insertPermissions);

        // print values inserted message to command line
        System.out.println("OK! Permissions table populated");
    }

    /**
     * Creates an admin account with full permissions.
     */
    private static void createAdmin(Statement statement) throws SQLException {
        // initialise an admin user
        String insertUser = "INSERT INTO users (userName, password) VALUES "
                + "('admin', 'admin')";

        String insertUserPermissions = "INSERT INTO userPermissions VALUES "
                + "('admin', 0), "
                + "('admin', 1), "
                + "('admin', 2), "
                + "('admin', 3)";

        // execute insert values statement
        statement.executeUpdate(insertUser);
        statement.executeUpdate(insertUserPermissions);

        // print values inserted message to command line
        System.out.println("OK! Admin user created");
    }

    /**
     * Creates all the tables required for the EBDM database if they do not already exist.
     */
    public static void setupTables() {
        System.out.println("Checking database status");

        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();

            // if the EBDM db is empty, create all the required tables
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            if (!resultSet.next()) {
                System.out.println("Database is empty! Creating tables now...");
                createUsersTable(statement);
                createBillboardsTable(statement);
                createSchedulesTable(statement);
                createPermissionsTable(statement);
                createUserPermissionsTable(statement);
            } else {
                System.out.println("OK! Tables already exist");
            }

            // if the permissions table is empty, initialise it with the four user permissions
            resultSet = statement.executeQuery("SELECT * FROM permissions");
            if (!resultSet.next()) {
                System.out.println("Permissions table is empty! Populating now...");
                populatePermissions(statement);
            } else {
                System.out.println("OK! Permissions table already populated");
            }

            // if the users table is empty, initialise an admin user with full permissions
            resultSet = statement.executeQuery("SELECT * FROM users");
            if (!resultSet.next()) {
                System.out.println("Users table is empty! Creating admin user now...");
                createAdmin(statement);
            } else {
                System.out.println("OK! Users table already populated");
            }

            // close result set
            resultSet.close();

            // close statement and connection object
            statement.close();
            connection.close();

            System.out.print("SUCCESS!");

        } catch (Exception e) {
            System.err.println(e);
            System.out.println("FAILURE! Could not connect to database");
        }
    }
}