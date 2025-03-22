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
    /*
     * Hashes a password using a simple hash.
     * 
     * Parameters: password (type: String) -- the plaintext password
     * 
     * Returns: the hashed password
     */
    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    /*
     * Checks if a given password matches this user's stored and hashed password.
     * 
     * Parameters: password (type: String) -- the plaintext password
     * 
     * Returns: true if match or false otherwise
     */
    public boolean checkPassword(String password) {
        return this.password.equals(hashPassword(password));
    }

    /*
     * Adds a song to this user's library if not already present.
     * 
     * Parameters: song (type: Song) -- the song to add
     * 
     * Returns: none
     */
    public void addSong(Song song) {
        if (!userSongs.contains(song)) {
            userSongs.add(song);
        }
    }

    /*
     * Adds an album to this user's library if its not already present. Also adds the album's songs.
     * 
     * Parameters: album (type: Album) -- the album to add
     * 
     * Returns: none
     */
    public void addAlbum(Album album) {
        if (!userAlbums.contains(album)) {
            userAlbums.add(album);
            for (Song song : album.getSongs()) {
                addSong(song);
            }
        }
    }

    /*
     * Creates a new playlist if one with the same name does not already exist.
     * 
     * Parameters: name (type: String) -- the playlist name
     * 
     * Returns: true if created or false otherwise
     */
    public boolean createPlaylist(String name) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        playlists.add(new Playlist(name));
        return true;
    }

    /*
     * Increments the play count for a given song.
     * 
     * Parameters: song (type: Song) -- the song played
     * 
     * Returns: none
     */
    public void incrementPlayCount(Song song) {
        for (PlayCount pc : playCounts) {
            if (pc.getSong().equals(song)) {
                pc.incrementCount();
                return;
            }
        }
        playCounts.add(new PlayCount(song, 1));
    }

    /*
     * Retrieves how many times a given song has been played.
     * 
     * Parameters: song (type: Song) -- the song
     * 
     * Returns: int play count
     */
    public int getPlayCount(Song song) {
        for (PlayCount pc : playCounts) {
            if (pc.getSong().equals(song)) {
                return pc.getCount();
            }
        }
        return 0;
    }
}