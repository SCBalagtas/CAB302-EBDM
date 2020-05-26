package Classes;

import java.io.Serializable;

/**
 * Author: Steven Balagtas
 *
 * This class is the schedule object.
 */

public class Schedule implements Serializable {
    /**
     * Private fields for the schedule object.
     */
    private String billboardName;
    private String scheduledBy;
    private String schedule;
    private int duration;
    private String freqType;
    private int freqInterval;

    /**
     * Constructor to create a new instance of a schedule.
     *
     * @param billboardName a string of the name of the billboard that's scheduled.
     * @param scheduledBy a string of the username of the user who created the schedule.
     * @param schedule a string of the datetime that the billboard is scheduled to run at.
     * @param duration an int representing how long the billboard will run for in minutes.
     * @param freqType a string of the frequency type of the schedule. Can be null.
     * @param freqInterval an int representing the interval in minutes of the frequency of the schedule.
     *                     Only defined if the 'Minutes' option is selected for the freqType. Can be null.
     */
    public Schedule(String billboardName, String scheduledBy, String schedule, int duration, String freqType, int freqInterval) {
        this.billboardName = billboardName;
        this.scheduledBy = scheduledBy;
        this.schedule = schedule;
        this.duration = duration;
        this.freqType = freqType;
        this.freqInterval = freqInterval;
    }

    /**
     * Get the billboard's name.
     *
     * @return a string of the billboard's name.
     */
    public String getBillboardName() { return this.billboardName; }

    /**
     * Get the scheduler's username.
     *
     * @return a string of the username of the user who created the schedule.
     */
    public String getScheduledBy() { return this.scheduledBy; }

    /**
     * Get the date and time of this schedule.
     *
     * @return a string of the date and time of the schedule.
     */
    public String getSchedule() { return this.schedule; }

    /**
     * Get the duration of this schedule.
     *
     * @return an int of the duration of the schedule in minutes.
     */
    public int getDuration() { return this.duration; }

    /**
     * Get the frequency type for this schedule.
     *
     * @return a string of the frequency type of the schedule. Can be null.
     */
    public String getFreqType() { return this.freqType; }

    /**
     * Get the frequency interval for this schedule.
     *
     * @return an int of the interval of the frequency of the schedule in minutes. Can be null.
     */
    public int getFreqInterval() { return this.freqInterval; }
}
