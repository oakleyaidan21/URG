package com.oaklea.urg.view.visuals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;

import com.oaklea.urg.model.GameMetrics;
import com.oaklea.urg.model.GlobalSettings;

import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;

/**
 * A class dedicated to rendering the graphics of the game portion of URG
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class GameRenderer {

    Canvas canvas;
    AnchorPane anchor;
    GraphicsContext context;
    ArrayList<ArrayList<NoteEntity>> notes;
    Image background;
    boolean[] light;
    GlobalSettings settings;

    /**
     * Creates a new GameRenderer
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param canvas     the canvas to render on
     * @param anchorPane the anchor pane the canvas attaches to
     * @param background the background image of the canvas
     * @param settings   the global settings
     */
    public GameRenderer(Canvas canvas, AnchorPane anchorPane, Image background, GlobalSettings settings) {
        this.canvas = canvas;
        this.anchor = anchorPane;
        this.background = background;
        this.context = canvas.getGraphicsContext2D();
        this.notes = new ArrayList<ArrayList<NoteEntity>>();
        this.light = new boolean[4];
        this.settings = settings;
        for (int i = 0; i < 4; i++) {
            this.notes.add(new ArrayList<NoteEntity>());
        }
    }

    /**
     * Adds a NoteEntity to be rendered
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param note the note entity
     * @param lane the lane it will be in
     */
    public void addNote(NoteEntity note, int lane) {
        this.notes.get(lane).add(note);
    }

    /**
     * Removes a NoteEntity from rendering
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param note the note entity
     * @param lane the lane it's in
     */
    public void removeNote(NoteEntity note, int lane) {
        this.notes.get(lane).remove(note);
    }

    /**
     * Removes a NoteEntity from rendering from a specific index
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param index the index of the note entity to remove
     * @param lane  the lane it's in
     */
    public void removeNoteAt(int index, int lane) {
        this.notes.get(lane).remove(index);
    }

    /**
     * Gets the entities currently being rendered
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the entities currently being rendered
     */
    public ArrayList<ArrayList<NoteEntity>> getEntities() {
        return this.notes;
    }

    /**
     * Lights up a note receptor in a given lane
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param lane the lane to light up
     */
    public void lightReceptor(int lane) {
        this.light[lane] = true;
    }

    /**
     * Stops lighting up a note receptor in a given lane
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param lane the lane to stop lighting
     */
    public void unlightReceptor(int lane) {
        this.light[lane] = false;
    }

    /**
     * Renders all entities and metrics of the game
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param songPosition          the position of the song in ms
     * @param songLength            the length of the song in ms
     * @param metrics               the game metrics
     * @param secondsSinceLastFrame the seconds since the last rendering
     */
    public void render(double songPosition, double songLength, GameMetrics metrics, float secondsSinceLastFrame) {

        this.context.save();

        this.renderStats(songPosition, songLength, metrics);
        this.renderJudgement(metrics);
        this.renderNotes(songPosition);

        this.context.restore();
    }

    private void renderNotes(double songPosition) {
        int height = this.anchor.heightProperty().intValue();
        // update every note
        Iterator<ArrayList<NoteEntity>> laneItr = this.notes.iterator();
        while (laneItr.hasNext()) {
            Iterator<NoteEntity> noteItr = laneItr.next().iterator();
            while (noteItr.hasNext()) {
                NoteEntity note = noteItr.next();
                double xcoord = note.getPosition().getX();
                double ycoord = height - 90 - (((note.getNoteData().getEndTime() - songPosition * 1000) + 50)
                        / (note.getNoteData().getEndTime() - note.getNoteData().getStartTime()) * (height - 90));
                Color og = this.settings.getLaneColors()[note.getLane()];
                this.context.setFill(og);
                if (note.getNoteData().isFader()) {
                    if (ycoord < height && ycoord > 0) {
                        Color c = new Color(og.getRed(), og.getGreen(), og.getBlue(),
                                ((height - ycoord) / height) * 1.0);
                        this.context.setFill(c);
                    }

                }
                // amount of time it should be on screen for
                // left / total = how far it is in percentage
                note.setPosition(new Point2D(xcoord, ycoord));
                Point2D pos = note.getPosition();
                this.context.fillRoundRect(pos.getX(), pos.getY(), note.getWidth(), note.getHeight(), 10, 10);

            }
        }
    }

    private void renderJudgement(GameMetrics metrics) {
        int width = this.anchor.widthProperty().intValue();
        int height = this.anchor.heightProperty().intValue();
        this.context.setFont(new Font("Arial", 15));
        // if there was a hit judgement, show it
        if (metrics.getPreviousJudgement() != null) {
            this.context.setTextAlign(TextAlignment.CENTER);
            switch (metrics.getPreviousJudgement()) {
                case PERFECT:
                    this.context.setFill(Color.GOLD);
                    this.context.fillText("PERFECT", width / 2, height / 2 + 50);
                    break;
                case GREAT:
                    this.context.setFill(Color.GREEN);
                    this.context.fillText("GREAT", width / 2, height / 2 + 50);
                    break;
                case GOOD:
                    this.context.setFill(Color.YELLOW);
                    this.context.fillText("GOOD", width / 2, height / 2 + 50);
                    break;
                case OK:
                    this.context.setFill(Color.BROWN);
                    this.context.fillText("OK", width / 2, height / 2 + 50);
                    break;
                case MISS:
                    this.context.setFill(Color.RED);
                    this.context.fillText("MISS", width / 2, height / 2 + 50);
                    break;
                default:
                    break;
            }
        }
    }

    private void renderStats(double songPosition, double songLength, GameMetrics metrics) {
        int width = this.anchor.widthProperty().intValue();
        int height = this.anchor.heightProperty().intValue();
        int laneStartLocation = width / 2 - (570 / 2);

        // update progress bar
        this.context.setFill(Color.WHITE);
        this.context.setStroke(Color.WHITE);
        // draw outline
        this.context.strokeRoundRect(laneStartLocation + 10, 10, 570 - 20, 10, 5, 5);
        // draw fill
        this.context.fillRoundRect(laneStartLocation + 10, 10, ((570 - 20) * (songPosition / songLength)), 10, 5, 5);
        this.context.setTextAlign(TextAlignment.CENTER);
        this.context.setTextBaseline(VPos.CENTER);
        this.context.setFill(Color.WHITE);
        // combo
        this.context.setFont(new Font("Arial", 50));
        this.context.fillText(metrics.getCombo() + "", width / 2, height / 2);
        // accuracy
        this.context.setTextAlign(TextAlignment.RIGHT);
        this.context.setFont(new Font("Arial", 30));
        this.context.fillText(
                BigDecimal.valueOf(metrics.getAccuracy()).setScale(3, RoundingMode.HALF_UP).doubleValue() + "",
                laneStartLocation - 10, 20);
        // score
        this.context.setTextAlign(TextAlignment.LEFT);
        this.context.fillText(metrics.getScore() + "", laneStartLocation + 570 + 10, 20);

        // hit counts
        this.context.setFont(new Font("Arial", 20));
        this.context.strokeRoundRect(laneStartLocation - 70, 50, 60, 160, 5, 5);
        this.context.setFill(new Color(0, 0, 0, 0.5));
        this.context.fillRoundRect(laneStartLocation - 70, 50, 60, 160, 5, 5);

        this.context.setTextAlign(TextAlignment.CENTER);
        this.context.setFill(Color.GOLD);
        this.context.fillText(metrics.getPerfects() + "", laneStartLocation - 40, 70);
        this.context.setFill(Color.GREEN);
        this.context.fillText(metrics.getGreats() + "", laneStartLocation - 40, 100);
        this.context.setFill(Color.YELLOW);
        this.context.fillText(metrics.getGoods() + "", laneStartLocation - 40, 130);
        this.context.setFill(Color.BROWN);
        this.context.fillText(metrics.getOKs() + "", laneStartLocation - 40, 160);
        this.context.setFill(Color.RED);
        this.context.fillText(metrics.getMisses() + "", laneStartLocation - 40, 190);
    }

    /**
     * Prepares the stationary elements of the game
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void prepare() {
        int width = this.anchor.widthProperty().intValue();
        int height = this.anchor.heightProperty().intValue();
        int laneStartLocation = width / 2 - (570 / 2);

        if (this.background != null) {
            this.context.drawImage(background, 0, 0, 1920, 1080);
        }

        // add transparent "dimmer" over image
        this.context.setFill(new Color(0, 0, 0, 0.5));
        this.context.fillRect(0, 0, width, height);

        // add another dimmer for lanes
        this.context.setFill(new Color(0, 0, 0, 0.5));
        this.context.fillRect(laneStartLocation, 0, 570, height);

        // add hit line
        // this.context.setFill(Color.WHITE);
        // this.context.fillRect(laneStartLocation, height - 200, 570, 8);

        // add note receptors
        for (int i = 0; i < 4; i++) {
            if (this.light[i]) {
                context.setFill(Color.ORANGE);
            } else {
                context.setFill(Color.WHITE);
            }

            double cornerY = height - 150;
            double cornerX = (laneStartLocation + 115) + (i * 90);

            context.fillRoundRect(cornerX, cornerY, 70, 70, 10, 10);
            context.setFill(Color.BLACK);
            context.fillRoundRect(cornerX + 10, cornerY + 10, 50, 50, 10, 10);
        }

    }

}
