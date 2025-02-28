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

    
}