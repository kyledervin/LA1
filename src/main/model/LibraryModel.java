/*
 * File: LibraryModel.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Models a user's music library, allowing the storage, searching, and management 
 * of songs, albums, and playlists. Supports interactions with a MusicStore and provides 
 * playlist, favorite, and rating functionalities.
 * Course: CSC 335 Spring 2025
 */

 package model;
 import database.*;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Queue;
 
 public class LibraryModel {
     private MusicStore musicStore;
     private Map<String, User> users; // Map of all users
     private User currentUser; // Currently logged-in user
 
     public LibraryModel(MusicStore store) {
         this.musicStore = store;
         this.users = new HashMap<>();
     }
 
     /**
      * Creates a new user account.
      */
     public boolean createUser(String username, String password) {
         if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
             return false; // prevent empty or null values
         }
         
         if (users.containsKey(username)) {
             return false; // username already exists
         }
         
         users.put(username, new User(username, password));
         return true;
     }
 
 
     /**
      * Logs in a user.
      */
     public boolean login(String username, String password) {
         if (currentUser != null) {
             return false; // a user is already logged in
         }
 
         User user = users.get(username);
         if (user != null && user.checkPassword(password)) {
             currentUser = user;
             return true;
         }
         return false;
     }
 
     public void logout() {
         currentUser = null; // clear the loggedin user
     }
 
     public boolean isUserLoggedIn() {
         return currentUser != null;
     }
 
     public User getCurrentUser() {
         return currentUser;
     }
 
     /**
      * Adds a song to the current user's library.
      */
     public boolean addSongToLibrary(String songTitle, String artist) {
         if (currentUser == null) {
             return false; // No user is logged in
         }
 
      // Check if the song already exists in the user's library
         for (Song song : currentUser.getUserSongs()) {
             if (song.getTitle().equalsIgnoreCase(songTitle) && song.getArtist().equalsIgnoreCase(artist)) {
                 return false; // Song already exists in the library
             }
         }
 
         // Search for the song in the music store
         List<Song> matchingSongs = musicStore.searchSongByTitle(songTitle);
         for (Song song : matchingSongs) {
             if (song.getArtist().equalsIgnoreCase(artist)) {
                 currentUser.addSong(song); // Add the song to the library
                 
                 // Get the album for the song
                 Album album = musicStore.getAlbumForSong(song);
                 if (album != null) {
                     // Check if the album is already in the library
                     boolean albumExists = false;
                     Album userAlbum = null;
                     for (Album existingAlbum : currentUser.getUserAlbums()) {
                         if (existingAlbum.getTitle().equalsIgnoreCase(album.getTitle()) &&
                             existingAlbum.getArtist().equalsIgnoreCase(album.getArtist())) {
                             albumExists = true;
                             userAlbum = existingAlbum;
                             break;
                         }
                     }
 
                     if (!albumExists) {
                         // Create a new album with only the added song
                         ArrayList<Song> albumSongs = new ArrayList<>();
                         albumSongs.add(song); // Add only the explicitly added song
                         userAlbum = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear(), albumSongs);
                         currentUser.addAlbum(userAlbum);
                     } else {
                         // Add the song to the existing album in the library
                         ArrayList<Song> albumSongs = new ArrayList<>(userAlbum.getSongs());
                         albumSongs.add(song); // Add only the explicitly added song
                         userAlbum = new Album(userAlbum.getTitle(), userAlbum.getArtist(), userAlbum.getGenre(), userAlbum.getYear(), albumSongs);
                         // Replace the existing album with the updated one
                         currentUser.getUserAlbums().remove(userAlbum);
                         currentUser.addAlbum(userAlbum);
                     }
                 }
 
                 return true;
             }
         }
         return false; // Song not found in the store
     }
 
     /**
      * Adds an album to the current user's library.
      */
     public boolean addAlbumToLibrary(String albumTitle, String artist) {
         if (currentUser == null) {
             return false; // No user is logged in
         }
 
         List<Album> matchingAlbums = musicStore.searchAlbumByTitle(albumTitle);
         for (Album album : matchingAlbums) {
             if (album.getArtist().equalsIgnoreCase(artist)) {
                 Album existingAlbum = null;
 
                 // Check if the album is already in the library
                 for (Album userAlbum : currentUser.getUserAlbums()) {
                     if (userAlbum.getTitle().equalsIgnoreCase(albumTitle) &&
                         userAlbum.getArtist().equalsIgnoreCase(artist)) {
                         existingAlbum = userAlbum;
                         break;
                     }
                 }
 
                 if (existingAlbum != null) {
                     // The album exists; update it with any missing songs
                     List<Song> updatedSongs = new ArrayList<>(existingAlbum.getSongs());
 
                     for (Song song : album.getSongs()) {
                         if (!updatedSongs.contains(song)) {
                             updatedSongs.add(song);
                         }
                     }
 
                     // Create a new Album object with updated songs and replace it in the library
                     Album updatedAlbum = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear(), updatedSongs);
                     currentUser.getUserAlbums().remove(existingAlbum);
                     currentUser.addAlbum(updatedAlbum);
                 } else {
                     currentUser.addAlbum(album);
                 }
 
                 return true;
             }
         }
         return false; // Album not found in the store
     }
 
     /**
      * Simulates playing a song and updates play counts and recently played list.
      */
     public String playSong(String songTitle, String artist) {
         if (currentUser == null) {
             return "No user is logged in.";
         }
 
         Song song = findSongInLibrary(songTitle, artist);
         if (song == null) {
             return "Song not found in your library.";
         }
 
         currentUser.incrementPlayCount(song);
 
         // Update recently played list
         Queue<Song> recentlyPlayed = currentUser.getRecentlyPlayed();
         if (recentlyPlayed.size() == 10) {
             recentlyPlayed.poll(); // Remove the oldest song
         }
         recentlyPlayed.offer(song);
 
         return "Now playing: " + song.getTitle() + " by " + song.getArtist();
     }
 
 
     /**
      * Gets the 10 most recently played songs.
      */
     public List<Song> getRecentlyPlayedSongs() {
         if (currentUser == null) {
             return new ArrayList<>(); // No user is logged in
         }
 
         // Reverse the order of the recently played songs
         List<Song> recentlyPlayed = new ArrayList<>(currentUser.getRecentlyPlayed());
         Collections.reverse(recentlyPlayed);
         return recentlyPlayed;
     }
 
     /**
      * Gets the 10 most frequently played songs.
      */
     public List<PlayCount> getMostFrequentlyPlayedSongs() {
         if (currentUser == null) {
             return new ArrayList<>(); // No user is logged in
         }
 
         List<PlayCount> playCounts = currentUser.getPlayCounts();
         playCounts.sort((pc1, pc2) -> pc2.getCount() - pc1.getCount()); // Sort by play count (descending)
 
         // Return the top 10 most frequently played songs
         return playCounts.subList(0, Math.min(10, playCounts.size()));
     }
 
     /**
      * Sorts the user's songs by title, artist, or rating.
      */
     public List<Song> sortSongs(String sortBy) {
         if (currentUser == null) {
             return new ArrayList<>(); // No user is logged in
         }
 
         List<Song> sortedSongs = new ArrayList<>(currentUser.getUserSongs());
         switch (sortBy.toLowerCase()) {
             case "title":
                 sortedSongs.sort(Comparator.comparing(Song::getTitle));
                 break;
             case "artist":
                 sortedSongs.sort(Comparator.comparing(Song::getArtist));
                 break;
             case "rating":
                 sortedSongs.sort(Comparator.comparingInt(Song::getRating)); // Ascending order
                 break;
             default:
                 throw new IllegalArgumentException("Invalid sort field: " + sortBy);
         }
         return sortedSongs;
     }
 
     /**
      * Searches for songs by genre.
      */
     public List<Song> searchSongsByGenre(String genre) {
         if (currentUser == null) {
             return new ArrayList<>(); // No user is logged in
         }
 
         List<Song> matches = new ArrayList<>();
         for (Song song : currentUser.getUserSongs()) {
             if (song.getGenre().equalsIgnoreCase(genre)) {
                 matches.add(song);
             }
         }
         return matches;
     }
 
 
     /**
      * Gets a playlist of favorite songs (rated 5 or marked as favorite).
      */
     public List<Song> getFavoriteSongsPlaylist() {
         if (currentUser == null) {
             return new ArrayList<>(); // No user is logged in
         }
 
         List<Song> favorites = new ArrayList<>();
         for (Song song : currentUser.getUserSongs()) {
             if (song.isFavorite() || song.getRating() == 5) {
                 favorites.add(song);
             }
         }
         return favorites;
     }
 
     /**
      * Gets a playlist of top-rated songs (rated 4 or 5).
      */
     public List<Song> getTopRatedSongsPlaylist() {
         if (currentUser == null) {
             return new ArrayList<>(); // No user is logged in
         }
 
         List<Song> topRated = new ArrayList<>();
         for (Song song : currentUser.getUserSongs()) {
             if (song.getRating() >= 4) {
                 topRated.add(song);
             }
         }
         return topRated;
     }
 
     /**
      * Gets a playlist of songs for a specific genre if there are at least 10 songs.
      */
     public List<Song> getGenrePlaylist(String genre) {
         if (currentUser == null) {
             return null; // No user is logged in
         }
 
         List<Song> genreSongs = searchSongsByGenre(genre);
         if (genreSongs.size() >= 10) {
             return genreSongs;
         }
         return null;
     }
 
     /**
      * Finds a song in the user's library by title and artist.
      */
     private Song findSongInLibrary(String title, String artist) {
         if (currentUser == null) {
             return null; // No user is logged in
         }
 
         for (Song song : currentUser.getUserSongs()) {
             if (song.getTitle().equalsIgnoreCase(title) && song.getArtist().equalsIgnoreCase(artist)) {
                 return song;
             }
         }
         return null;
     }
    }