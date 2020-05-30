package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to query the billboards table in the database.
 */

public class Billboards {
    /**
     * Check if the billboard name already exists in the billboards table.
     *
     * @param billboardName of the billboard.
     * @return true if the billboard already exists in the billboards table.
     */
    public static boolean doesBillboardExists(String billboardName) {
        // try and check if the billboardName already exists
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT billboardName FROM billboards WHERE billboardName=?"
            );
            statement.clearParameters();
            statement.setString(1, billboardName);
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
            return true;
        }
    }

    /**
     * Insert a new billboard into the billboards table.
     *
     * @param billboardName of the new billboard.
     * @param content of the new billboard.
     * @param creator of the new billboard.
     */
    public static void insertNewBillboard(String billboardName, String content, String creator) {
        // try to insert the new billboard into the billboards table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO billboards (billboardName, content, creator) VALUES (?, ?, ?)"
            );
            statement.clearParameters();
            statement.setString(1, billboardName);
            statement.setString(2, content);
            statement.setString(3, creator);
            statement.executeUpdate();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
