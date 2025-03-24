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
import java.util.Map;
import java.util.Scanner;


public class LibraryView {
    public static void main(String[] args) {
        // 1) Create and load the MusicStore
        MusicStore store = new MusicStore();
        String musicDir = "albums"; // Adjust this path to where your albums.txt and album files are located
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
                case "9":
                    playSong(scanner, library);
                    break;
                case "10":
                    viewRecentlyPlayedSongs(library);
                    break;
                case "11":
                    viewMostFrequentlyPlayedSongs(library);
                    break;
                case "12":
                    sortSongs(scanner, library);
                    break;
                case "13":
                    searchSongsByGenre(scanner, library);
                    break;
                case "14":
                    viewAutomaticPlaylists(library);
                    break;
                case "15":
                    createUser(scanner, library);
                    break;
                case "16":
                    loginUser(scanner, library);
                    break;
                case "17":
                	logoutUser(library);
                	break;
                case "18":
                	removeFromLibrary(scanner, library);
                	break;
                case "19":
                	shuffleSongs(scanner, library);
                	break;
                case "q":
                case "quit":
                case "exit":
                    running = false;
                    System.out.println("Exiting...");
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
        System.out.println("6) Add/remove/view songs from a playlist");
        System.out.println("7) Favorite/unfavorite a song");
        System.out.println("8) Rate a song");
        System.out.println("9) Play a song");
        System.out.println("10) View recently played songs");
        System.out.println("11) View most frequently played songs");
        System.out.println("12) Sort songs in library");
        System.out.println("13) Search songs by genre");
        System.out.println("14) View automatic playlists");
        System.out.println("15) Create a new user");
        System.out.println("16) Log in");
        System.out.println("17) Log out");
        System.out.println("18) Remove a song or album from the library");
        System.out.println("19) Shuffle songs in library or playlist");
        System.out.println("Q) Quit");
    }

    // -----------------------------------------------------------------------
    // 9) Play a song
    // -----------------------------------------------------------------------
    private static void playSong(Scanner scanner, LibraryModel library) {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter song artist: ");
        String artist = scanner.nextLine().trim();

        String message = library.playSong(title, artist);
        System.out.println(message);
    }


    // -----------------------------------------------------------------------
    // 10) View recently played songs
    // -----------------------------------------------------------------------
    private static void viewRecentlyPlayedSongs(LibraryModel library) {
        List<Song> recentlyPlayed = library.getRecentlyPlayedSongs();
        if (recentlyPlayed.isEmpty()) {
            System.out.println("No songs have been played yet.");
        } else {
            System.out.println("Recently played songs:");
            for (Song song : recentlyPlayed) {
                System.out.println(song);
            }
        }
    }

    // -----------------------------------------------------------------------
    // 11) View most frequently played songs
    // -----------------------------------------------------------------------
    private static void viewMostFrequentlyPlayedSongs(LibraryModel library) {
    	List<PlayCount> mostFrequent = library.getMostFrequentlyPlayedSongs();
        if (mostFrequent.isEmpty()) {
            System.out.println("No songs have been played yet.");
        } else {
            System.out.println("Most frequently played songs:");
            for (PlayCount pc : mostFrequent) {
                Song song = pc.getSong();
                int playCount = pc.getCount();
                System.out.println(song + " (Played " + playCount + " times)");
            }
        }
    }

    // -----------------------------------------------------------------------
    // 12) Sort songs in library
    // -----------------------------------------------------------------------
    private static void sortSongs(Scanner scanner, LibraryModel library) {
        System.out.println("Sort songs by:");
        System.out.println("   1) Title");
        System.out.println("   2) Artist");
        System.out.println("   3) Rating");
        String choice = scanner.nextLine().trim();

        String sortBy;
        switch (choice) {
            case "1":
                sortBy = "title";
                break;
            case "2":
                sortBy = "artist";
                break;
            case "3":
                sortBy = "rating";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        List<Song> sortedSongs = library.sortSongs(sortBy);
        System.out.println("Sorted songs:");
        for (Song song : sortedSongs) {
            System.out.println(song);
        }
    }

    // -----------------------------------------------------------------------
    // 13) Search songs by genre
    // -----------------------------------------------------------------------
    private static void searchSongsByGenre(Scanner scanner, LibraryModel library) {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine().trim();

        List<Song> genreSongs = library.searchSongsByGenre(genre);
        if (genreSongs.isEmpty()) {
            System.out.println("No songs found in this genre.");
        } else {
            System.out.println("Songs in genre '" + genre + "':");
            for (Song song : genreSongs) {
                System.out.println(song);
            }
        }
    }

    // -----------------------------------------------------------------------
    // 14) View automatic playlists
    // -----------------------------------------------------------------------
    private static void viewAutomaticPlaylists(LibraryModel library) {
        System.out.println("Automatic playlists:");
        System.out.println("1) Favorite Songs");
        System.out.println("2) Top Rated Songs");
        System.out.println("3) Genre Playlists");
        System.out.print("Enter choice: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                List<Song> favorites = library.getFavoriteSongsPlaylist();
                System.out.println("Favorite Songs:");
                for (Song song : favorites) {
                    System.out.println(song);
                }
                break;
            case "2":
                List<Song> topRated = library.getTopRatedSongsPlaylist();
                System.out.println("Top Rated Songs:");
                for (Song song : topRated) {
                    System.out.println(song);
                }
                break;
            case "3":
                System.out.print("Enter genre: ");
                String genre = scanner.nextLine().trim();
                List<Song> genrePlaylist = library.getGenrePlaylist(genre);
                if (genrePlaylist == null) {
                    System.out.println("Not enough songs in this genre to create a playlist.");
                } else {
                    System.out.println("Genre Playlist for '" + genre + "':");
                    for (Song song : genrePlaylist) {
                        System.out.println(song);
                    }
                }
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    // -----------------------------------------------------------------------
    // 15) Create a new user
    // -----------------------------------------------------------------------
    private static void createUser(Scanner scanner, LibraryModel library) {
        String username, password;
        
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine().trim();

            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Error: Username and password cannot be empty. Please try again.");
            } else {
                break; // Valid input, exit loop
            }
        }

        boolean created = library.createUser(username, password);
        if (created) {
            System.out.println("User created successfully!");
        } else {
            System.out.println("Error: Username already exists.");
        }
    }


    // -----------------------------------------------------------------------
    // 16) Log in
    // -----------------------------------------------------------------------
    private static void loginUser(Scanner scanner, LibraryModel library) {
        if (library.isUserLoggedIn()) {
            System.out.println("Error: Another user is already logged in. Please log out first.");
            return;
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        boolean loggedIn = library.login(username, password);
        if (loggedIn) {
            System.out.println("Login successful! Welcome, " + username + ".");
        } else {
            System.out.println("Error: Invalid username or password, or another user is already logged in.");
        }
    }

    private static void logoutUser(LibraryModel library) {
        if (!library.isUserLoggedIn()) {
            System.out.println("Error: No user is currently logged in.");
            return;
        }

        library.logout();
        System.out.println("User logged out successfully.");
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
    @SuppressWarnings("unchecked")
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
                    System.out.println("Matching songs:");
                    for (Song s : songsByTitle) {
                        System.out.println("- " + s);
                    }

                    // Prompt for album information
                    System.out.print("Do you want to see album information for a song? (yes/no): ");
                    String response = scanner.nextLine().trim().toLowerCase();
                    if (response.equals("yes")) {
                        System.out.print("Enter song title: ");
                        String songTitle = scanner.nextLine().trim();
                        System.out.print("Enter song artist: ");
                        String songArtist = scanner.nextLine().trim();

                        Map<String, Object> albumInfo = library.getAlbumInfoForSong(songTitle, songArtist);
                        Album album = (Album) albumInfo.get("album");
                        boolean isAlbumInLibrary = (boolean) albumInfo.get("isAlbumInLibrary");
                        List<String> addedSongs = (List<String>) albumInfo.get("addedSongs");
                        List<String> notAddedSongs = (List<String>) albumInfo.get("notAddedSongs");

                        if (album == null) {
                            System.out.println("No album found for this song.");
                        } else {
                            System.out.println("\nAlbum Information:");
                            System.out.println("Title: " + album.getTitle());
                            System.out.println("Artist: " + album.getArtist());
                            System.out.println("Genre: " + album.getGenre());
                            System.out.println("Year: " + album.getYear());
                            System.out.println("Is album in library? " + (isAlbumInLibrary ? "Yes" : "No"));
                            System.out.println("Songs in Album:");
                            for (String addedSong : addedSongs) {
                                System.out.println("- " + addedSong + " (Added)");
                            }
                            for (String notAddedSong : notAddedSongs) {
                                System.out.println("- " + notAddedSong + " (Not Added)");
                            }
                        }
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

                // Check if the song is already in the library
                boolean songExists = false;
                for (Song song : library.getAllUserSongs()) {
                    if (song.getTitle().equalsIgnoreCase(songTitle) && song.getArtist().equalsIgnoreCase(songArtist)) {
                        songExists = true;
                        break;
                    }
                }

                if (songExists) {
                    System.out.println("Song is already in your library.");
                } else {
                    boolean addedSong = library.addSongToLibrary(songTitle, songArtist);
                    if (addedSong) {
                        System.out.println("Song added to library!");
                    } else {
                        System.out.println("Song not found in store or could not be added.");
                    }
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
            ArrayList<Song> songs = library.getAllUserSongs();
            if (songs.isEmpty()) {
                System.out.println("No user is logged in or there are no songs in your library.");
            } else {
                for (Song s : songs) {
                    System.out.println(s);
                }
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
            case "4":
                for (String pl : library.getAllPlaylists()) {
                    System.out.println(pl);
                }
                break;
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
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine().trim();
        System.out.println("   1) Add a song");
        System.out.println("   2) Remove a song");
        System.out.println("   3) View playlist contents");  // New option
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
                    System.out.println("Could not add song. Make sure it's in your library and playlist exists.");
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
            case "3":
                library.displayPlaylistContents(playlistName);
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
    private static void removeFromLibrary(Scanner scanner, LibraryModel library) {
        System.out.println("Remove from library:");
        System.out.println("   1) A single song");
        System.out.println("   2) A whole album");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Enter song title: ");
                String songTitle = scanner.nextLine().trim();
                System.out.print("Enter song artist: ");
                String songArtist = scanner.nextLine().trim();
                boolean removedSong = library.removeSongFromLibrary(songTitle, songArtist);
                if (removedSong) {
                    System.out.println("Song removed from library!");
                } else {
                    System.out.println("Song not found in library or could not be removed.");
                }
                break;
            case "2":
                System.out.print("Enter album title: ");
                String albumTitle = scanner.nextLine().trim();
                System.out.print("Enter album artist: ");
                String albumArtist = scanner.nextLine().trim();
                boolean removedAlbum = library.removeAlbumFromLibrary(albumTitle, albumArtist);
                if (removedAlbum) {
                    System.out.println("Album removed from library!");
                } else {
                    System.out.println("Album not found in library or could not be removed.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
	 // -----------------------------------------------------------------------
	 // 19) Shuffle songs in library or playlist
	 // -----------------------------------------------------------------------
	 private static void shuffleSongs(Scanner scanner, LibraryModel library) {
	     System.out.println("Shuffle songs in:");
	     System.out.println("   1) Library");
	     System.out.println("   2) Playlist");
	     String choice = scanner.nextLine().trim();

	     switch (choice) {
	         case "1":
	             library.shuffleLibrarySongs();
	             System.out.println("Songs in the library have been shuffled.");
	             break;
	         case "2":
	             System.out.print("Enter playlist name: ");
	             String playlistName = scanner.nextLine().trim();
	             library.shufflePlaylist(playlistName);
	             System.out.println("Songs in the playlist '" + playlistName + "' have been shuffled.");
	             break;
	         default:
	             System.out.println("Invalid choice.");
	             break;
	     }
	 }

    }
