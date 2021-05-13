package com.oaklea.urg.view.visuals;

import com.oaklea.urg.model.SongNote;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * Represents a note "block" that will render on screen
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class NoteEntity {

    Point2D position;
    Rectangle rect;
    double width;
    double height;
    SongNote data;

    /**
     * 
     * @param width
     * @param height
     * @param data
     */
    public NoteEntity(double width, double height, SongNote data) {
        this.rect = new Rectangle(width, height);
        this.width = width;
        this.height = height;
        this.data = data;
    }

    /**
     * Gets the lane this NoteEntity is in
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the lane this NoteEntity is in
     */
    public int getLane() {
        return this.data.getLane();
    }

    /**
     * Gets the position of the note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the position of the note
     */
    public Point2D getPosition() {
        return position;
    }

    /**
     * Sets the position of the note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setPosition(double x, float y) {
        this.position = new Point2D(x, y);
    }

    /**
     * Sets the position of the note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param newPos the new position of the note
     */
    public void setPosition(Point2D newPos) {
        this.position = newPos;
    }

    /**
     * Gets the width of the note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the width of the note
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the note
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the height of the note
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Gets the note data associated with this entity
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the note data
     */
    public SongNote getNoteData() {
        return this.data;
    }

}
