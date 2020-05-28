package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     * Inserts a new user into the user table.
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
            statement.execute();

            // try to insert the new user's permissions into the userPermissions table
            statement = connection.prepareStatement(
                    "INSERT INTO userPermissions VALUES (?, ?)"
            );
            // insert all of the permissions from permissionsList
            for (String permission : permissionsList) {
                statement.clearParameters();
                statement.setString(1, userName);
                statement.setInt(2, Integer.parseInt(permission));
                statement.execute();
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
}
