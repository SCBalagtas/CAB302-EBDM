package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        ArrayList<String> userCredentials = new ArrayList<String>();

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
}
