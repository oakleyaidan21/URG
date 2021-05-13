package com.oaklea.urg.view.UI;

import com.oaklea.urg.model.GlobalSettings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A ColorPicker for selecting the color of lanes
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class LaneColorPicker extends HBox {

    ColorLane[] lanes;

    /**
     * Creates a new LaneColorPicker
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param settings the settings this picker will affect
     */
    public LaneColorPicker(GlobalSettings settings) {
        this.setSpacing(20);
        this.setPrefHeight(500);
        this.setAlignment(Pos.TOP_CENTER);
        this.lanes = new ColorLane[settings.getLaneColors().length];
        for (int i = 0; i < this.lanes.length; i++) {
            this.lanes[i] = new ColorLane(i, settings.getLaneColors()[i], settings);
        }
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(this.lanes);
    }
}

class ColorLane extends VBox {

    Pane topColor;
    Pane[] otherColors;
    VBox otherColorBox;
    Color currentColor;
    GlobalSettings settings;
    boolean expanded = false;

    public ColorLane(int ind, Color color, GlobalSettings settings) {
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        this.topColor = new Pane();
        this.topColor.setPrefSize(50, 50);
        this.topColor.setOnMouseClicked(e -> {
            this.toggleColorPicker();
        });
        this.currentColor = color;
        this.otherColorBox = new VBox();
        this.otherColorBox.setSpacing(20);
        this.settings = settings;
        Color[] colors = this.settings.getAvailableColors();
        this.otherColors = new Pane[colors.length];
        for (int i = 0; i < colors.length; i++) {
            this.otherColors[i] = new Pane();
            this.otherColors[i]
                    .setBackground(new Background(new BackgroundFill(colors[i], CornerRadii.EMPTY, Insets.EMPTY)));
            this.otherColors[i].setPrefSize(50, 50);
            final int index = i;
            this.otherColors[i].setOnMouseClicked(e -> {
                this.settings.setLaneColor(ind, colors[index]);
                this.currentColor = colors[index];
                this.setTopColor();
            });
            this.otherColorBox.getChildren().add(this.otherColors[i]);
        }
        this.setTopColor();
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(this.topColor);
    }

    public void setTopColor() {
        this.topColor
                .setBackground(new Background(new BackgroundFill(this.currentColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void toggleColorPicker() {
        if (this.expanded) {
            this.getChildren().remove(this.otherColorBox);
            this.expanded = false;
        } else {
            this.getChildren().add(this.otherColorBox);
            this.expanded = true;
        }

    }
}
