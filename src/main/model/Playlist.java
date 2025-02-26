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

    public String displayPlaylist() {
        // Build a string describing the playlist contents
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist: ").append(name).append("\n");
        if (songs.isEmpty()) {
            sb.append("   (Empty)\n");
        } else {
            for (int i = 0; i < songs.size(); i++) {
                sb.append((i + 1)).append(". ").append(songs.get(i)).append("\n");
            }
        }
        return sb.toString();
    }

}