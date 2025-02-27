/*
 * File: LibraryModel.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Models a user's music library, allowing for storage, searching, and management 
 * of songs, albums, and playlists. Supports interactions with MusicStore and provides functionality
 * for playlists, favoriting, and rating.
 * Course: CSC 335 Spring 2025
 */

 package model;

 import database.*;
 import java.util.ArrayList;
 
 public class LibraryModel {
     private MusicStore musicStore;
     private ArrayList<Song> userSongs; // songs the user has added
     private ArrayList<Album> userAlbums; // albums the user has added
     private ArrayList<Playlist> playlists; // playlists the user has created
 
     public LibraryModel(MusicStore store) {
         this.musicStore = store;
         this.userSongs = new ArrayList<>();
         this.userAlbums = new ArrayList<>();
         this.playlists = new ArrayList<>();
     }

    /*
     * Helper method for searching albums in the user library.
     * 
     * Parameters: query (type: String) -- substring to search for within album titles or artists,
     * searchByTitle (type: boolean) -- if true, searches by title otherwise, searches by artist
     * 
     * Returns: matches (type: ArrayList<Album>) -- a list of album objects matching the search criteria
     */
    private ArrayList<Album> searchAlbumsInLibrary(String query, boolean searchByTitle) {
        ArrayList<Album> matches = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Album album : userAlbums) {
            String fieldToCheck = searchByTitle ? album.getTitle() : album.getArtist();
            if (fieldToCheck.toLowerCase().contains(lowerQuery)) {
                matches.add(album);
            }
        }
        return matches;
    }

    /*
     * Searches for an album by title in the user's library.
     * 
     * Parameters: title (type: String) -- the title of the album to search for
     * 
     * Returns: ArrayList<Album> -- a list of matching albums
     */
    public ArrayList<Album> searchAlbumByTitleInLibrary(String title) {
        return searchAlbumsInLibrary(title, true);
    }

    /*
     * Searches for an album by artist in the user's library.
     * 
     * Parameters: artist (type: String) -- the artist of the album to search for
     * 
     * Returns: ArrayList<Album> -- a list of matching albums
     */
    public ArrayList<Album> searchAlbumByArtistInLibrary(String artist) {
        return searchAlbumsInLibrary(artist, false);
    }

    /*
     * Helper method for searching songs in the user library.
     * 
     * Parameters: query (type: String) -- substring to search for within song titles or artists,
     * searchByTitle (type: boolean) -- if true, searches by title otherwise, searches by artist
     * 
     * Returns: matches (type: ArrayList<Song>) -- a list of song objects matching the search criteria
     */
    private ArrayList<Song> searchSongsInLibrary(String query, boolean searchByTitle) {
        ArrayList<Song> matches = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Song song : userSongs) {
            String fieldToCheck = searchByTitle ? song.getTitle() : song.getArtist();
            if (fieldToCheck.toLowerCase().contains(lowerQuery)) {
                matches.add(song);
            }
        }
        return matches;
    }

    /*
     * Searches for a song by title in the user's library.
     * 
     * Parameters: title (type: String) -- the title of the song to search for
     * 
     * Returns: ArrayList<Song> -- a list of matching songs
     */
    public ArrayList<Song> searchSongByTitleInLibrary(String title) {
        return searchSongsInLibrary(title, true);
    }

    /*
     * Searches for a song by artist in the user's library.
     * 
     * Parameters: artist (type: String) -- the artist of the song to search for
     * 
     * Returns: ArrayList<Song> -- a list of matching songs
     */
    public ArrayList<Song> searchSongByArtistInLibrary(String artist) {
        return searchSongsInLibrary(artist, false);
    }
 }