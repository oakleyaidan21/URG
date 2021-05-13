package com.oaklea.urg.model;

import javafx.animation.AnimationTimer;

/**
 * A class for creating a 60fps loop. Provides a function to be overridden that
 * is called 60 times a second. Can be paused.
 * 
 *
 * @author Aidan Oakley
 * @version 1.0.0
 */
public abstract class GameLoop extends AnimationTimer {

    long pauseStart;
    long animationStart;
    long lastFrameTimeNanos;
    boolean paused;
    boolean active;
    boolean pauseScheduled;
    boolean playScheduled;

    /**
     * Returns whether or not the loop is paused
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return whether or not the game is paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Pauses the loop
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void pause() {
        if (!this.paused) {
            pauseScheduled = true;
        }
    }

    /**
     * Resumes or plays the loop
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void play() {
        if (this.paused) {
            playScheduled = true;
        }
    }

    /**
     * Starts the loop
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    @Override
    public void start() {
        super.start();
        this.active = true;
    }

    /**
     * Stops the loop
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    @Override
    public void stop() {
        super.stop();
        this.pauseStart = 0;
        this.paused = false;
        this.active = false;
        this.pauseScheduled = false;
        this.playScheduled = false;
    }

    /**
     * Overrides AnimationTimer's handle function to be called 60 times a second.
     * Calculates the time between frames and calls the "tick" function
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param now the current time in nano seconds
     */
    @Override
    public void handle(long now) {

        if (this.pauseScheduled) {
            this.pauseStart = now;
            this.paused = true;
            this.pauseScheduled = false;
        }

        if (this.playScheduled) {
            this.animationStart += (now - this.pauseStart);
            this.paused = false;
            this.playScheduled = false;
        }

        if (!this.paused) {
            float secondsSinceLastFrame = (float) ((now - this.lastFrameTimeNanos) / 1e9);
            this.lastFrameTimeNanos = now;
            if (secondsSinceLastFrame < 20000) {
                tick(secondsSinceLastFrame);
            }

        }

    }

    /**
     * An abstract function that is called once every 60 seconds
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param secondsSinceLastFrame the amount of seconds elapsed since the last
     *                              frame
     */
    public abstract void tick(float secondsSinceLastFrame);

}
