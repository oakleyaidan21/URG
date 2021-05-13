package com.oaklea.urg.view.UI;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The Pause Screen for URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class PauseScreen extends VBox {

    StyleText resumeText;
    StyleText exitText;

    /**
     * Creates a new PauseScreen with a set spacing
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param spacing the spacing of the buttons
     */
    public PauseScreen(int spacing) {
        super(spacing);
        this.getStylesheets().add("UIStyleSheet.css");
        this.setAlignment(Pos.CENTER);
        this.resumeText = new StyleText("Resume");
        this.exitText = new StyleText("Exit");
        this.resumeText.setTextStyle("pause-screen-button-text", Color.WHITE);
        this.exitText.setTextStyle("pause-screen-button-text", Color.WHITE);

        this.resumeText.getStyleClass().add("pause-screen-button");
        this.exitText.getStyleClass().add("pause-screen-button");
        this.getChildren().addAll(this.resumeText, this.exitText);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    }

    /**
     * Gets the resume text
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the resume text
     */
    public StyleText getResumeText() {
        return this.resumeText;
    }

    /**
     * Gets the exit text
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the exit text
     */
    public StyleText getExitText() {
        return this.exitText;
    }

}
