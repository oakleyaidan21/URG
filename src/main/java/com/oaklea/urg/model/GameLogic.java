package com.oaklea.urg.model;

import java.io.File;
import java.util.ArrayList;
import com.oaklea.urg.view.visuals.GameRenderer;
import com.oaklea.urg.view.visuals.NoteEntity;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The main facilitator of everything that interacts with the game. Checks the
 * song's position and updates the game state and view accordingly
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public abstract class GameLogic {

    Song song;
    GameLoop loop;
    Canvas canvas;
    AnchorPane anchor;
    SongConductor conductor;
    GameRenderer renderer;
    SongNote notesOnScreen;
    Boolean autoplay;
    GlobalSettings settings;
    MediaPlayer hitsoundPlayer;
    GameMetrics metrics;
    float initialOffset;
    String hitsoundPath = new File("./bin/misc/drum-hitnormal.wav").toURI().toString();
    final int noteWidth = 50;
    final int noteHeight = 50;

    /**
     * Creates a new GameLogic object with every class it needs to interact with
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param conductor the song conductor
     * @param song      the song
     * @param autoplay  whether or not to autoplay the song
     * @param settings  the game settings
     */
    public GameLogic(SongConductor conductor, Song song, boolean autoplay, GlobalSettings settings) {
        this.song = song;
        this.conductor = conductor;
        this.autoplay = autoplay;
        this.settings = settings;
        this.initialOffset = 0f;
        this.metrics = new GameMetrics(this.song.getDifficulty());
        this.hitsoundPlayer = new MediaPlayer(new Media(new File("./bin/misc/drum-hitnormal.wav").toURI().toString()));
        this.hitsoundPlayer.setVolume(settings.getMusicVolume());
    }

    /**
     * Sets the view for the game to operate inside of
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param canvas     the canvas to draw the game on
     * @param anchorPane the anchor pane to put the canvas on
     */
    public void setView(Canvas canvas, AnchorPane anchorPane) {
        this.canvas = canvas;
        this.anchor = anchorPane;
        this.initializeCanvas();
        Image background = new Image(new File(song.getBackgroundPath()).toURI().toString(), this.canvas.getWidth(),
                this.canvas.getHeight(), false, false);

        this.renderer = new GameRenderer(this.canvas, this.anchor, background, this.settings);
    }

    private void initializeCanvas() {
        this.canvas.widthProperty().bind(this.anchor.widthProperty());
        this.canvas.heightProperty().bind(this.anchor.heightProperty());
    }

    /**
     * Starts the game loop
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void startGame() {
        this.conductor.start();
        this.loop = new GameLoop() {
            @Override
            public void tick(float secondsSinceLastFrame) {
                renderer.prepare();

                // handle note creation
                ArrayList<ArrayList<SongNote>> notes = song.getNotes();
                // check each lane to see if notes need to be spawned
                for (int i = 0; i < 4; i++) {
                    ArrayList<SongNote> lane = notes.get(i);
                    if (lane.size() > 0) {
                        SongNote next = lane.get(0);
                        if ((conductor.getPosition() * 1000) >= next.getStartTime()) {
                            NoteEntity newNote = new NoteEntity(noteWidth, noteHeight, next);
                            newNote.setPosition(anchor.getWidth() / 2 - (570 / 2) + 125 + (next.getLane() * 90), -50);
                            renderer.addNote(newNote, next.getLane());
                            lane.remove(0);
                        }
                    }
                }

                // check if notes need to be hit for autoplay
                // also check if a note has been missed
                ArrayList<ArrayList<NoteEntity>> notesOnScreen = renderer.getEntities();
                boolean hitAny = false;
                for (int i = 0; i < 4; i++) {
                    ArrayList<NoteEntity> lane = notesOnScreen.get(i);
                    if (lane.size() > 0) {
                        NoteEntity next = lane.get(0);
                        if (autoplay) {
                            if (next.getNoteData().getEndTime() <= conductor.getPosition() * 1000) {
                                if (hitNote(i)) {
                                    hitAny = true;
                                }
                            }
                        }
                        if (next.getPosition().getY() >= canvas.getHeight()) {
                            // they missed a note
                            renderer.removeNoteAt(0, i);
                            metrics.miss();
                            metrics.passedNote();
                        }
                    }
                }
                if (hitAny) {
                    playHitsound();
                }

                // update song playhead
                if (conductor.update() == 1) {
                    // stop game loop
                    this.stop();
                    // go to results screen
                    onSongEnd();
                }

                // render new game state
                renderer.render(conductor.getPosition(), conductor.songLength(), metrics, secondsSinceLastFrame);
            }
        };
        this.loop.start();
    }

    /**
     * Toggles the game's state, switching from paused to unpaused and back
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void togglePause() {
        if (this.loop.isPaused()) {
            this.conductor.unpause();
            this.loop.play();
        } else {
            this.loop.pause();
            this.conductor.pause();
        }
    }

    /**
     * Presses a lane, playing a hitsound and lighting up a note receptor
     * accordingly. Makes the game check for any notes to hit
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param lane the lane to light up and check for notes to hit
     */
    public void pressLane(int lane) {
        playHitsound();
        hitNote(lane);
        this.renderer.lightReceptor(lane);
    }

    /**
     * Releases a lane, unlighting the receptor
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param lane the lane to
     */
    public void releaseLane(int lane) {
        this.renderer.unlightReceptor(lane);
    }

    /**
     * Returns the GameMetrics for this game loop
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the GameMetrics for this game loop
     */
    public GameMetrics getMetrics() {
        return this.metrics;
    }

    /**
     * An abstract function called when the song ends
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public abstract void onSongEnd();

    private void playHitsound() {
        if (settings.getHitsoundVolume() <= 0)
            return;
        // Thread playerThread = new Thread(new Runnable() {
        // public void run() {
        // MediaPlayer player;
        // player = new MediaPlayer(new Media(hitsoundPath));
        // player.setVolume(settings.getHitsoundVolume());
        // player.play();
        // };
        // });
        // playerThread.start();
        MediaPlayer player;
        player = new MediaPlayer(new Media(hitsoundPath));
        player.setVolume(settings.getHitsoundVolume());
        player.play();
    }

    private boolean hitNote(int direction) {
        // check the next four notes and see if any of them would be hit in this
        // instance
        double duration = this.conductor.getPosition() * 1000;
        ArrayList<NoteEntity> lane = this.renderer.getEntities().get(direction);
        NoteEntity noteHit = null;
        if (lane.size() > 0) {
            // find note in lane
            for (NoteEntity next : lane) {
                double endTime = next.getNoteData().getEndTime();
                if ((endTime - 18 <= duration && duration <= endTime + 18) || this.autoplay) {
                    // perfect
                    noteHit = next;
                    this.metrics.hitPerfect(next.getNoteData());
                    break;
                } else if (endTime - 43 <= duration && duration <= endTime + 43) {
                    // great
                    noteHit = next;
                    this.metrics.hitGreat(next.getNoteData());
                    break;
                } else if (endTime - 76 <= duration && duration <= endTime + 76) {
                    // good
                    noteHit = next;
                    this.metrics.hitGood(next.getNoteData());
                    break;
                } else if (endTime - 127 <= duration && duration <= endTime + 127) {
                    // ok
                    noteHit = next;
                    this.metrics.hitOK(next.getNoteData());
                    break;
                }
            }
        }
        if (noteHit != null) {
            renderer.removeNote(noteHit, direction);
            this.metrics.passedNote();
            return true;
        }
        return false;

    }

}
