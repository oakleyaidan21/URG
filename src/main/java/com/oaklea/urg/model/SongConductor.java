package com.oaklea.urg.model;

import javafx.scene.media.MediaPlayer;

/**
 * Watches a MediaPlayer object and reconciles its current position with an
 * internal clock
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SongConductor {

    double position;
    double previousPosition;
    double previousFrameTime;
    MediaPlayer player;

    /**
     * Creates a new SongConductor for a player
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param player the player to watch
     */
    public SongConductor(MediaPlayer player) {
        this.position = 0;
        this.player = player;
        this.previousPosition = 0;
        this.previousFrameTime = 0;
    }

    /**
     * Returns the length of the song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the length of the song in seconds
     */
    public double songLength() {
        return this.player.totalDurationProperty().get().toSeconds();
    }

    /**
     * Updates this objects reference to the song's position
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return 0 if the song hasn't ended, 1 otherwise
     */
    public int update() {
        this.position += System.nanoTime() / 1000000000.0 - this.previousFrameTime;
        this.previousFrameTime = System.nanoTime() / 1000000000.0;

        if (Math.abs(this.position - this.songLength()) < 0.05) {
            return 1; // song is over
        } else if (this.player.getCurrentTime().toSeconds() != previousPosition) {
            this.position = (this.position + this.player.getCurrentTime().toSeconds()) / 2;
            this.previousPosition = this.player.getCurrentTime().toSeconds();
        }
        return 0;
    }

    /**
     * Pauses the player
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void pause() {
        this.player.pause();
    }

    /**
     * Unpauses the player
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void unpause() {
        this.previousFrameTime = System.nanoTime() / 1000000000.0;
        this.player.play();
    }

    /**
     * Starts the player watches it
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void start() {
        this.player.stop();
        this.previousFrameTime = System.nanoTime() / 1000000000.0;

        this.player.play();
    }

    /**
     * Gets the current position
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the current position in ms
     */
    public double getPosition() {
        return this.position;
    }

}
