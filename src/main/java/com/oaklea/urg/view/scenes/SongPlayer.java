package com.oaklea.urg.view.scenes;

import com.oaklea.urg.model.GameLogic;
import com.oaklea.urg.model.GlobalSettings;
import com.oaklea.urg.model.Song;
import com.oaklea.urg.view.UI.PauseScreen;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * The SongPlayer for URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SongPlayer extends Scene {

    Pane mainPane;
    BorderPane gamePane;
    PauseScreen pauseScreen;
    GameLogic logic;
    Canvas gameCanvas;
    AnchorPane gameAnchor;
    Song song;
    boolean isPaused;
    boolean autoplay;
    GlobalSettings settings;

    /**
     * Creates a new Song Player with the given parameters
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param song     the song that will play
     * @param pane     the pane to attach to
     * @param autoplay whether the game will play automatically or not
     * @param logic    the game logic
     * @param settings the global settings
     */
    public SongPlayer(Song song, Pane pane, boolean autoplay, GameLogic logic, GlobalSettings settings) {
        super(pane);
        this.mainPane = pane;
        this.song = song;
        this.logic = logic;
        this.autoplay = autoplay;
        this.settings = settings;
        initGamePane();
        initPausePane();
        setupKeyListener();
        initGameLogic();

    }

    private void initGamePane() {
        this.gamePane = new BorderPane();
        // set up canvas
        this.gameAnchor = new AnchorPane();
        this.gameCanvas = new Canvas();
        GraphicsContext gc = this.gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BISQUE);
        this.gameAnchor.getChildren().add(this.gameCanvas);
        this.gamePane.setCenter(this.gameAnchor);
        this.mainPane.getChildren().add(this.gamePane);
        this.logic.setView(this.gameCanvas, this.gameAnchor);

    }

    private void initPausePane() {
        this.pauseScreen = new PauseScreen(20);
    }

    private void initGameLogic() {
        this.song.readInNotes();
        this.logic.startGame();
    }

    /**
     * Gets the pause screen
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the pause screen
     */
    public PauseScreen getPauseScreen() {
        return this.pauseScreen;
    }

    /**
     * Toggles the pause screen
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void togglePauseScreen() {
        if (this.isPaused) {
            this.mainPane.getChildren().remove(this.pauseScreen);
            this.isPaused = false;

        } else {
            this.isPaused = true;
            this.mainPane.getChildren().add(this.pauseScreen);
        }
        this.logic.togglePause();
    }

    private void setupKeyListener() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ESCAPE:
                        togglePauseScreen();
                        break;
                    case T:
                        break;
                    case D:
                        if (!autoplay)
                            logic.pressLane(0);
                        break;
                    case F:
                        if (!autoplay)
                            logic.pressLane(1);
                        break;
                    case J:
                        if (!autoplay)
                            logic.pressLane(2);
                        break;
                    case K:
                        if (!autoplay)
                            logic.pressLane(3);
                        break;
                    default:
                        break;
                }
            }
        });

        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case D:
                        if (!autoplay)
                            logic.releaseLane(0);
                        break;
                    case F:
                        if (!autoplay)
                            logic.releaseLane(1);
                        break;
                    case J:
                        if (!autoplay)
                            logic.releaseLane(2);
                        break;
                    case K:
                        if (!autoplay)
                            logic.releaseLane(3);
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
