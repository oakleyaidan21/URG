package com.oaklea.urg.view.scenes;

import com.jfoenix.controls.JFXSlider;
import com.oaklea.urg.model.GlobalSettings;
import com.oaklea.urg.view.UI.LaneColorPicker;
import com.oaklea.urg.view.UI.StyleText;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The Settings Screen for URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SettingsScreen extends Scene {

    BorderPane mainPane;
    HBox topPane;
    VBox centerPane;
    StyleText backButton;
    HBox bottomPane;
    StyleText musicVolumeLabel;
    StyleText saveText;
    JFXSlider musicVolumeSlider;
    LaneColorPicker colorPicker;
    StyleText hitsoundVolumeLabel;
    JFXSlider hitsoundVolumeSlider;
    GlobalSettings settings;

    /**
     * Creates a new SettingsScreen with global settings
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param pane
     * @param settings
     */
    public SettingsScreen(BorderPane pane, GlobalSettings settings) {
        super(pane);
        this.mainPane = pane;
        this.settings = settings;
        initPane();
    }

    private void initPane() {
        // set up top pane
        this.topPane = new HBox();
        this.topPane.setPrefSize(1920, 70);
        this.backButton = new StyleText("< Back");
        this.topPane.getChildren().add(this.backButton);
        this.topPane.setPadding(new Insets(0, 0, 0, 20));

        // set up center pane
        this.centerPane = new VBox(20);
        this.centerPane.setAlignment(Pos.CENTER);
        this.centerPane.setPadding(new Insets(0, 700, 0, 700));
        this.musicVolumeLabel = new StyleText("Music Volume");
        this.musicVolumeSlider = new JFXSlider(0, 100, this.settings.getMusicVolume() * 100);
        this.hitsoundVolumeLabel = new StyleText("Hitsound Volume");
        this.hitsoundVolumeSlider = new JFXSlider(0, 100, this.settings.getMusicVolume() * 100);
        this.colorPicker = new LaneColorPicker(this.settings);
        this.saveText = new StyleText("Save");
        this.saveText.setOnMouseClicked(e -> {
            this.settings.save("./bin/settings.gs");
        });
        this.centerPane.getChildren().addAll(this.musicVolumeLabel, this.musicVolumeSlider, this.hitsoundVolumeLabel,
                this.hitsoundVolumeSlider, this.colorPicker, this.saveText);

        // set up bottom pane
        this.bottomPane = new HBox();
        this.bottomPane.setPrefSize(1920, 70);

        this.addStyle();
        this.mainPane.setTop(this.topPane);
        this.mainPane.setCenter(this.centerPane);
        this.mainPane.setBottom(this.bottomPane);
    }

    private void addStyle() {
        this.getStylesheets().add("UIStylesheet.css");
        this.backButton.setTextStyle("back-button", Color.WHITE);
        this.musicVolumeLabel.setTextStyle("settings-field", Color.WHITE);
        this.hitsoundVolumeLabel.setTextStyle("settings-field", Color.WHITE);
        this.saveText.setTextStyle("settings-field", Color.WHITE);
        this.centerPane.setStyle("-fx-background-color: rgb(50, 50, 50)");
        this.mainPane.setStyle("-fx-background-color: rgb(20, 20, 20)");
    }

    /**
     * Gets the music volume slider
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the music volume slider
     */
    public JFXSlider getMusicVolumeSlider() {
        return this.musicVolumeSlider;
    }

    /**
     * Gets the hitsound volume slider
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the hitsound volume slider
     */
    public JFXSlider getHitsoundVolumeSlider() {
        return this.hitsoundVolumeSlider;
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
