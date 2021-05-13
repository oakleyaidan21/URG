package com.oaklea.urg.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * A class to contain the metadata and note data for a song
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class Song {

    String title;
    String path;
    String artist;
    int bpm;
    int previewTime;
    String difficulty;
    String mediaPath;
    ArrayList<ArrayList<SongNote>> notes;
    ArrayList<GameMetrics> scores;
    File songFile;

    /**
     * Creates a new Song from a songFile. Sets all data except the note data
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param songFile the file to read in
     */
    public Song(File songFile) {
        this.path = songFile.getAbsolutePath();
        this.songFile = songFile;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(songFile));
            this.title = reader.readLine();
            this.artist = reader.readLine();
            this.difficulty = reader.readLine();
            this.previewTime = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.readInScores();
    }

    private void readInScores() {
        // read in arraylist of scores
        this.scores = new ArrayList<GameMetrics>();
        try {

            if (new File(this.getScorePath()).length() != 0) { // check if no scores
                FileInputStream fis = new FileInputStream(this.getScorePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                if (obj instanceof ArrayList<?>) {
                    ArrayList<?> al = (ArrayList<?>) obj;
                    for (int i = 0; i < al.size(); i++) {
                        Object o = al.get(i);
                        if (o instanceof GameMetrics) {
                            GameMetrics gm = (GameMetrics) o;
                            this.scores.add(gm);
                        }
                    }
                }

                ois.close();
                fis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads in the notes for the song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void readInNotes() {
        BufferedReader reader;
        this.notes = new ArrayList<ArrayList<SongNote>>();
        for (int i = 0; i < 4; i++) {
            this.notes.add(new ArrayList<SongNote>());
        }
        try {

            reader = new BufferedReader(new FileReader(songFile));
            int count = 1;
            String line = reader.readLine();
            while (line != null) {
                if (count > 4) {
                    String[] split = line.split(" ");
                    if (split.length >= 4) {
                        int direction = Integer.parseInt(split[0]);
                        SongNote note = new SongNote((count - 4), direction, Integer.parseInt(split[1]),
                                Integer.parseInt(split[2]), Integer.parseInt(split[3]), split.length == 5);
                        this.notes.get(direction).add(note);
                    }

                }
                count++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the scores for this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the scores for this song
     */
    public ArrayList<GameMetrics> getScores() {
        return this.scores;
    }

    /**
     * Gets the notes for this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the notes for this song
     */
    public ArrayList<ArrayList<SongNote>> getNotes() {
        return this.notes;
    }

    /**
     * Gets the path of the song file
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the path of the song file
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Gets the title of this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the title of this song
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the artist of this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the artist of this song
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Gets the song file for this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the song file for this song
     */
    public File getFile() {
        return this.songFile;
    }

    /**
     * Gets the path for the audio of this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the path for the audio of this song
     */
    public String getAudioPath() {
        String path = this.songFile.getParentFile().getPath();
        return path + "/audio.mp3/";
    }

    /**
     * Gets the path of the background image of this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the path of the background image of this song
     */
    public String getBackgroundPath() {
        String path = this.songFile.getParentFile().getAbsolutePath() + "/bg.png/";
        return path;
    }

    /**
     * Gets the difficulty of this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the difficulty of this song
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Gets the preview time of this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the preview time of this song in ms
     */
    public int getPreviewTime() {
        if (this.previewTime < 0)
            return 0;
        return this.previewTime;
    }

    /**
     * Gets the path for the score file of this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the path for the score file of this song
     */
    public String getScorePath() {
        String path = this.songFile.getParentFile().getPath();
        return path + "/scores.gm/";
    }

    /**
     * Adds a score to this song
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param score new score to add
     */
    public void addScore(GameMetrics score) {
        this.scores.add(score);
    }
}
