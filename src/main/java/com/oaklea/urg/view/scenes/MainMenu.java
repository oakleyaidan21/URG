package com.oaklea.urg.view.scenes;

import com.oaklea.urg.view.UI.AudioVisualizer;
import com.oaklea.urg.view.UI.SongPreviewPlayer;
import com.oaklea.urg.view.UI.StyleText;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The MainMenu of URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class MainMenu extends Scene {

    StackPane mainPane;
    HBox contentPane;
    VBox leftColumn;
    VBox rightColumn;
    StyleText titleText;
    StyleText singlePlayerButton;
    StyleText multiPlayerButton;
    StyleText quitButton;
    StyleText settingsButton;
    SongPreviewPlayer previewPlayer;
    AudioVisualizer visualizer;

    /**
     * Creates a new MainMenu
     * 
     * @param pane the pane to put the MainMenu on
     */
    public MainMenu(StackPane pane) {
        super(pane);
        this.mainPane = pane;
        this.mainPane.setAlignment(Pos.CENTER);
        this.initPane();
    }

    private void initPane() {
        this.contentPane = new HBox();
        // init left column
        this.titleText = new StyleText("Untitled Rhythm Game");
        this.singlePlayerButton = new StyleText("Play");
        this.settingsButton = new StyleText("Settings");
        this.quitButton = new StyleText("Quit");
        this.addStyle();

        this.leftColumn = new VBox(20);
        this.leftColumn.setPadding(new Insets(0, 100, 0, 100));

        this.leftColumn.setPrefSize(1920 / 2, 1080 / 2);
        this.leftColumn.setAlignment(Pos.CENTER_LEFT);
        this.leftColumn.getChildren().addAll(this.titleText, this.singlePlayerButton, this.settingsButton,
                this.quitButton);

        // init right column
        this.rightColumn = new VBox(20);
        this.rightColumn.setPadding(new Insets(0, 100, 0, 100));
        this.rightColumn.setPrefSize(1920 / 2, 1080 / 2);
        this.rightColumn.setAlignment(Pos.CENTER);
        this.visualizer = new AudioVisualizer();

        this.contentPane.getChildren().addAll(this.leftColumn, this.rightColumn);
        this.mainPane.getChildren().addAll(this.visualizer, this.contentPane);

    }

    /**
     * Gets the audio visualizer shown on the main menu
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the audio visualizer
     */
    public AudioVisualizer getVisualizer() {
        return this.visualizer;
    }

    /**
     * Sets and displays a preview player
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param player the songpreviewplayer to show
     */
    public void setAndShowSongPreviewPlayer(SongPreviewPlayer player) {
        if (this.previewPlayer != null) {
            this.rightColumn.getChildren().remove(this.previewPlayer);
        }
        this.previewPlayer = player;
        this.rightColumn.getChildren().add(this.previewPlayer);
    }

    private void addStyle() {
        this.getStylesheets().add("UIStyleSheet.css");
        this.titleText.setTextStyle("mm-title-text", new Color(0.26, 0.52, 0.99, 1));
        this.titleText.setAlignment(Pos.CENTER_LEFT);
        this.singlePlayerButton.setTextStyle("menu-button", new Color(0.6, 0.6, 0.6, 1));
        this.singlePlayerButton.setAlignment(Pos.CENTER_LEFT);
        this.settingsButton.setTextStyle("menu-button", new Color(0.6, 0.6, 0.6, 1));
        this.settingsButton.setAlignment(Pos.CENTER_LEFT);
        this.quitButton.setTextStyle("menu-button", new Color(0.6, 0.6, 0.6, 1));
        this.quitButton.setAlignment(Pos.CENTER_LEFT);
        this.mainPane.setStyle("-fx-background-color: rgb(20, 20, 20)");
    }

    /**
     * Gets the single player button
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the single player button
     */
    public StyleText getSinglePlayerButton() {
        return this.singlePlayerButton;
    }

    /**
     * Gets the settings button
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the settings button
     */
    public StyleText getSettingsButton() {
        return this.settingsButton;
    }

    /**
     * Gets the quit button
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the quit button
     */
    public StyleText getQuitButton() {
        return this.quitButton;
    }

    /**
     * Gets the preview player
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the preview player
     */
    public SongPreviewPlayer getPreviewPlayer() {
        return this.previewPlayer;
    }

}
