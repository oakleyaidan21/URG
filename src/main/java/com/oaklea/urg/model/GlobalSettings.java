package com.oaklea.urg.model;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A class containing some global settings of the app, including volume, colors,
 * etc
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class GlobalSettings implements Serializable {

    double hitsoundVolume;
    double musicVolume;
    SerializableColor[] laneColors;

    /**
     * Creates a new GlobalSettings object
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public GlobalSettings() {
        super();
        this.hitsoundVolume = 0.1;
        this.musicVolume = 0.1;
        this.laneColors = new SerializableColor[4];
        this.laneColors[0] = new SerializableColor(Color.BLUE);
        this.laneColors[1] = new SerializableColor(Color.BLUE);
        this.laneColors[2] = new SerializableColor(Color.BLUE);
        this.laneColors[3] = new SerializableColor(Color.BLUE);
    }

    /**
     * Serializes this object to a given path
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param path the path to serialize to
     */
    public void save(String path) {
        try {
            File file = new File(path);

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Returns the list of available colors to set the settings to
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the available colors
     */
    public Color[] getAvailableColors() {
        return new Color[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PURPLE, Color.WHITE, Color.hsb(219, 0.74, 1.0)};
    }

    /**
     * Sets the color of a lane
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param index    the index of the lane to set
     * @param newColor the color to set the lane to
     */
    public void setLaneColor(int index, Color newColor) {
        this.laneColors[index] = new SerializableColor(newColor);
    }

    /**
     * Sets the music volume
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param newVolume the value to set the music volume to
     */
    public void setMusicVolume(double newVolume) {
        this.musicVolume = newVolume;
    }

    /**
     * Sets the hitsound volume
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param newVolume the value to set the hitsound volume to
     */
    public void setHitsoundVolume(double newVolume) {
        this.hitsoundVolume = newVolume;
    }

    /**
     * Returns the current colors for each lane
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the current colors for each lane
     */
    public Color[] getLaneColors() {
        Color[] colors = new Color[4];
        for (int i = 0; i < 4; i++) {
            colors[i] = this.laneColors[i].getFXColor();
        }
        return colors;
    }

    /**
     * Returns the current music volume value
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the current music volume value
     */
    public double getMusicVolume() {
        return this.musicVolume;
    }

    /**
     * Returns the current hitsound volume value
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the current hitsound volume value
     */
    public double getHitsoundVolume() {
        return this.hitsoundVolume;
    }
}

class SerializableColor implements Serializable {
    private double red;
    private double green;
    private double blue;
    private double alpha;

    public SerializableColor(Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getOpacity();
    }

    public SerializableColor(double red, double green, double blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color getFXColor() {
        return new Color(red, green, blue, alpha);
    }
}