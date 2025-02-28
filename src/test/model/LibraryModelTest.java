/*
 * File: LibraryModelTest.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: JUnit coverage testing for LibraryModel with >90% coverage.
 * Course: CSC 335 Spring 2025
 */

package model;

import database.MusicStore;


import org.junit.Test;


import static org.junit.Assert.*;
import java.util.ArrayList;

public class LibraryModelTest {

	private String musicDir = "albums";
	private MusicStore store;
	private LibraryModel libraryModel;
	
	public LibraryModelTest() {
		store = new MusicStore();
		store.loadMusicStore(musicDir);
		libraryModel = new LibraryModel(store);
	}

	
    @Test
    public void testAddSongToLibrary() {
        boolean added = libraryModel.addSongToLibrary("Daydreamer", "Adele");
        assertTrue(added);
        assertEquals(1, libraryModel.getAllUserSongs().size());
        assertEquals("Daydreamer", libraryModel.getAllUserSongs().get(0).getTitle());
        boolean notAdded = libraryModel.addSongToLibrary("NonExistentSong", "NonExistentArtist");
        assertFalse(notAdded);
    }

    
    @Test
    public void testAddAlbumToLibrary() {
        boolean added = libraryModel.addAlbumToLibrary("21", "Adele");
        assertTrue(added);
        assertEquals(1, libraryModel.getAllUserAlbums().size());
        assertEquals(12, libraryModel.getAllUserSongs().size());
    }
    
    
    @Test
    public void testSearchSongByTitleInLibrary() {
        libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
        assertTrue(libraryModel.searchSongByTitleInLibrary("Rolling in the Deep").size() > 0);
        assertTrue(libraryModel.searchSongByTitleInLibrary("NonExistentSong").isEmpty());
    }

    
    @Test
    public void testSearchSongByArtistInLibrary() {
        libraryModel.addSongToLibrary("Daydreamer", "Adele");
        libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
        assertEquals(2, libraryModel.searchSongByArtistInLibrary("Adele").size());
        assertTrue(libraryModel.searchSongByArtistInLibrary("NonExistentArtist").isEmpty());
    }

    
    @Test
    public void testSearchAlbumByTitleInLibrary() {
        libraryModel.addAlbumToLibrary("21", "Adele");
        assertEquals(1, libraryModel.searchAlbumByTitleInLibrary("21").size());
        assertTrue(libraryModel.searchAlbumByTitleInLibrary("NonExistentAlbum").isEmpty());
    }

    
    @Test
    public void testSearchAlbumByArtistInLibrary() {
        libraryModel.addAlbumToLibrary("21", "Adele");
        assertEquals(1, libraryModel.searchAlbumByArtistInLibrary("Adele").size());
        assertTrue(libraryModel.searchAlbumByArtistInLibrary("NonExistentArtist").isEmpty());
    }

   
    @Test
    public void testGetAllUserSongs() {
        assertTrue(libraryModel.getAllUserSongs().isEmpty());

        libraryModel.addSongToLibrary("Daydreamer", "Adele");
        assertEquals(1, libraryModel.getAllUserSongs().size());
        assertEquals("Daydreamer", libraryModel.getAllUserSongs().get(0).getTitle());

        libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
        assertEquals(2, libraryModel.getAllUserSongs().size());
        boolean daydreamer = false;
        boolean rollingInTheDeep = false;

        for (Song song : libraryModel.getAllUserSongs()) {
            if (song.getTitle().equals("Daydreamer")) {
                daydreamer = true;
            }
            if (song.getTitle().equals("Rolling in the Deep")) {
                rollingInTheDeep = true;
            }
        }

        assertTrue(daydreamer);
        assertTrue(rollingInTheDeep);
    }

    @Test
    public void testGetAllUserAlbums() {
        assertTrue(libraryModel.getAllUserAlbums().isEmpty());

        libraryModel.addAlbumToLibrary("21", "Adele");
        assertEquals(1, libraryModel.getAllUserAlbums().size());
        assertEquals("21", libraryModel.getAllUserAlbums().get(0).getTitle());

        libraryModel.addAlbumToLibrary("A Rush of Blood to the Head", "Coldplay");
        assertEquals(2, libraryModel.getAllUserAlbums().size());
        boolean twentyOne = false;
        boolean aRushOfBloodToTheHead = false;

        for (Album album : libraryModel.getAllUserAlbums()) {
            if (album.getTitle().equals("21")) {
                twentyOne = true;
            }
            if (album.getTitle().equals("A Rush of Blood to the Head")) {
                aRushOfBloodToTheHead = true;
            }
        }

        assertTrue(twentyOne);
        assertTrue(aRushOfBloodToTheHead);
    }
    
    
    @Test
    public void testGetAllUserArtists() {
        libraryModel.addSongToLibrary("Daydreamer", "Adele");
        libraryModel.addSongToLibrary("Clocks", "Coldplay");

        ArrayList<String> artists = libraryModel.getAllUserArtists();
        assertEquals(2, artists.size());
        assertTrue(artists.contains("Adele"));
        assertTrue(artists.contains("Coldplay"));
    }
    
    @Test
    public void testGetAllPlaylists() {
        libraryModel.createPlaylist("Rap");
        libraryModel.createPlaylist("Oldies");

        ArrayList<String> playlists = libraryModel.getAllPlaylists();
        assertEquals(2, playlists.size());
        assertTrue(playlists.contains("Rap"));
        assertTrue(playlists.contains("Oldies"));
    }

    @Test
    public void testGetFavoriteSongs() {
        libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
        libraryModel.addSongToLibrary("Someone Like You", "Adele");
        libraryModel.toggleFavoriteSong("Rolling in the Deep", "Adele");
        libraryModel.toggleFavoriteSong("Someone Like You", "Adele");

        ArrayList<Song> favorite = libraryModel.getFavoriteSongs();
        assertEquals(2, favorite.size());
        boolean rollingInTheDeep = false;
        boolean someoneLikeYou = false;

        for (Song song : favorite) {
            if (song.getTitle().equals("Rolling in the Deep")) {
            	rollingInTheDeep = true;
            }
            if (song.getTitle().equals("Someone Like You")) {
            	someoneLikeYou = true;
            }
        }

        assertTrue(rollingInTheDeep);
        assertTrue(someoneLikeYou);
    }


    @Test
    public void testCreatePlaylistAddRemove() {
        libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");

        assertTrue(libraryModel.createPlaylist("Sad Songs"));
        assertTrue(libraryModel.addSongToPlaylist("Sad Songs", "Rolling in the Deep", "Adele"));
        assertTrue(libraryModel.removeSongFromPlaylist("Sad Songs", "Rolling in the Deep", "Adele"));
        assertFalse(libraryModel.removeSongFromPlaylist("Sad Songs", "Rolling in the Deep", "Adele"));
    }
    

    @Test
    public void testFavoriteAndRating() {
        libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
        assertTrue(libraryModel.toggleFavoriteSong("Rolling in the Deep", "Adele"));
        assertTrue(libraryModel.getAllUserSongs().get(0).isFavorite());

        assertTrue(libraryModel.rateSong("Rolling in the Deep", "Adele", 5));
        assertEquals(5, libraryModel.getAllUserSongs().get(0).getRating());

        libraryModel.rateSong("Rolling in the Deep", "Adele", 99);
        assertEquals(0, libraryModel.getAllUserSongs().get(0).getRating());
    }
}