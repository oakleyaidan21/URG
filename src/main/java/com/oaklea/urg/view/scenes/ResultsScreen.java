package com.oaklea.urg.view.scenes;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.oaklea.urg.model.GameMetrics;
import com.oaklea.urg.model.Song;
import com.oaklea.urg.view.UI.StyleText;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The Results Screen for URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class ResultsScreen extends Scene {

    BorderPane mainPane;
    HBox topPane;
    HBox bottomPane;
    VBox centerPane;
    VBox leftColumn;
    StyleText backButton;
    VBox rightColumn;
    StyleText songInfo;
    StyleText grade;
    StyleText accuracy;
    StyleText score;
    GameMetrics results;
    Song song;

    /**
     * Creates a new ResultsScreen based off of GameMetrics
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param pane    the pane to attach to
     * @param metrics the metrics to display
     * @param song    the song the metrics is based off of
     */
    public ResultsScreen(BorderPane pane, GameMetrics metrics, Song song) {
        super(pane);
        this.mainPane = pane;
        this.results = metrics;

        // set up top pane
        this.topPane = new HBox();
        this.topPane.setPrefSize(1920, 70);
        this.topPane.setPadding(new Insets(0, 0, 0, 20));
        this.topPane.setAlignment(Pos.CENTER_LEFT);
        this.backButton = new StyleText("< Back");
        this.topPane.getChildren().add(this.backButton);

        // set up middle pane
        this.centerPane = new VBox(10);
        this.songInfo = new StyleText(song.getTitle() + " by " + song.getArtist() + " [" + song.getDifficulty() + "]");
        this.grade = new StyleText(metrics.getGrade());
        this.accuracy = new StyleText(
                new BigDecimal(Double.toString(metrics.getAccuracy())).setScale(2, RoundingMode.HALF_UP)
                        + "% accurate");
        this.score = new StyleText(metrics.getScore() + " points");
        this.centerPane.setPrefSize(1920, 1080);
        this.centerPane.setAlignment(Pos.CENTER);
        this.centerPane.getChildren().addAll(this.songInfo, this.grade, this.accuracy, this.score);

        // set up bottom pane
        this.bottomPane = new HBox();
        this.bottomPane.setPrefHeight(70);

        this.addStyle();
        this.mainPane.setTop(this.topPane);
        this.mainPane.setCenter(this.centerPane);
        this.mainPane.setBottom(this.bottomPane);

    }

    private void addStyle() {
        this.getStylesheets().add("UIStylesheet.css");
        Color gradeColor = Color.WHITE;
        switch (this.results.getGrade()) {
            case "S":
                gradeColor = Color.GOLD;
                break;
            case "A":
                gradeColor = Color.ORANGERED;
                break;
            case "B":
                gradeColor = Color.BLUE;
                break;
            case "C":
                gradeColor = Color.BEIGE;
                break;
            case "D":
                gradeColor = Color.BROWN;
                break;
            case "F":
                gradeColor = Color.RED;
                break;
            default:
                gradeColor = Color.WHITE;
                break;
        }
        this.songInfo.setTextStyle("result-title", Color.WHITE);
        this.grade.setTextStyle("result-grade", gradeColor);
        this.score.setTextStyle("result-score", Color.WHITE);
        this.accuracy.setTextStyle("result-accuracy", Color.WHITE);
        this.backButton.setTextStyle("back-button", Color.WHITE);
        this.centerPane.setStyle("-fx-background-color: rgb(50, 50, 50)");

        this.mainPane.setStyle("-fx-background-color: rgb(20, 20, 20)");

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
}
