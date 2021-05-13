package com.oaklea.urg.view.scenes;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

import com.oaklea.urg.model.GameMetrics;
import com.oaklea.urg.model.Song;
import com.oaklea.urg.model.SongGroup;
import com.oaklea.urg.view.UI.ScoreItem;
import com.oaklea.urg.view.UI.SongGroupItem;
import com.oaklea.urg.view.UI.StyleText;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The Song Picker for URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SongPicker extends Scene {

    BorderPane mainPane;
    HBox topPane;
    HBox centerPane;
    VBox leftColumn;
    VBox rightColumn;
    VBox scoresBox;
    Pane bottomPane;
    ScrollPane scrollPane;
    VBox songListPane;
    StyleText backButton;
    StyleText noScores;
    ArrayList<SongGroupItem> songGroupItems;
    ArrayList<SongGroup> songGroups;

    /**
     * Creates a SongPicker that renders song groups
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param songGroups the song groups to render
     * @param pane       the pane to attach to
     */
    public SongPicker(ArrayList<SongGroup> songGroups, BorderPane pane) {
        super(pane);
        this.mainPane = pane;
        this.scrollPane = new ScrollPane();
        this.songGroups = songGroups;
        initPane();
        renderSongGroups();
    }

    private void initPane() {
        this.centerPane = new HBox();

        // set up top pane
        this.topPane = new HBox();
        this.topPane.setPrefSize(1920, 70);
        this.topPane.setPadding(new Insets(0, 0, 0, 20));
        this.topPane.setAlignment(Pos.CENTER_LEFT);
        this.backButton = new StyleText("< Back");
        this.topPane.getChildren().add(this.backButton);

        // set up middle pane
        // left side
        this.leftColumn = new VBox();
        this.leftColumn.setPrefWidth(1920 / 2);
        this.songListPane = new VBox(10);
        this.songListPane.setPadding(new Insets(10, 0, 10, 10));
        this.songListPane.setPrefWidth(1920 / 3);
        this.scrollPane.setContent(songListPane);
        this.scrollPane.getStyleClass().add("scroll-pane");
        this.scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.leftColumn.getChildren().add(this.scrollPane);
        // right side
        this.rightColumn = new VBox();
        this.scoresBox = new VBox(20);
        this.scoresBox.setPrefHeight(1080);
        this.scoresBox.setPadding(new Insets(20));
        this.rightColumn.setPrefWidth(1920 / 2);
        this.noScores = new StyleText("No scores");
        this.rightColumn.setPadding(new Insets(10));
        this.rightColumn.getChildren().add(this.scoresBox);
        this.centerPane.getChildren().addAll(this.leftColumn, this.rightColumn);

        // set up bottom pane
        this.bottomPane = new Pane();
        this.bottomPane.setPrefHeight(70);

        this.addStyle();
        this.mainPane.setTop(this.topPane);
        this.mainPane.setCenter(this.centerPane);
        this.mainPane.setBottom(this.bottomPane);

    }

    private void addStyle() {
        this.getStylesheets().add("UIStylesheet.css");
        this.backButton.setTextStyle("back-button", Color.WHITE);
        this.mainPane.setStyle("-fx-background-color: rgb(20, 20, 20)");
        this.scoresBox.getStyleClass().add("scores-box");
        this.noScores.setTextStyle("back-button", Color.WHITE);

    }

    /**
     * Sets the background image of this scene
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param imagePath the path to the new background image
     */
    public void setBackgroundImage(String imagePath) {
        this.scrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        BackgroundImage bi = new BackgroundImage(
                new Image(new File(imagePath).toURI().toString(), 1920, 1080, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        this.scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        this.centerPane.setBackground(new Background(bi));

    }

    private void renderSongGroups() {
        this.songGroupItems = new ArrayList<SongGroupItem>();
        for (SongGroup group : this.songGroups) {
            SongGroupItem item = new SongGroupItem(group);
            this.songGroupItems.add(item);
            this.songListPane.getChildren().add(item);
        }

    }

    public void renderScores(Song song) {
        ArrayList<GameMetrics> scores = song.getScores();
        this.scoresBox.getChildren().clear();
        if (scores.size() == 0) {
            this.scoresBox.setAlignment(Pos.CENTER);
            this.scoresBox.getChildren().add(this.noScores);
        } else {
            // sort scores
            scores.sort(new Comparator<GameMetrics>() {
                @Override
                public int compare(GameMetrics o1, GameMetrics o2) {
                    return o2.getScore() - o1.getScore();
                }
            });
            for (GameMetrics gm : scores) {
                this.scoresBox.setAlignment(Pos.TOP_CENTER);
                this.scoresBox.getChildren().add(new ScoreItem(gm));

            }
        }
    }

    /**
     * Gets the back button
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the back button
     */
    public StyleText getBackButton() {
        return this.backButton;
    }

    /**
     * Gets the SongGroupItems
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the SongGroupItems
     */
    public ArrayList<SongGroupItem> getSongGroupItems() {
        return this.songGroupItems;
    }

}
