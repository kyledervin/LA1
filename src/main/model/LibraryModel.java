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

     /*
     * Helper method for searching albums in the user library.
     * 
     * Parameters: query (type: String) -- substring to search for within album titles or artists,
     * searchByTitle (type: boolean) -- if true, searches by title; otherwise, searches by artist
     * 
     * Returns: matches (type: ArrayList<Album>) -- A list of album objects matching the search criteria
     */
    private ArrayList<Album> searchAlbumsInLibrary(String query, boolean searchByTitle) {
        ArrayList<Album> matches = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Album album : currentUser.getUserAlbums()) {
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
     * searchByTitle (type: boolean) -- if true, searches by title; otherwise, searches by artist
     * 
     * Returns: matches (type: ArrayList<Song>) -- A list of song objects matching the search criteria
     */
    private ArrayList<Song> searchSongsInLibrary(String query, boolean searchByTitle) {
        ArrayList<Song> matches = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Song song : currentUser.getUserSongs()) {
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
        for (Playlist p : currentUser.getPlaylists()) {
            if (p.getName().equalsIgnoreCase(playlistName)) {
                return p;
            }
        }
        return null;
    }


    /*
     * Retrieves a copy of all songs in the user's library.
     * 
     * Returns: ArrayList<Song> -- a copy of the list of songs
     */
    public ArrayList<Song> getAllUserSongs() {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(currentUser.getUserSongs());
    }


    /*
     * Retrieves a copy of all albums in the user's library.
     * 
     * Returns: ArrayList<Album> -- a copy of the list of albums
     */
    public ArrayList<Album> getAllUserAlbums() {
    	if (currentUser == null) {
            System.out.println("No user is logged in."); // Debug message
            return new ArrayList<>(); // Return an empty list
        }
        return new ArrayList<>(currentUser.getUserAlbums());
    }

    /*
     * Retrieves a list of all unique artists in the user's library.
     * 
     * Returns: ArrayList<String> -- a list of unique artist names
     */
    public ArrayList<String> getAllUserArtists() {
    	if (currentUser == null) {
            System.out.println("No user is logged in."); // Debug message
            return new ArrayList<>(); // Return an empty list
        }
    	
        ArrayList<String> artists = new ArrayList<>();
        for (Song s : currentUser.getUserSongs()) {
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
    	if (currentUser == null) {
            System.out.println("No user is logged in."); // Debug message
            return new ArrayList<>(); // Return an empty list
        }
    
        ArrayList<String> playlistNames = new ArrayList<>();
        for (Playlist p : currentUser.getPlaylists()) {
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
    	if (currentUser == null) {
            System.out.println("No user is logged in."); // Debug message
            return new ArrayList<>(); // Return an empty list
        }
    	
        ArrayList<Song> favs = new ArrayList<>();
        for (Song s : currentUser.getUserSongs()) {
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
        currentUser.getPlaylists().add(new Playlist(name));
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
    	if (currentUser == null) {
            System.out.println("No user is logged in."); // Debug
            return false; // No user is logged in
        }

        Playlist playlist = searchPlaylistByName(playlistName);
        if (playlist == null) {
            System.out.println("Playlist not found: " + playlistName); // Debug
            return false; // Playlist not found
        }

        Song song = findSongInLibrary(songTitle, artist);
        if (song == null) {
            System.out.println("Song not found in library: " + songTitle + " by " + artist); // Debug
            return false; // Song not found in the user's library
        }

        System.out.println("Adding song to playlist: " + songTitle + " by " + artist); // Debug
        playlist.addSong(song);
        return true;
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
     * 
     * Returns: nothing
     */
    public void displayPlaylistContents(String playlistName) {
        Playlist playlist = searchPlaylistByName(playlistName);
        if (playlist == null) {
            System.out.println("Playlist not found.");
        } else {
            playlist.displayPlaylist();
        }
    }

    /*
     * Toggles the favorite status of a song in the user's library.
     * 
     * Parameters: songTitle (type: String) -- title of the song to toggle,
     * artist (type: String) -- artist of the song to toggle
     * 
     * Returns: boolean -- true if toggled successfully, false otherwise
     */
    public boolean toggleFavoriteSong(String songTitle, String artist) {
        for (Song s : currentUser.getUserSongs()) {
            if (s.getTitle().equalsIgnoreCase(songTitle) 
                && s.getArtist().equalsIgnoreCase(artist)) {
                s.setFavorite(!s.isFavorite());
                return true;
            }
        }
        return false;
    }

    /*
     * Rates a song in the user's library.
     * 
     * Parameters: songTitle (type: String) -- title of the song to rate,
     * artist (type: String) -- artist of the song to rate, rating (type: int) -- rating to assign to the song
     * 
     * Returns: boolean -- true if rated successfully, false otherwise
     */
    public boolean rateSong(String songTitle, String artist, int rating) {
        for (Song s : currentUser.getUserSongs()) {
            if (s.getTitle().equalsIgnoreCase(songTitle)
                && s.getArtist().equalsIgnoreCase(artist)) {
                s.setRating(rating); 
                return true;
            }
        }
        return false;
    }   
    /**
	 * Removes a song from the current user's library.
	 */
	public boolean removeSongFromLibrary(String songTitle, String artist) {
	    if (currentUser == null) {
	        return false; // No user is logged in
	    }
	
	    Song songToRemove = null;
	    for (Song song : currentUser.getUserSongs()) {
	        if (song.getTitle().equalsIgnoreCase(songTitle) && song.getArtist().equalsIgnoreCase(artist)) {
	            songToRemove = song;
	            break;
	        }
	    }
	
	    if (songToRemove != null) {
	        currentUser.getUserSongs().remove(songToRemove);
	        
	     // Remove the song from all playlists
	        for (Playlist playlist : currentUser.getPlaylists()) {
	            playlist.removeSong(songToRemove);
	        }
	        return true;
	    }
	    return false;
	}
	
	/**
	 * Removes an album from the current user's library.
	 */
	public boolean removeAlbumFromLibrary(String albumTitle, String artist) {
	    if (currentUser == null) {
	        return false; // No user is logged in
	    }
	
	    Album albumToRemove = null;
	    for (Album album : currentUser.getUserAlbums()) {
	        if (album.getTitle().equalsIgnoreCase(albumTitle) && album.getArtist().equalsIgnoreCase(artist)) {
	            albumToRemove = album;
	            break;
	        }
	    }
	
	    if (albumToRemove != null) {
	        currentUser.getUserAlbums().remove(albumToRemove);
	        // Also remove all songs from the album
	        currentUser.getUserSongs().removeAll(albumToRemove.getSongs());
	        return true;
	    }
	    return false;
	}
	/**
	 * Shuffles the songs in the current user's library.
	 */
	public void shuffleLibrarySongs() {
	    if (currentUser == null) {
	        return; // No user is logged in
	    }
	    Collections.shuffle(currentUser.getUserSongs());
	}
	
	/**
	 * Shuffles the songs in a playlist.
	 */
	public void shufflePlaylist(String playlistName) {
		if (currentUser == null) {
	        return; // No user is logged in
	    }

	    Playlist playlist = searchPlaylistByName(playlistName);
	    if (playlist != null) {
	        playlist.shuffle(); // Shuffle the songs in the playlist
	    }
	}
	
	/**
	 * Gets the album information for a song and checks if the album is already in the user's library.
	 */
	public Map<String, Object> getAlbumInfoForSong(String songTitle, String artist) {
		Map<String, Object> result = new HashMap<>();

	    if (currentUser == null) {
	        return result; // No user is logged in
	    }

	    // Search for the song in the music store
	    List<Song> matchingSongs = musicStore.searchSongByTitle(songTitle);
	    for (Song song : matchingSongs) {
	        if (song.getArtist().equalsIgnoreCase(artist)) {
	            // Get the album for the song
	            Album album = musicStore.getAlbumForSong(song);
	            if (album != null) {
	                result.put("album", album);

	                // Check if the album is in the library
	                boolean isAlbumInLibrary = false;
	                for (Album userAlbum : currentUser.getUserAlbums()) {
	                    if (userAlbum.getTitle().equalsIgnoreCase(album.getTitle()) &&
	                        userAlbum.getArtist().equalsIgnoreCase(album.getArtist())) {
	                        isAlbumInLibrary = true;
	                        break;
	                    }
	                }
	                result.put("isAlbumInLibrary", isAlbumInLibrary);

	                // Check which songs on the album are added to the library
	                List<String> addedSongs = new ArrayList<>();
	                List<String> notAddedSongs = new ArrayList<>();
	                for (Song albumSong : album.getSongs()) {
	                    boolean isSongInLibrary = false;
	                    for (Song userSong : currentUser.getUserSongs()) {
	                        if (userSong.getTitle().equalsIgnoreCase(albumSong.getTitle()) &&
	                            userSong.getArtist().equalsIgnoreCase(albumSong.getArtist())) {
	                            isSongInLibrary = true;
	                            break;
	                        }
	                    }
	                    if (isSongInLibrary) {
	                        addedSongs.add(albumSong.getTitle());
	                    } else {
	                        notAddedSongs.add(albumSong.getTitle());
	                    }
	                }
	                result.put("addedSongs", addedSongs);
	                result.put("notAddedSongs", notAddedSongs);

	                return result;
	            }
	        }
	    }

	    return result; // Return an empty map if no album is found
	}
}