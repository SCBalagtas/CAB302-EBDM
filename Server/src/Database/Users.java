package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to query the users table in the database.
 */

public class Users {
    /**
     * Gets the users credentials from the database.
     *
     * @param userName of the user.
     * @return a string ArrayList containing the user's userName and password.
     * If userName does not exist in the database, empty ArrayList will be returned.
     * */
    public static ArrayList<String> getUserCredentials(String userName) throws SQLException {
        // string ArrayList to store the user's credentials in
        ArrayList<String> userCredentials = new ArrayList<>();

        // create new connection and statement object
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT userName, password FROM users WHERE userName=?"
        );
        statement.clearParameters();
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();

        // add the user's credentials to userCredentials if result set is not empty
        if (resultSet.next()) {
            userCredentials.add(resultSet.getString("userName"));
            userCredentials.add(resultSet.getString("password"));
        }

        // close result set
        resultSet.close();

        // close statement and connection object
        statement.close();
        connection.close();

        // return userCredentials
        return userCredentials;
    }

    /**
     * Inserts a new user into the users table.
     *
     * @param userName of the new user.
     * @param permissions of the new user.
     * @param password of the new user.
     * @return true if insert operation was successful.
     */
    public static boolean insertNewUser(String userName, String permissions, String password) {
        // convert permissions string into a list
        List<String> permissionsList = Arrays.asList(permissions.substring(1, permissions.length() - 1).split(", "));

        // try to insert the new user into the user table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (userName, password) VALUES (?, ?)"
            );
            statement.clearParameters();
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.executeUpdate();

            // try to insert the new user's permissions into the userPermissions table
            statement = connection.prepareStatement(
                    "INSERT INTO userPermissions VALUES (?, ?)"
            );
            // insert all of the permissions from permissionsList
            for (String permission : permissionsList) {
                if (permission.equals("")) { continue; } // if empty permissions list is passed
                statement.clearParameters();
                statement.setString(1, userName);
                statement.setInt(2, Integer.parseInt(permission));
                statement.executeUpdate();
            }

            // close statement and connection object
            statement.close();
            connection.close();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Check if user has the provided permission.
     *
     * @param userName of the user.
     * @param permissionId int of the permissionId.
     * @return true if the query gets a result.
     */
    public static boolean userHasPermission(String userName, int permissionId) {
        // try to query if the user has the provided permission
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM userPermissions WHERE userName=? AND permissionId=?"
            );
            statement.clearParameters();
            statement.setString(1, userName);
            statement.setInt(2, permissionId);
            ResultSet resultSet = statement.executeQuery();

            // return true if result set is not empty
            if (resultSet.next()) {
                // close result set
                resultSet.close();

                // close statement and connection object
                statement.close();
                connection.close();
                return true;
            }
            // close result set
            resultSet.close();

            // close statement and connection object
            statement.close();
            connection.close();

            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Delete user from the users table.
     *
     * @param userName of user to be deleted.
     * @return true if the delete operation was successful.
     */
    public static boolean deleteUserFromDB(String userName) {
        // try to delete the user from the users table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE userName=?"
            );
            statement.clearParameters();
            statement.setString(1, userName);

            // if affected rows != 0 return true
            if (statement.executeUpdate() != 0) {
                // close statement and connection object
                statement.close();
                connection.close();

                return true;
            }
            // close statement and connection object
            statement.close();
            connection.close();

            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Set a new password for a particular user in the users table.
     *
     * @param userName of the user whose password will be changed.
     * @param password that will be set.
     * @return true if the update operation was successful.
     */
    public static boolean setUserPasswordInDB(String userName, String password) {
        // try to update the user's password from the users table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET password=? WHERE userName=?"
            );
            statement.clearParameters();
            statement.setString(1, password);
            statement.setString(2, userName);

            // if affected rows != 0 return true
            if (statement.executeUpdate() != 0) {
                // close statement and connection object
                statement.close();
                connection.close();

                return true;
            }

            // close statement and connection object
            statement.close();
            connection.close();

            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Get a user's permissions.
     *
     * @param userName of the user whose permissions will be retrieved.
     * @return an int ArrayList containing the user's permissions.
     * If userName does not exist in the database, empty ArrayList will be returned.
     */
    public static ArrayList<Integer> getUserPermissionsFromDB(String userName) {
        // int ArrayList to store the user's permissions in
        ArrayList<Integer> userPermissions = new ArrayList<>();

        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT permissionId FROM userPermissions WHERE userName=?"
            );
            statement.clearParameters();
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            // add the user's credentials to userCredentials if result set is not empty
            while (resultSet.next()) {
                userPermissions.add((resultSet.getInt("permissionId")));
            }

            // close result set
            resultSet.close();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            // return userCredentials
            return userPermissions;
        }
        // return userCredentials
        return userPermissions;
    }

    /**
     * Set new permissions for a particular user in the users table.
     *
     * @param userName of the user whose permissions will be changed.
     * @param permissions string of the new permissions to be set.
     */
    public static void setPermissionsInDB(String userName, String permissions) {
        // convert permissions string into a list
        List<String> permissionsList = Arrays.asList(permissions.substring(1, permissions.length() - 1).split(", "));

        // try to delete the user's current permissions and set the new ones
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM userPermissions WHERE userName=?"
            );
            statement.clearParameters();
            statement.setString(1, userName);
            statement.executeUpdate();

            // insert the new list of permissions for the user into the userPermissions table
            statement = connection.prepareStatement(
                    "INSERT INTO userPermissions VALUES (?, ?)"
            );
            // insert all of the permissions from permissionsList
            for (String permission : permissionsList) {
                if (permission.equals("")) { continue; } // if empty permissions list is passed
                statement.clearParameters();
                statement.setString(1, userName);
                statement.setInt(2, Integer.parseInt(permission));
                statement.executeUpdate();
            }

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Get all the userNames from the users table.
     *
     * @return a string ArrayList of all the userNames in the users table.
     */
    public static ArrayList<String> getUsers() {
        // string ArrayList to store the userNames of all the users in the users table
        ArrayList<String> userNames = new ArrayList<>();

        // try to get all the userNames from the users table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT userName FROM users");

            // add the user's userName into userNames if result set is not empty
            while (resultSet.next()) {
                userNames.add(resultSet.getString("userName"));
            }

            // close result set
            resultSet.close();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
        // return userNames
        return userNames;
    }
}
