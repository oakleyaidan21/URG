package com.oaklea.urg.view.UI;

import com.oaklea.urg.model.GameMetrics;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * A UI Element for displaying a GameMetric object
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class ScoreItem extends HBox {

    StyleText grade;
    GameMetrics score;
    StyleText accuracy;
    StyleText points;
    StyleText difficulty;

    /**
     * Creates a ScoreItem based off of a given GameMetrics object
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param score the GameMetrics object
     */
    public ScoreItem(GameMetrics score) {
        this.score = score;
        this.setSpacing(20);
        this.setPadding(new Insets(10));
        this.grade = new StyleText(score.getGrade());
        this.difficulty = new StyleText("[" + score.getDifficulty() + "]");
        this.points = new StyleText(score.getScore() + " points (" + score.getMaxCombo() + "x combo)");
        this.accuracy = new StyleText(score.getAccuracy() + "%");
        this.addStyle();
        this.getChildren().addAll(this.grade, this.difficulty, this.points);
    }

    private void addStyle() {
        this.getStylesheets().add("UIStylesheet.css");
        this.getStyleClass().add("score-item");
        Color gradeColor = Color.WHITE;
        switch (this.score.getGrade()) {
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
        this.grade.setTextStyle("score-grade", gradeColor);
        this.difficulty.setTextStyle("score-item-text", Color.WHITE);
        this.points.setTextStyle("score-item-text", Color.WHITE);
        this.accuracy.setTextStyle("score-item-text", Color.WHITE);
    }

}
