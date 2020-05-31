package Database;

import java.sql.*;

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

    /**
     * Get the creator of a particular billboard.
     *
     * @param billboardName of the billboard whose creator will be retrieved.
     * @return a string of the userName of the creator of the billboard.
     */
    public static String getCreator(String billboardName) {
        String creator = null;

        // try to get the creator of billboard
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT creator FROM billboards WHERE billboardName=?"
            );
            statement.clearParameters();
            statement.setString(1, billboardName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            // save result into creator
            creator = resultSet.getString("creator");

            // close result set
            resultSet.close();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return creator;
    }

    /**
     * Update a billboard's content in the billboards table.
     *
     * @param billboardName of the billboard to be updated.
     * @param content that will be replace the old content.
     */
    public static void updateBillboardContent(String billboardName, String content) {
        // try to insert the new content for the billboard
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE billboards SET content=? WHERE billboardName=?"
            );
            statement.clearParameters();
            statement.setString(1, content);
            statement.setString(2, billboardName);
            statement.executeUpdate();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Delete billboard from the billboards table.
     *
     * @param billboardName of billboard to be deleted.
     */
    public static void deleteBillboardFromDB(String billboardName) {
        // try to delete the billboard from the billboards table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM billboards WHERE billboardName=?"
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

    /**
     * Get a billboard's content.
     *
     * @param billboardName of the billboard whose content will be retrieved.
     */
    public static String getBillboardContent(String billboardName) {
        String content = null;

        // try to get the content of billboard
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT content FROM billboards WHERE billboardName=?"
            );
            statement.clearParameters();
            statement.setString(1, billboardName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            // save result into content
            content = resultSet.getString("content");

            // close result set
            resultSet.close();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return content;
    }
}
