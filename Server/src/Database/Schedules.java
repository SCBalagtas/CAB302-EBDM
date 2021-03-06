package Database;

import Classes.Schedule;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    /**
     * Insert a new schedule into the schedules table without frequency options.
     *
     * @param billboardName of the billboard to be scheduled.
     * @param scheduledBy userName of the user who created the schedule.
     * @param schedule DateTime of the schedule.
     * @param duration of the schedule in minutes.
     */
    public static void insertSchedule(String billboardName, String scheduledBy, LocalDateTime schedule, int duration) {
        // try and insert the schedule into the schedules table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO schedules (billboardName, scheduledBy, schedule, duration) VALUES (?, ?, ?, ?)"
            );
            statement.clearParameters();
            statement.setString(1, billboardName);
            statement.setString(2, scheduledBy);
            statement.setObject(3, schedule);
            statement.setInt(4, duration);
            statement.executeUpdate();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Insert a new schedule into the schedules table with frequency options.
     *
     * @param billboardName of the billboard to be scheduled.
     * @param scheduledBy userName of the user who created the schedule.
     * @param schedule DateTime of the schedule.
     * @param duration of the schedule in minutes.
     * @param freqType string of the frequency type, ('Daily', 'Hourly', 'Minutes').
     * @param freqInterval interval of the frequency in minutes, only exist if 'Minutes' freqType is selected.
     */
    public static void insertScheduleWithFrequencyOptions(String billboardName, String scheduledBy, LocalDateTime schedule, int duration, String freqType, String freqInterval) {
        // try and insert the schedule into the schedules table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement;

            // if freqType is 'Minutes', also insert the freqInterval
            if (freqType.equals("Minutes")) {
                statement = connection.prepareStatement(
                        "INSERT INTO schedules (billboardName, scheduledBy, schedule, duration, freqType, freqInterval) VALUES (?, ?, ?, ?, ?, ?)"
                );
                statement.clearParameters();
                statement.setString(1, billboardName);
                statement.setString(2, scheduledBy);
                statement.setObject(3, schedule);
                statement.setInt(4, duration);
                statement.setString(5, freqType);
                statement.setInt(6, Integer.parseInt(freqInterval));
            } else {
                statement = connection.prepareStatement(
                        "INSERT INTO schedules (billboardName, scheduledBy, schedule, duration, freqType) VALUES (?, ?, ?, ?, ?)"
                );
                statement.clearParameters();
                statement.setString(1, billboardName);
                statement.setString(2, scheduledBy);
                statement.setObject(3, schedule);
                statement.setInt(4, duration);
                statement.setString(5, freqType);
            }

            statement.executeUpdate();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Delete schedule from the schedules table.
     *
     * @param billboardName of the billboard in the schedule.
     * @param schedule DateTime of the schedule.
     */
    public static void deleteScheduleFromDB(String billboardName, LocalDateTime schedule) {
        // try to delete the schedule from the schedules table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM schedules WHERE billboardName=? AND schedule=?"
            );
            statement.clearParameters();
            statement.setString(1, billboardName);
            statement.setObject(2, schedule);
            statement.executeUpdate();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Get all the schedules from the schedules table.
     *
     * @return a schedule ArrayList of all the schedules in the schedules table.
     */
    public static ArrayList<Schedule> getSchedules() {
        // schedule ArrayList to store all the schedules from the schedules table
        ArrayList<Schedule> schedules = new ArrayList<>();

        // try to get all the schedules from the schedules table
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM schedules");

            // add the schedule into schedules if result set is not empty
            while (resultSet.next()) {
                schedules.add(new Schedule(
                        resultSet.getString("billboardName"),
                        resultSet.getString("scheduledBy"),
                        resultSet.getObject("schedule").toString(),
                        resultSet.getInt("duration"),
                        resultSet.getString("freqType"),
                        resultSet.getInt("freqInterval")
                ));
            }

            // close result set
            resultSet.close();

            // close statement and connection object
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
        // return schedules
        return schedules;
    }

    /**
     * Get the currently scheduled billboard's content.
     *
     * @return a string of the billboard's content.
     */
    public static String getCurrentlyScheduledBillboard() {
        // string to store the billboard's content that will be sent to the client
        String billboardContent = "";
        String billboardName;

        // int array to store the scheduleIds of the schedules that are currently scheduled
        ArrayList<Integer> schedulesCurrentlyScheduled = new ArrayList<>();

        // try to get the content of the currently scheduled billboard
        try {
            // create new connection and statement object
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();

            // get all the schedules and see if any are scheduled in between LocalDateTime.now() and the duration of the schedule
            ResultSet resultSet = statement.executeQuery("SELECT scheduleId, schedule, duration FROM schedules");
            statement.close();

            // check if result set is empty, then return billboardContent
            if (!resultSet.next()) { return billboardContent; }
            resultSet.beforeFirst();

            while (resultSet.next()) {
                LocalDateTime schedule = resultSet.getTimestamp("schedule").toLocalDateTime();
                int duration = resultSet.getInt("duration");
                // check if this LocalDateTime.now() is between the schedule and the schedule plus the duration
                if (LocalDateTime.now().isAfter(schedule) && LocalDateTime.now().isBefore(schedule.plusMinutes(duration))) {
                    // add this schedule's scheduleId into schedulesCurrentlyScheduled
                    schedulesCurrentlyScheduled.add(resultSet.getInt("scheduleId"));
                }
            }

            // if schedulesCurrentlyScheduled.size() == 0, return billboardContent
            if (schedulesCurrentlyScheduled.size() == 0) { return billboardContent; }

            // generate a (?, ?, ...) for however many elements are in schedulesCurrentlyScheduled
            String questionMarks = "(";
            for (int i = 0; i < schedulesCurrentlyScheduled.size(); i++) {
                if (i == schedulesCurrentlyScheduled.size() - 1) {
                    questionMarks += "?)";
                } else {
                    questionMarks += "?, ";
                }
            }

            // get the billboardName of the billboard from the latest created schedule that is currently scheduled
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format("SELECT scheduleId, billboardName FROM schedules WHERE scheduleId IN %s ORDER BY creationDate DESC", questionMarks)
            );
            preparedStatement.clearParameters();
            for (int i = 1; i <= schedulesCurrentlyScheduled.size(); i++) {
                preparedStatement.setInt(i, schedulesCurrentlyScheduled.get(i - 1));
            }
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            billboardName = resultSet.getString("billboardName");

            // get this billboard's content
            preparedStatement = connection.prepareStatement(
                    "SELECT content FROM billboards WHERE billboardName=?"
            );
            preparedStatement.clearParameters();
            preparedStatement.setString(1, billboardName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            billboardContent = resultSet.getString("content");

            // close result set
            resultSet.close();

            // close statement and connection object
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
        // return billboardContent
        return billboardContent;
    }
}
