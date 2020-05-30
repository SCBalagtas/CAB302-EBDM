package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Steven Balagtas
 *
 * Class of static methods to query the schedules table in the database.
 */

public class Schedules {
    /**
     * Check if a billboard is scheduled.
     *
     * @param billboardName of the billboard.
     * @return true if the billboard is scheduled.
     */
    public static boolean isBillboardScheduled(String billboardName) {
        // try and check if the billboard is scheduled
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM schedules WHERE billboardName=?"
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
            return false;
        }
    }
}
