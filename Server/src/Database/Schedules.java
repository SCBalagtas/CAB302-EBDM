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

    /**
     * Delete schedules from the schedules table by billboardName.
     *
     * @param billboardName of billboard whose schedules will be deleted.
     */
    public static void deleteSchedulesFromDBByBillboardName(String billboardName) {
        // try to delete the schedules from the schedules table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM schedules WHERE billboardName=?"
            );
            statement.clearParameters();
            statement.setString(1, billboardName);
            statement.executeUpdate();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
