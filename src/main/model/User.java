/*
 * File: User.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Stores information about a single user inclduing their username, hashed password,
 * songs in their library, albums, playlists, and play history.
 * Course: CSC 335 Spring 2025
 */

package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class User {
    private String username;
    private String password; // hashed password
    private ArrayList<Song> userSongs;
    private ArrayList<Album> userAlbums;
    private ArrayList<Playlist> playlists;
    private Queue<Song> recentlyPlayed; // up to 10 recently played
    private ArrayList<PlayCount> playCounts;

   
    public User(String username, String password) {
        this.username = username;
        this.password = hashPassword(password);
        this.userSongs = new ArrayList<>();
        this.userAlbums = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.recentlyPlayed = new LinkedList<>();
        this.playCounts = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Song> getUserSongs() {
        return userSongs;
    }

    public void setUserSongs(ArrayList<Song> userSongs) {
        this.userSongs = userSongs;
    }

    public ArrayList<Album> getUserAlbums() {
        return userAlbums;
    }

    public void setUserAlbums(ArrayList<Album> userAlbums) {
        this.userAlbums = userAlbums;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Queue<Song> getRecentlyPlayed() {
        return recentlyPlayed;
    }

    public void setRecentlyPlayed(ArrayList<Song> recentlyPlayed) {
        this.recentlyPlayed = new LinkedList<>(recentlyPlayed);
    }

    public ArrayList<PlayCount> getPlayCounts() {
        return playCounts;
    }

    public void setPlayCounts(ArrayList<PlayCount> playCounts) {
        this.playCounts = playCounts;
    }
}