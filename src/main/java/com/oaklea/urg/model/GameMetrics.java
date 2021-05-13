package com.oaklea.urg.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class containing all significant metrics of a "play" of a song
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class GameMetrics implements Serializable {

    double notesPassed;
    double notesHit;
    int combo;
    int maxCombo;
    int score;
    HitJudgement previousJudgement;
    int perfects;
    int greats;
    int goods;
    int oks;
    int misses;
    String difficulty;

    /**
     * Creates a new GameMetrics object for a given difficulty
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param diff
     */
    public GameMetrics(String diff) {
        super();
        this.difficulty = diff;
    }

    /**
     * Modifies the GameMetrics for when a "perfect" is hit
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param note the note that was hit
     */
    public void hitPerfect(SongNote note) {
        this.notesHit += 1;
        this.combo++;
        this.score += this.combo * note.getPointValue();
        this.perfects++;
        this.previousJudgement = HitJudgement.PERFECT;
    }

    /**
     * Modifies the GameMetrics for when a "great" is hit
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param note the note that was hit
     */
    public void hitGreat(SongNote note) {
        this.notesHit += 0.98;
        this.combo++;
        this.greats++;
        this.score += this.combo * note.getPointValue();
        this.previousJudgement = HitJudgement.GREAT;
    }

    /**
     * Modifies the GameMetrics for when a "good" is hit
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param note the note that was hit
     */
    public void hitGood(SongNote note) {
        this.notesHit += 0.65;
        this.combo++;
        this.goods++;
        this.score += this.combo * note.getPointValue();
        this.previousJudgement = HitJudgement.GOOD;

    }

    /**
     * Modifies the GameMetrics for when an "ok" is hit
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param note the note that was hit
     */
    public void hitOK(SongNote note) {
        this.notesHit += 0.25;
        this.combo++;
        this.oks++;
        this.score += this.combo * note.getPointValue();
        this.previousJudgement = HitJudgement.OK;
    }

    /**
     * Modifies the GameMetrics for when a note is missed
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void miss() {
        this.misses++;
        if (this.combo > this.maxCombo) {
            this.maxCombo = this.combo;
        }
        this.previousJudgement = HitJudgement.MISS;
        this.combo = 0;
    }

    /**
     * Returns the difficulty of this GameMetric
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the difficulty
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Serializes this GameMetric to a given path
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param path the path to serialize this object to
     */
    public void save(String path) {

        // read in arraylist of scores
        ArrayList<GameMetrics> scores = new ArrayList<GameMetrics>();
        try {
            if (new File(path).length() != 0) { // check if file empty first
                FileInputStream fis = new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                if (obj instanceof ArrayList<?>) {
                    ArrayList<?> al = (ArrayList<?>) obj;
                    for (int i = 0; i < al.size(); i++) {
                        Object o = al.get(i);
                        if (o instanceof GameMetrics) {
                            GameMetrics gm = (GameMetrics) o;
                            scores.add(gm);
                        }
                    }
                }
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // add to arraylist
        scores.add(this);

        // seralize arraylist again
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(scores);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Modifies the GameMetrics by incrementing the amount of notes passed
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     */
    public void passedNote() {
        this.notesPassed += 1;
    }

    /**
     * Returns the accuracy of this GameMetric
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the accuracy
     */
    public double getAccuracy() {
        if (this.notesPassed == 0) {
            return 100.0;
        }
        return this.notesHit / this.notesPassed * 100;
    }

    /**
     * Returns a letter grade based on the GameMetrics
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return a letter grade
     */
    public String getGrade() {
        String ret = "";
        double acc = this.getAccuracy();
        if (this.misses == 0) {
            ret = "S";
        } else if (acc >= 92) {
            ret = "A";
        } else if (acc >= 80) {
            ret = "B";
        } else if (acc >= 70) {
            ret = "C";
        } else if (acc >= 60) {
            ret = "D";
        } else {
            ret = "F";
        }
        return ret;
    }

    /**
     * Returns the current combo number
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the current combo number
     */
    public int getCombo() {
        return this.combo;
    }

    /**
     * Returns the maximum combo of this play
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the maximum combo
     */
    public int getMaxCombo() {
        return this.maxCombo > 0 ? this.maxCombo : this.combo;
    }

    /**
     * Returns the score of this play
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the score of this play
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Returns the amount of perfects hit in this play
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the amount of perfects hit
     */
    public int getPerfects() {
        return this.perfects;
    }

    /**
     * Returns the amount of greats hit in this play
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the amount of greats hit
     */
    public int getGreats() {
        return this.greats;
    }

    /**
     * Returns the amount of goods hit in this play
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the amount of goods hit
     */
    public int getGoods() {
        return this.goods;
    }

    /**
     * Returns the amount of oks hit in this play
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the amount of oks hit
     */
    public int getOKs() {
        return this.oks;
    }

    /**
     * Returns the amount of misses hit in this play
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the amount of misses hit
     */
    public int getMisses() {
        return this.misses;
    }

    /**
     * Returns the latest hitjudgement
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the last hitjudgement
     */
    public HitJudgement getPreviousJudgement() {
        return this.previousJudgement;
    }
}
