package com.oaklea.urg.view.UI;

import java.io.File;

import com.oaklea.urg.model.Song;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

/**
 * A UI Element that displays a song playing in a mediaplayer
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SongPreviewPlayer extends HBox {

    ImageView songImage;
    StyleText nowPlaying;
    StyleText title;
    StyleText artist;
    FontIcon pauseButton;
    FontIcon shuffleButton;
    VBox songInfo;
    HBox controller;

    /**
     * Creates a new SongPreviewPlayer
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param song       the song that it will play
     * @param songPlayer the mediaplayer for the song
     */
    public SongPreviewPlayer(Song song, MediaPlayer songPlayer) {
        this.setSpacing(20);
        this.songImage = new ImageView(
                new Image(new File(song.getBackgroundPath()).toURI().toString(), 200, 200, false, true));

        this.songInfo = new VBox(10);
        this.songInfo.setAlignment(Pos.CENTER);
        this.songInfo.setPrefSize(1000, 1000);
        this.controller = new HBox(10);
        this.controller.setAlignment(Pos.CENTER);
        this.pauseButton = new FontIcon("mdi-pause");
        this.shuffleButton = new FontIcon("mdi-shuffle-variant");
        this.controller.getChildren().addAll(this.pauseButton, this.shuffleButton);

        this.nowPlaying = new StyleText("Now Playing:");
        this.title = new StyleText(song.getTitle());
        this.artist = new StyleText("by " + song.getArtist());

        this.songInfo.getChildren().addAll(this.nowPlaying, this.title, this.artist, this.controller);

        this.setPrefSize(400, 200);
        this.addStyle();

        this.getChildren().addAll(this.songImage, this.songInfo);
    }

    private void addTextStyle() {
        this.nowPlaying.setTextStyle("now-playing", Color.WHITE);
        this.title.setTextStyle("preview-title", Color.hsb(219, 0.74, 1.0));
        this.artist.setTextStyle("preview-artist", Color.WHITE);
    }

    private void addStyle() {
        this.getStylesheets().add("UIStylesheet.css");
        this.addTextStyle();
        this.pauseButton.setIconSize(40);
        this.pauseButton.setIconColor(Color.WHITE);
        this.shuffleButton.setIconSize(40);
        this.shuffleButton.setIconColor(Color.WHITE);
        this.setStyle("-fx-background-color: rgb(50,50,50);");
    }

    /**
     * Gets the pause button
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the pause button
     */
    public FontIcon getPauseButton() {
        return this.pauseButton;
    }

    /**
     * Gets the shuffle button
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the shuffle button
     */
    public FontIcon getShuffleButton() {
        return this.shuffleButton;
    }
}
