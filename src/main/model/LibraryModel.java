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

    /*
     * Searches for a playlist by name in the user's library.
     * 
     * Parameters: playlistName (type: String) -- the name of the playlist to search for
     * 
     * Returns: Playlist -- the playlist object if found, otherwise null
     */
    public Playlist searchPlaylistByName(String playlistName) {
        for (Playlist p : playlists) {
            if (p.getName().equalsIgnoreCase(playlistName)) {
                return p;
            }
        }
        return null;
    }

    /*
     * This method adds a song to the user's library if found in MusicStore.
     * 
     * Parameters: songTitle (type: String) -- title of the song to be added,
     * artist (type: String) -- artist of the song to be added
     * 
     * Returns: boolean -- true if the song was added, false otherwise
     */
    public boolean addSongToLibrary(String songTitle, String artist) {
        ArrayList<Song> matchingSongs = musicStore.searchSongByTitle(songTitle);
        for (Song s : matchingSongs) {
            if (s.getArtist().equalsIgnoreCase(artist)) {
                if (!userSongs.contains(s)) {
                    userSongs.add(s);
                }
                return true;
            }
        }
        return false;
    }

    /*
     * This method adds an entire album from the store to the user's library.
     * 
     * Parameters: albumTitle (type: String) -- title of the album to be added,
     * artist (type: String) -- artist of the album to be added
     * 
     * Returns: boolean -- true if the album was added, false otherwise
     */
    public boolean addAlbumToLibrary(String albumTitle, String artist) {
        ArrayList<Album> matchingAlbums = musicStore.searchAlbumByTitle(albumTitle);
        for (Album a : matchingAlbums) {
            if (a.getArtist().equalsIgnoreCase(artist)) {
                if (!userAlbums.contains(a)) {
                    userAlbums.add(a);
                }
                for (Song s : a.getSongs()) {
                    if (!userSongs.contains(s)) {
                        userSongs.add(s);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /*
     * Retrieves a copy of all songs in the user's library.
     * 
     * Returns: ArrayList<Song> -- a copy of the list of songs
     */
    public ArrayList<Song> getAllUserSongs() {
        return new ArrayList<>(userSongs);
    }

    /*
     * Retrieves a copy of all albums in the user's library.
     * 
     * Returns: ArrayList<Album> -- a copy of the list of albums
     */
    public ArrayList<Album> getAllUserAlbums() {
        return new ArrayList<>(userAlbums);
    }

    /*
     * Retrieves a list of all unique artists in the user's library.
     * 
     * Returns: ArrayList<String> -- a list of unique artist names
     */
    public ArrayList<String> getAllUserArtists() {
        ArrayList<String> artists = new ArrayList<>();
        for (Song s : userSongs) {
            String artist = s.getArtist();
            if (!artists.contains(artist)) {
                artists.add(artist);
            }
        }
        return artists;
    }

    /*
     * Retrieves a copy of all playlist names in the user's library.
     * 
     * Returns: ArrayList<String> -- a list of playlist names
     */
    public ArrayList<String> getAllPlaylists() {
        ArrayList<String> playlistNames = new ArrayList<>();
        for (Playlist p : playlists) {
            playlistNames.add(p.getName());
        }
        return playlistNames;
    }

    /*
     * Retrieves a copy of all favorite songs in the user's library.
     * 
     * Returns: ArrayList<Song> -- a list of favorite songs
     */
    public ArrayList<Song> getFavoriteSongs() {
        ArrayList<Song> favs = new ArrayList<>();
        for (Song s : userSongs) {
            if (s.isFavorite()) {
                favs.add(s);
            }
        }
        return favs;
    }

    /*
     * Creates a new playlist with the given name if it does not already exist.
     * 
     * Parameters: name (type: String) -- the name of the new playlist
     * 
     * Returns: boolean -- true if the playlist was created, false if it already exists
     */
    public boolean createPlaylist(String name) {
        if (searchPlaylistByName(name) != null) {
            return false;
        }
        playlists.add(new Playlist(name));
        return true;
    }

    /*
     * Adds a song to an existing playlist in the user's library.
     * 
     * Parameters: playlistName (type: String) -- name of the playlist,
     * songTitle (type: String) -- title of the song, artist (type: String) -- artist of the song
     * 
     * Returns: boolean -- true if the song was added, false otherwise
     */
    public boolean addSongToPlaylist(String playlistName, String songTitle, String artist) {
        Playlist playlist = searchPlaylistByName(playlistName);
        if (playlist == null) {
            return false;
        }
        for (Song s : userSongs) {
            if (s.getTitle().equalsIgnoreCase(songTitle) 
                && s.getArtist().equalsIgnoreCase(artist)) {
                playlist.addSong(s);
                return true;
            }
        }
        return false;
    }

    /*
     * Removes a song from an existing playlist in the user's library.
     * 
     * Parameters: playlistName (type: String) -- name of the playlist,
     * songTitle (type: String) -- title of the song, artist (type: String) -- artist of the song
     * 
     * Returns: boolean -- true if the song was removed, false otherwise
     */
    public boolean removeSongFromPlaylist(String playlistName, String songTitle, String artist) {
        Playlist playlist = searchPlaylistByName(playlistName);
        if (playlist == null) {
            return false;
        }
        Song toRemove = null;
        for (Song s : playlist.getSongs()) {
            if (s.getTitle().equalsIgnoreCase(songTitle)
                && s.getArtist().equalsIgnoreCase(artist)) {
                toRemove = s;
                break;
            }
        }
        if (toRemove != null) {
            playlist.removeSong(toRemove);
            return true;
        }
        return false;
    }

    /*
     * Displays the contents of a given playlist.
     * 
     * Parameters: playlistName (type: String) -- the name of the playlist
     */
    public void displayPlaylistContents(String playlistName) {
        Playlist playlist = searchPlaylistByName(playlistName);
        if (playlist == null) {
            System.out.println("Playlist not found.");
        } else {
            playlist.displayPlaylist();
        }
    }
 }