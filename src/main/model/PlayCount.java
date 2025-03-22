/*
 * File: PlayCount.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Represents a play count for a particular song, storing the song and 
 * how many times it has been played.
 * Course: CSC 335 Spring 2025
 */

package model;

public class PlayCount {
    private Song song;
    private int count;

    public PlayCount(Song song, int count) {
        this.song = song;
        this.count = count;
    }

    public Song getSong() {
        return song;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    /*
     * Checks equality by comparing the underlying song.
     * 
     * Parameters: obj (type: Object) -- another object to compare
     * 
     * Returns: true if its the same song or false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PlayCount other = (PlayCount) obj;
        return song.equals(other.song);
    }
}