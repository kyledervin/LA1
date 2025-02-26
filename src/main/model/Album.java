/*
 * File: Album.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Represents a music album with title, artist, genre, year, and a list of songs.
 * Course: CSC 335 Spring 2025
 */


package model;

import java.util.ArrayList;

public class Album {
	private String title;
    private String artist;
    private String genre;
    private String year;
    private ArrayList<Song> songs; // maintain the order from the file

    public Album(String title, String artist, String genre, String year, ArrayList<Song> songs) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        this.songs = songs;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }

    public ArrayList<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    @Override
    public String toString() {
        return title + " (" + year + ") by " + artist + " [" + genre + "]";
    }

}