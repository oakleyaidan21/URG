package com.oaklea.urg.view.UI;

import java.io.File;
import java.util.ArrayList;

import com.oaklea.urg.model.Song;
import com.oaklea.urg.model.SongGroup;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * A UI Element for displaying a SongGroup
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SongGroupItem extends VBox {
    SongGroup songGroup;
    ArrayList<SongItem> songItems;
    VBox songItemPane;
    HBox songGroupPane;
    VBox songInfoPane;
    Boolean expanded = false;

    /**
     * Creates a SongGroupItem from a given SongGroup
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param group the SongGroup to display
     */
    public SongGroupItem(SongGroup group) {
        this.songGroup = group;
        this.songItems = new ArrayList<SongItem>();
        this.songInfoPane = new VBox();
        this.songGroupPane = new HBox(10);
        String bgpath = group.getImagePath();
        ImageView view = new ImageView(new Image(new File(bgpath).toURI().toString(), 100, 100, false, false));
        Text groupLabel = new Text(this.songGroup.getTitle());
        Text groupArtistLabel = new Text(this.songGroup.getArtist());
        groupLabel.setFill(Color.WHITE);
        groupArtistLabel.setFill(Color.WHITE);
        this.songInfoPane.getChildren().addAll(groupLabel, groupArtistLabel);
        this.songGroupPane.getChildren().addAll(view, this.songInfoPane);
        for (Song song : this.songGroup.getSongs()) {
            SongItem item = new SongItem(song);
            this.songItems.add(item);
        }

        this.getStylesheets().add("UIStyleSheet.css");
        this.songGroupPane.getStyleClass().add("song-group-item");
        this.getChildren().add(this.songGroupPane);

    }

    /**
     * Gets the main pane of the SongGroupItem
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the main pane
     */
    public HBox getMainPane() {
        return this.songGroupPane;
    }

    /**
     * Removes the SongItems from rendering
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void close() {
        if (this.expanded) {
            this.getChildren().remove(this.songItemPane);
            this.expanded = false;
        }

    }

    /**
     * Toggles the display of the SongItems
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void toggleSongItems() {
        if (!this.expanded) {
            renderSongItems();
        } else {
            this.close();
        }
    }

    private void renderSongItems() {
        this.songItemPane = new VBox(10);
        this.songItemPane.setPadding(new Insets(10, 40, 0, 0));
        for (SongItem songItem : this.songItems) {
            this.songItemPane.getChildren().add(songItem);
        }
        this.getChildren().add(this.songItemPane);
        this.expanded = true;
    }

    /**
     * Gets the SongItems
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the SongItems
     */
    public ArrayList<SongItem> getSongItems() {
        return this.songItems;
    }
}
