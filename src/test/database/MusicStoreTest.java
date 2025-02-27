/*
 * File: MusicStoreTest.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: JUnit coverage testing for MusicStore database with >90% coverage.
 * Course: CSC 335 Spring 2025
 */

package database;

import model.*;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class MusicStoreTest {
	private String musicDir = "albums";
	private MusicStore store;
	
	public MusicStoreTest() {
		store = new MusicStore();
		store.loadMusicStore(musicDir);
	}


    @Test
    public void testLoadMusicStore() {
        ArrayList<Album> albums = store.getAllAlbums();
        assertFalse(albums.isEmpty());
    }

    @Test
    public void testSearchAlbumByTitle() {
        ArrayList<Album> matches = store.searchAlbumByTitle("21");
        assertFalse(matches.isEmpty());
        assertEquals("21", matches.get(0).getTitle());
        assertEquals("Adele", matches.get(0).getArtist());
    }

    @Test
    public void testSearchAlbumByArtist() {
        ArrayList<Album> matches = store.searchAlbumByArtist("Adele");
        assertFalse("Should find albums by Adele", matches.isEmpty());
        
        boolean album19 = false;
        boolean album21 = false;

        for (Album album : matches) {
        	if (album.getTitle().equals("19")) {
        		album19 = true;
        	}
        	if (album.getTitle().equals("21")) {
        		album21 = true;
        	}
        }
        
        assertTrue(album19);
        assertTrue(album21);
    }


    @Test
    public void testSearchSongByTitle() {
        ArrayList<Song> matches = store.searchSongByTitle("Someone Like You");
        assertFalse(matches.isEmpty());
        assertEquals("Someone Like You", matches.get(0).getTitle());
    }

    
    @Test
    public void testSearchSongByArtist() {
        ArrayList<Song> matches = store.searchSongByArtist("Adele");
        assertFalse(matches.isEmpty());
        
        boolean someoneLikeYou = false;
        for (Song song : matches) {
        	if (song.getTitle().equals("Someone Like You")) {
        		someoneLikeYou = true;
        		break;
        	}
        }
        assertTrue(someoneLikeYou);
    }

    
}
