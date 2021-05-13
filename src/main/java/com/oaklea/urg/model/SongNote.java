package com.oaklea.urg.model;

/**
 * Represents a note in the game, including its timing and point value
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SongNote {

    int lane;
    double startTime;
    double endTime;
    double pointValue;
    int id;
    boolean fader;

    /**
     * Creates a new SongNote with the given parameters
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param id        the id of the note
     * @param lane      the lane the not will reside in
     * @param value     the point value of this note
     * @param startTime the start time of this note's appearance
     * @param endTime   the end time of this note's appearance
     * @param fader     whether this note "fades" as it goes down the screen
     */
    public SongNote(int id, int lane, double value, double startTime, double endTime, boolean fader) {
        this.id = id;
        this.lane = lane;
        this.pointValue = value;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fader = fader;
    }

    /**
     * Gets the start time of this note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the start time of this note
     */
    public double getStartTime() {
        return this.startTime;
    }

    /**
     * Gets the end time of this note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the end time of this note
     */
    public double getEndTime() {
        return this.endTime;
    }

    /**
     * Gets the lane of this note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the lane of this note
     */
    public int getLane() {
        return this.lane;
    }

    /**
     * Gets the point value of this note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the point value of this note
     */
    public double getPointValue() {
        return this.pointValue;
    }

    /**
     * Returns whether or not this note is a fader
     * 
     * 
     * @return if this note is a fader
     */
    public boolean isFader() {
        return this.fader;
    }
}
