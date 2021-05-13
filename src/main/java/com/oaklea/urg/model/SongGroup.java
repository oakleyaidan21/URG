package com.oaklea.urg.model;

import java.util.ArrayList;

/**
 * Represents a group of songs
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class SongGroup {

    ArrayList<Song> songs;
    String songTitle;
    String artist;
    String imagePath;

    /**
     * Creates a new group with a title
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param songTitle the title of the SongGroup
     */
    public SongGroup(String songTitle) {
        this.songTitle = songTitle;
        this.songs = new ArrayList<Song>();
    }

    /**
     * Adds a song to this group
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param song the song to add
     */
    public void addSong(Song song) {
        this.imagePath = song.getBackgroundPath();
        this.songs.add(song);
        this.artist = song.getArtist();
    }

    /**
     * Sets the artist of this group
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param artist the artist name
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gets the songs belonging to this group
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the songs
     */
    public ArrayList<Song> getSongs() {
        return this.songs;
    }

    /**
     * Gets the title of this group
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the title
     */
    public String getTitle() {
        return this.songTitle;
    }

    /**
     * Gets the artist of this group
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the artist
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Gets the image path of this group
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @return the image path
     */
    public String getImagePath() {
        return this.imagePath;
    }
}
