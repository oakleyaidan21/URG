package com.oaklea.urg.view.UI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

/**
 * An AudioVisualizer that renders a mediaplayer's frequencies as bars
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class AudioVisualizer extends AnchorPane {

    MediaPlayer player;
    Pane[] bands;
    Canvas canvas;
    boolean paused;
    GraphicsContext context;

    /**
     * Creates a new AudioVisualizer
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public AudioVisualizer() {
        this.canvas = new Canvas();
        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());
        this.context = this.canvas.getGraphicsContext2D();
        this.getChildren().add(this.canvas);

    }

    /**
     * Starts the audiovisualizer
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param player the player to attach to the visualizer
     */
    public void start(MediaPlayer player) {
        this.player = player;
        this.player.setAudioSpectrumInterval(0.016);
        this.bands = new Pane[this.player.getAudioSpectrumNumBands()];
        for (int i = 0; i < this.player.getAudioSpectrumNumBands(); i++) {
            this.bands[i] = new Pane();
            this.bands[i].setStyle("-fx-background-color: rgba(40, 40, 40, 0.5)");
            this.bands[i].setPrefWidth(10);

        }
        this.getChildren().addAll(this.bands);
        this.player
                .setAudioSpectrumListener((double timestamp, double interval, float[] magnitudes, float[] phases) -> {
                    this.update(timestamp, interval, magnitudes, phases);
                });

    }

    private void update(double timestamp, double interval, float[] magnitudes, float[] phases) {
        if (!paused) {
            this.context.save();
            this.context.fillRect(0, 0, 1920, 1080);
            for (int i = 0; i < this.player.getAudioSpectrumNumBands(); i++) {
                this.context.setFill(Color.hsb(0, 0, 0.20));
                this.context.fillRect(i * 20 + 10, this.canvas.getHeight() - 5 * Math.abs(magnitudes[i]), 10,
                        5 * Math.abs(magnitudes[i]));
            }

            this.context.restore();
        }

    }

    /**
     * Pauses the visualization
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void pause() {
        this.paused = true;
    }

    /**
     * Unpauses the visualization
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void unpause() {
        System.out.println("unpause");
        this.paused = false;
    }
}
