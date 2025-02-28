/*
 * File: LibraryView.java
 * Authors: Jaden Gee + Kyle Dervin (AI GENERATED CODE!!!)
 * Purpose: A simple user interface and its only purpose is to interact
 * with the user and communicate with the model. It simply gets user requests and gets 
 * information from the model based on those requests.
 * Course: CSC 335 Spring 2025
 */

 package view;

 import model.*;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.ArrayList;
 
 
 import java.util.List;
 import java.util.Scanner;
 
 import database.MusicStore;
 
 public class LibraryView {
     public static void main(String[] args) {
 
         // 1) Create and load the MusicStore
         MusicStore store = new MusicStore();
         // Adjust this path to where your albums.txt and album files are located
         String musicDir = "albums";
         store.loadMusicStore(musicDir);
 
         // 2) Create the Model (user’s library)
         LibraryModel library = new LibraryModel(store);
 
         // 3) Text-based UI loop
         Scanner scanner = new Scanner(System.in);
         boolean running = true;
 
         while (running) {
             printMenu();
             System.out.print("Enter command: ");
             String cmd = scanner.nextLine().trim().toLowerCase();
 
             switch (cmd) {
                 case "1":
                     searchInStore(scanner, store);
                     break;
                 case "2":
                     searchInLibrary(scanner, library);
                     break;
                 case "3":
                     addToLibrary(scanner, library);
                     break;
                 case "4":
                     listLibraryItems(scanner, library);
                     break;
                 case "5":
                     createPlaylist(scanner, library);
                     break;
                 case "6":
                     modifyPlaylist(scanner, library);
                     break;
                 case "7":
                     toggleFavorite(scanner, library);
                     break;
                 case "8":
                     rateSong(scanner, library);
                     break;
                 case "q":
                 case "quit":
                 case "exit":
                     running = false;
                     System.out.println("Exiting...Goodbye!");
                     break;
                 default:
                     System.out.println("Unknown command, please try again.");
                     break;
             }
         }
 
         scanner.close();
     }
 
     private static void printMenu() {
         System.out.println("\n--- MUSIC LIBRARY MENU ---");
         System.out.println("1) Search in music store");
         System.out.println("2) Search in your library");
         System.out.println("3) Add to library");
         System.out.println("4) List items in library");
         System.out.println("5) Create a playlist");
         System.out.println("6) Add/remove songs from a playlist");
         System.out.println("7) Favorite/unfavorite a song");
         System.out.println("8) Rate a song");
         System.out.println("Q) Quit");
     }
 
     // -----------------------------------------------------------------------
     // 1) Search in music store
     // -----------------------------------------------------------------------
     private static void searchInStore(Scanner scanner, MusicStore store) {
         System.out.println("Search in store by:");
         System.out.println("   1) Song Title");
         System.out.println("   2) Song Artist");
         System.out.println("   3) Album Title");
         System.out.println("   4) Album Artist");
         String choice = scanner.nextLine().trim();
 
         System.out.print("Enter search term: ");
         String term = scanner.nextLine().trim();
 
         switch (choice) {
             case "1":
                 List<Song> songsByTitle = store.searchSongByTitle(term);
                 if (songsByTitle.isEmpty()) {
                     System.out.println("No songs found.");
                 } else {
                     for (Song s : songsByTitle) {
                         System.out.println(s);
                     }
                 }
                 break;
             case "2":
                 List<Song> songsByArtist = store.searchSongByArtist(term);
                 if (songsByArtist.isEmpty()) {
                     System.out.println("No songs found.");
                 } else {
                     for (Song s : songsByArtist) {
                         System.out.println(s);
                     }
                 }
                 break;
             case "3":
                 List<Album> albumsByTitle = store.searchAlbumByTitle(term);
                 if (albumsByTitle.isEmpty()) {
                     System.out.println("No albums found.");
                 } else {
                     for (Album a : albumsByTitle) {
                         System.out.println(a);
                         // Print songs in that album
                         for (Song s : a.getSongs()) {
                             System.out.println("   " + s.getTitle());
                         }
                     }
                 }
                 break;
             case "4":
                 List<Album> albumsByArtist = store.searchAlbumByArtist(term);
                 if (albumsByArtist.isEmpty()) {
                     System.out.println("No albums found.");
                 } else {
                     for (Album a : albumsByArtist) {
                         System.out.println(a);
                         // Print songs
                         for (Song s : a.getSongs()) {
                             System.out.println("   " + s.getTitle());
                         }
                     }
                 }
                 break;
             default:
                 System.out.println("Invalid choice.");
                 break;
         }
     }
 
     // -----------------------------------------------------------------------
     // 2) Search in library
     // -----------------------------------------------------------------------
     private static void searchInLibrary(Scanner scanner, LibraryModel library) {
         System.out.println("Search in library by:");
         System.out.println("   1) Song Title");
         System.out.println("   2) Song Artist");
         System.out.println("   3) Album Title");
         System.out.println("   4) Album Artist");
         System.out.println("   5) Playlist name");
         String choice = scanner.nextLine().trim();
 
         System.out.print("Enter search term: ");
         String term = scanner.nextLine().trim();
 
         switch (choice) {
             case "1":
                 List<Song> songsByTitle = library.searchSongByTitleInLibrary(term);
                 if (songsByTitle.isEmpty()) {
                     System.out.println("No songs found in library.");
                 } else {
                     for (Song s : songsByTitle) {
                         System.out.println(s);
                     }
                 }
                 break;
             case "2":
                 List<Song> songsByArtist = library.searchSongByArtistInLibrary(term);
                 if (songsByArtist.isEmpty()) {
                     System.out.println("No songs found in library.");
                 } else {
                     for (Song s : songsByArtist) {
                         System.out.println(s);
                     }
                 }
                 break;
             case "3":
                 List<Album> albumsByTitle = library.searchAlbumByTitleInLibrary(term);
                 if (albumsByTitle.isEmpty()) {
                     System.out.println("No albums found in library.");
                 } else {
                     for (Album a : albumsByTitle) {
                         System.out.println(a);
                         for (Song s : a.getSongs()) {
                             System.out.println("   " + s.getTitle());
                         }
                     }
                 }
                 break;
             case "4":
                 List<Album> albumsByArtist = library.searchAlbumByArtistInLibrary(term);
                 if (albumsByArtist.isEmpty()) {
                     System.out.println("No albums found in library.");
                 } else {
                     for (Album a : albumsByArtist) {
                         System.out.println(a);
                         for (Song s : a.getSongs()) {
                             System.out.println("   " + s.getTitle());
                         }
                     }
                 }
                 break;
             case "5":
                 // playlist name
                 Playlist playlist = library.searchPlaylistByName(term);
                 if (playlist == null) {
                     System.out.println("No playlist found with that name.");
                 } else {
                     System.out.println("Playlist: " + playlist.getName());
                     for (Song s : playlist.getSongs()) {
                         System.out.println("   " + s);
                     }
                 }
                 break;
             default:
                 System.out.println("Invalid choice.");
                 break;
         }
     }
 
     // -----------------------------------------------------------------------
     // 3) Add to library
     // -----------------------------------------------------------------------
     private static void addToLibrary(Scanner scanner, LibraryModel library) {
         System.out.println("Add to library:");
         System.out.println("   1) A single song");
         System.out.println("   2) A whole album");
         String choice = scanner.nextLine().trim();
 
         switch (choice) {
             case "1":
                 System.out.print("Enter song title: ");
                 String songTitle = scanner.nextLine().trim();
                 System.out.print("Enter song artist: ");
                 String songArtist = scanner.nextLine().trim();
                 boolean addedSong = library.addSongToLibrary(songTitle, songArtist);
                 if (addedSong) {
                     System.out.println("Song added to library!");
                 } else {
                     System.out.println("Song not found in store or could not be added.");
                 }
                 break;
             case "2":
                 System.out.print("Enter album title: ");
                 String albumTitle = scanner.nextLine().trim();
                 System.out.print("Enter album artist: ");
                 String albumArtist = scanner.nextLine().trim();
                 boolean addedAlbum = library.addAlbumToLibrary(albumTitle, albumArtist);
                 if (addedAlbum) {
                     System.out.println("Album added to library!");
                 } else {
                     System.out.println("Album not found in store or could not be added.");
                 }
                 break;
             default:
                 System.out.println("Invalid choice.");
                 break;
         }
     }
 
     // -----------------------------------------------------------------------
     // 4) List items in library
     // -----------------------------------------------------------------------
     private static void listLibraryItems(Scanner scanner, LibraryModel library) {
         System.out.println("List library items:");
         System.out.println("   1) Songs");
         System.out.println("   2) Artists");
         System.out.println("   3) Albums");
         System.out.println("   4) Playlists");
         System.out.println("   5) Favorite songs");
         String choice = scanner.nextLine().trim();
 
         switch (choice) {
             case "1":
                 for (Song s : library.getAllUserSongs()) {
                     System.out.println(s);
                 }
                 break;
             case "2":
                 for (String artist : library.getAllUserArtists()) {
                     System.out.println(artist);
                 }
                 break;
             case "3":
                 for (Album a : library.getAllUserAlbums()) {
                     System.out.println(a);
                 }
                 break;
             case "4": {
                 ArrayList<String> playlistNames = library.getAllPlaylists();
                 if (playlistNames.isEmpty()) {
                     System.out.println("No playlists in library yet!");
                 } else {
                     System.out.println("Playlists in your library:");
                     for (String plName : playlistNames) {
                         System.out.println("Playlist: " + plName);
                         Playlist p = library.searchPlaylistByName(plName);
                         if (p == null) {
                             // In case the playlist disappeared or wasn't found
                             System.out.println("   (Error: playlist not found)");
                             continue;
                         }
 
                         ArrayList<Song> songs = p.getSongs();
                         if (songs.isEmpty()) {
                             System.out.println("   (Empty playlist)");
                         } else {
                             // For each song, we’ll print Title, Artist, plus rating/favorite if applicable
                             for (Song s : songs) {
                                 // Build rating/favorite info
                                 int rating = s.getRating();
                                 boolean isFav = s.isFavorite();
 
                                 // Only show rating text if > 0
                                 String ratingInfo = (rating > 0) ? " [rating: " + rating + "]" : "";
                                 // Show "[favorite]" if isFav is true
                                 String favInfo = isFav ? " *favorite*" : "";
 
                                 // Print the line
                                 System.out.println("   - " + s.getTitle() 
                                     + " by " + s.getArtist() 
                                     + ratingInfo + favInfo);
                             }
                         }
                     }
                 }
                 break;
             }
 
 
             case "5":
                 for (Song fav : library.getFavoriteSongs()) {
                     System.out.println(fav);
                 }
                 break;
             default:
                 System.out.println("Invalid choice.");
                 break;
         }
     }
 
     // -----------------------------------------------------------------------
     // 5) Create a playlist
     // -----------------------------------------------------------------------
     private static void createPlaylist(Scanner scanner, LibraryModel library) {
         System.out.print("Enter new playlist name: ");
         String name = scanner.nextLine().trim();
         boolean created = library.createPlaylist(name);
         if (created) {
             System.out.println("Playlist '" + name + "' created.");
         } else {
             System.out.println("Playlist name already in use, or could not create.");
         }
     }
 
     // -----------------------------------------------------------------------
     // 6) Modify a playlist
     // -----------------------------------------------------------------------
     private static void modifyPlaylist(Scanner scanner, LibraryModel library) {
         // Prompt for playlist name
         System.out.print("Enter playlist name: ");
         String playlistName = scanner.nextLine().trim();
         
         // Check if the playlist actually exists
         Playlist p = library.searchPlaylistByName(playlistName);
         if (p == null) {
             System.out.println("Playlist \"" + playlistName + "\" does not exist!");
             return;  // Stop here because there's nothing to modify
         }
 
         // If it does exist, show menu to add or remove a song
         System.out.println("   1) Add a song");
         System.out.println("   2) Remove a song");
         System.out.print("Enter choice: ");
         String choice = scanner.nextLine().trim();
 
         switch (choice) {
             case "1":
                 System.out.print("Song title: ");
                 String titleAdd = scanner.nextLine().trim();
                 System.out.print("Song artist: ");
                 String artistAdd = scanner.nextLine().trim();
                 boolean added = library.addSongToPlaylist(playlistName, titleAdd, artistAdd);
                 if (added) {
                     System.out.println("Song added to playlist!");
                 } else {
                     System.out.println("Could not add song. Make sure it's in your library and that the playlist exists.");
                 }
                 break;
 
             case "2":
                 System.out.print("Song title: ");
                 String titleRemove = scanner.nextLine().trim();
                 System.out.print("Song artist: ");
                 String artistRemove = scanner.nextLine().trim();
                 boolean removed = library.removeSongFromPlaylist(playlistName, titleRemove, artistRemove);
                 if (removed) {
                     System.out.println("Song removed from playlist!");
                 } else {
                     System.out.println("Could not remove song. Check the song or playlist name.");
                 }
                 break;
 
             default:
                 System.out.println("Invalid choice.");
                 break;
         }
     }
 
 
     // -----------------------------------------------------------------------
     // 7) Mark favorite
     // -----------------------------------------------------------------------
     private static void toggleFavorite(Scanner scanner, LibraryModel library) {
         System.out.print("Song title: ");
         String title = scanner.nextLine().trim();
         System.out.print("Artist: ");
         String artist = scanner.nextLine().trim();
         
         boolean updated = library.toggleFavoriteSong(title, artist);
         if (updated) {
             System.out.println("Song favorite status updated!");
         } else {
             System.out.println("Song not found in your library.");
         }
     }
     
 
     // -----------------------------------------------------------------------
     // 8) Rate a song
     // -----------------------------------------------------------------------
     private static void rateSong(Scanner scanner, LibraryModel library) {
         System.out.print("Song title: ");
         String title = scanner.nextLine().trim();
         System.out.print("Artist: ");
         String artist = scanner.nextLine().trim();
     
         int rating = 0;
         while (true) {
             System.out.print("Rating (1-5): ");
             String ratingStr = scanner.nextLine().trim();
             try {
                 rating = Integer.parseInt(ratingStr);
                 if (rating >= 1 && rating <= 5) {
                     break; // Valid rating, exit loop
                 } else {
                     System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                 }
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. Please enter a number between 1 and 5.");
             }
         }
     
         boolean ok = library.rateSong(title, artist, rating);
         if (ok) {
             System.out.println("Song rated!");
         } else {
             System.out.println("Song not found in your library.");
         }
     }
 }