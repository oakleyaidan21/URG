package com.oaklea.urg.view.UI;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Text that is easily styled
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class StyleText extends VBox {

    private Text label;

    /**
     * Creates a new StyleText object with a given string
     * 
     * @param text the string to display
     */
    public StyleText(String text) {
        this.label = new Text(text);
        this.setAlignment(Pos.CENTER);
        this.setHeight(50);
        this.setWidth(100);
        this.getChildren().add(this.label);
    }

    /**
     * Sets the style of the text of this object
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param styleClass the styleclass to set the text to
     * @param color      the color of the text
     */
    public void setTextStyle(String styleClass, Color color) {
        this.label.getStyleClass().add(styleClass);
        this.label.setFill(color);
    }

}
