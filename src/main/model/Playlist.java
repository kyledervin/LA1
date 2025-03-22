/*
 * File: Playlist.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Represents a playlist containing a collection of songs, allowing for song addition, removal, and display.
 * Course: CSC 335 Spring 2025
 */

package model;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }    

    public String getName() {
        return name;
    }

    public ArrayList<Song> getSongs() {
        return new ArrayList<>(songs);
    }
    
    public void addSong(Song s) {
        songs.add(s);
    }

    public void removeSong(Song s) {
        songs.remove(s);
    }

    public void displayPlaylist() {
    	System.out.println("Playlist: " + name);
        for (Song song : songs) {
            System.out.println("   " + song.getTitle() + " by " + song.getArtist());
        }
    }
    
    /*
     * Shuffles the songs in this playlist. Using approach described in Piazza.
     * 
     * Returns: none
     */
    public void shuffle() {
        Collections.shuffle(songs);
    }
}