package com.oaklea.urg.view.UI;

import com.oaklea.urg.model.Song;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * A UI Element for displaying a Song object
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SongItem extends VBox {

    Song song;

    /**
     * Creates a new SongItem based off of a given Song object
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param song the Song object to display
     */
    public SongItem(Song song) {
        this.getStylesheets().add("UIStyleSheet.css");
        this.song = song;
        Text name = new Text(this.song.getDifficulty());
        name.setFill(Color.WHITE);
        name.getStyleClass().add("song-item-title");
        this.getChildren().add(name);
        this.setHeight(100);
        this.getStyleClass().add("song-item");
    }

    /**
     * Gets the Song object tied to this item
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the Song object
     */
    public Song getSong() {
        return this.song;
    }

}
