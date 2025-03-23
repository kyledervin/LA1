/*
 * File: LibraryModelTest.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: JUnit coverage testing for LibraryModel with >90% coverage.
 * Course: CSC 335 Spring 2025
 */

 package model;

 import database.*;
 
 import static org.junit.jupiter.api.Assertions.*;
 import org.junit.jupiter.api.Test;
 
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 
 
 
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
     public void testCreateUser() {
         assertTrue(libraryModel.createUser("a", "1"));
         assertFalse(libraryModel.createUser("a", "1"));
         assertFalse(libraryModel.createUser("", "1"));
         assertFalse(libraryModel.createUser("a", ""));
     }
 
     @Test
     public void testLoginAndLogout() {
         libraryModel.createUser("a", "1");
         assertTrue(libraryModel.login("a", "1"));
         assertTrue(libraryModel.isUserLoggedIn());
         libraryModel.logout();
         assertFalse(libraryModel.isUserLoggedIn());
         assertFalse(libraryModel.login("a", "2"));
     }
 
     @Test
     public void testAddSongToLibrary() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         assertTrue(libraryModel.addSongToLibrary("Daydreamer", "Adele"));
         assertEquals(1, libraryModel.getAllUserSongs().size());
         assertEquals("Daydreamer", libraryModel.getAllUserSongs().get(0).getTitle());
         assertFalse(libraryModel.addSongToLibrary("random", "random"));
         
         libraryModel.logout();
         assertFalse(libraryModel.addSongToLibrary("Daydreamer", "Adele"));
     }
 
     @Test
     public void testAddAlbumToLibrary() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         assertTrue(libraryModel.addAlbumToLibrary("21", "Adele"));
         assertEquals(1, libraryModel.getAllUserAlbums().size());
         assertEquals("21", libraryModel.getAllUserAlbums().get(0).getTitle());
         assertEquals(12, libraryModel.getAllUserSongs().size());
 
         assertFalse(libraryModel.addAlbumToLibrary("random", "random"));
         assertTrue(libraryModel.addAlbumToLibrary("21", "Adele"));
         assertEquals(1, libraryModel.getAllUserAlbums().size());
         assertEquals(12, libraryModel.getAllUserSongs().size());
         
         assertTrue(libraryModel.addAlbumToLibrary("A Rush of Blood to the Head", "Coldplay"));
         assertEquals(2, libraryModel.getAllUserAlbums().size());
     }
 
     @Test
     public void testSearchSongByTitleInLibrary() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         assertTrue(libraryModel.searchSongByTitleInLibrary("Rolling in the Deep").size() > 0);
         assertTrue(libraryModel.searchSongByTitleInLibrary("random").isEmpty());
     }
 
     @Test
     public void testSearchSongByArtistInLibrary() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         assertEquals(2, libraryModel.searchSongByArtistInLibrary("Adele").size());
         assertTrue(libraryModel.searchSongByArtistInLibrary("random").isEmpty());
 
     }
 
     @Test
     public void testSearchAlbumByTitleInLibrary() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addAlbumToLibrary("21", "Adele");
         assertEquals(1, libraryModel.searchAlbumByTitleInLibrary("21").size());
         assertTrue(libraryModel.searchAlbumByTitleInLibrary("random").isEmpty());
     }
 
     @Test
     public void testSearchAlbumByArtistInLibrary() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addAlbumToLibrary("21", "Adele");
         assertEquals(1, libraryModel.searchAlbumByArtistInLibrary("Adele").size());
         assertTrue(libraryModel.searchAlbumByArtistInLibrary("random").isEmpty());
     }
 
     @Test
     public void testGetAllUserSongs() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         assertTrue(libraryModel.getAllUserSongs().isEmpty());
 
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
         assertEquals(1, libraryModel.getAllUserSongs().size());
         assertEquals("Daydreamer", libraryModel.getAllUserSongs().get(0).getTitle());
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         assertEquals(2, libraryModel.getAllUserSongs().size());
     }
 
     @Test
     public void testGetAllUserAlbums() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         assertTrue(libraryModel.getAllUserAlbums().isEmpty());
 
         libraryModel.addAlbumToLibrary("21", "Adele");
         assertEquals(1, libraryModel.getAllUserAlbums().size());
         assertEquals("21", libraryModel.getAllUserAlbums().get(0).getTitle());
 
         libraryModel.addAlbumToLibrary("A Rush of Blood to the Head", "Coldplay");
         assertEquals(2, libraryModel.getAllUserAlbums().size());
     }
 
     @Test
     public void testGetAllUserArtists() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
         libraryModel.addSongToLibrary("Clocks", "Coldplay");
 
         ArrayList<String> artists = libraryModel.getAllUserArtists();
         assertEquals(2, artists.size());
         assertTrue(artists.contains("Adele"));
         assertTrue(artists.contains("Coldplay"));
     }
 
     @Test
     public void testGetAllPlaylists() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.createPlaylist("Rap");
         libraryModel.createPlaylist("Oldies");
 
         ArrayList<String> playlists = libraryModel.getAllPlaylists();
         assertEquals(2, playlists.size());
         assertTrue(playlists.contains("Rap"));
         assertTrue(playlists.contains("Oldies"));
     }
 
     @Test
     public void testGetFavoriteSongs() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.toggleFavoriteSong("Rolling in the Deep", "Adele");
         libraryModel.toggleFavoriteSong("Someone Like You", "Adele");
 
         ArrayList<Song> favorite = libraryModel.getFavoriteSongs();
         assertEquals(2, favorite.size());
     }
 
     @Test
     public void testCreatePlaylistAddRemove() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
 
         assertTrue(libraryModel.createPlaylist("Feels"));
         assertTrue(libraryModel.addSongToPlaylist("Feels", "Rolling in the Deep", "Adele"));
         assertTrue(libraryModel.removeSongFromPlaylist("Feels", "Rolling in the Deep", "Adele"));
         assertFalse(libraryModel.removeSongFromPlaylist("Feels", "Rolling in the Deep", "Adele"));
     }
 
     @Test
     public void testFavoriteAndRating() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         assertTrue(libraryModel.toggleFavoriteSong("Rolling in the Deep", "Adele"));
         assertTrue(libraryModel.getAllUserSongs().get(0).isFavorite());
 
         assertTrue(libraryModel.rateSong("Rolling in the Deep", "Adele", 5));
         assertEquals(5, libraryModel.getAllUserSongs().get(0).getRating());
 
         libraryModel.rateSong("Rolling in the Deep", "Adele", 6);
         assertEquals(0, libraryModel.getAllUserSongs().get(0).getRating());
     }
 
     @Test
     public void testRemoveSongFromLibrary() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         assertTrue(libraryModel.removeSongFromLibrary("Rolling in the Deep", "Adele"));
         assertFalse(libraryModel.removeSongFromLibrary("Rolling in the Deep", "Adele"));
     }
 
     @Test
     public void testRemoveAlbumFromLibrary() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addAlbumToLibrary("21", "Adele");
         assertTrue(libraryModel.removeAlbumFromLibrary("21", "Adele"));
         assertFalse(libraryModel.removeAlbumFromLibrary("21", "Adele"));
     }
 
     @Test
     public void testShuffleLibrarySongs() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
 
         List<Song> original = new ArrayList<>(libraryModel.getAllUserSongs());
         libraryModel.shuffleLibrarySongs();
         List<Song> newOrder = libraryModel.getAllUserSongs();
 
         assertNotEquals(original, newOrder);
     }
 
     @Test
     public void testShufflePlaylist() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
 
         libraryModel.createPlaylist("Hits");
         libraryModel.addSongToPlaylist("Hits", "Rolling in the Deep", "Adele");
         libraryModel.addSongToPlaylist("Hits", "Someone Like You", "Adele");
         libraryModel.addSongToPlaylist("Hits", "Daydreamer", "Adele");
 
         List<Song> original = new ArrayList<>(libraryModel.searchPlaylistByName("Hits").getSongs());
         libraryModel.shufflePlaylist("Hits");
         List<Song> newOrder = libraryModel.searchPlaylistByName("Hits").getSongs();
 
         assertNotEquals(original, newOrder);
     }
 
     
     @Test
     public void testGetAlbumInfoForSong() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         Map<String, Object> albumInfo = libraryModel.getAlbumInfoForSong("Rolling in the Deep", "Adele");
 
         assertNotNull(albumInfo.get("album"));
         assertTrue((boolean) albumInfo.get("isAlbumInLibrary"));
     }
   
     @Test
     public void testPlaySong() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
 
         String playing = libraryModel.playSong("Rolling in the Deep", "Adele");
         assertEquals("Now playing: Rolling in the Deep by Adele", playing);
 
         playing = libraryModel.playSong("NonExistentSong", "NonExistentArtist");
         assertEquals("Song not found in your library.", playing);
 
         libraryModel.logout();
         playing = libraryModel.playSong("Rolling in the Deep", "Adele");
         assertEquals("No user is logged in.", playing);
     }
 
     @Test
     public void testGetRecentlyPlayedSongs() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
 
         libraryModel.playSong("Rolling in the Deep", "Adele");
         libraryModel.playSong("Someone Like You", "Adele");
         libraryModel.playSong("Daydreamer", "Adele");
 
         List<Song> recentlyPlayed = libraryModel.getRecentlyPlayedSongs();
         assertEquals(3, recentlyPlayed.size());
         assertEquals("Daydreamer", recentlyPlayed.get(0).getTitle());
         assertEquals("Someone Like You", recentlyPlayed.get(1).getTitle());
         assertEquals("Rolling in the Deep", recentlyPlayed.get(2).getTitle());
 
         libraryModel.logout();
         assertTrue(libraryModel.getRecentlyPlayedSongs().isEmpty());
     }
 
     @Test
     public void testGetMostFrequentlyPlayedSongs() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
 
         libraryModel.playSong("Rolling in the Deep", "Adele");
         libraryModel.playSong("Rolling in the Deep", "Adele");
         libraryModel.playSong("Someone Like You", "Adele");
         libraryModel.playSong("Daydreamer", "Adele");
 
         List<PlayCount> mostPlayed = libraryModel.getMostFrequentlyPlayedSongs();
         assertEquals(3, mostPlayed.size());
         assertEquals("Rolling in the Deep", mostPlayed.get(0).getSong().getTitle());
         assertEquals(2, mostPlayed.get(0).getCount());
 
         libraryModel.logout();
         assertTrue(libraryModel.getMostFrequentlyPlayedSongs().isEmpty());
     }
 
     @Test
     public void testSortSongs() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
 
         List<Song> sortedByTitle = libraryModel.sortSongs("title");
         assertEquals("Daydreamer", sortedByTitle.get(0).getTitle());
         assertEquals("Rolling in the Deep", sortedByTitle.get(1).getTitle());
         assertEquals("Someone Like You", sortedByTitle.get(2).getTitle());
 
         List<Song> sortedByArtist = libraryModel.sortSongs("artist");
         assertEquals("Adele", sortedByArtist.get(0).getArtist());
 
         libraryModel.rateSong("Rolling in the Deep", "Adele", 5);
         libraryModel.rateSong("Someone Like You", "Adele", 3);
         libraryModel.rateSong("Daydreamer", "Adele", 4);
 
         List<Song> sortedByRating = libraryModel.sortSongs("rating");
         assertEquals(3, sortedByRating.get(0).getRating());
         assertEquals(5, sortedByRating.get(2).getRating());
     }
 
     @Test
     public void testSearchSongsByGenre() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
 
         List<Song> pop = libraryModel.searchSongsByGenre("Pop");
         assertEquals(3, pop.size());
 
         List<Song> rock = libraryModel.searchSongsByGenre("Rock");
         assertTrue(rock.isEmpty());
 
         libraryModel.logout();
         assertTrue(libraryModel.searchSongsByGenre("Pop").isEmpty());
     }
 
     @Test
     public void testGetFavoriteSongsPlaylist() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
 
         libraryModel.toggleFavoriteSong("Rolling in the Deep", "Adele");
         libraryModel.rateSong("Someone Like You", "Adele", 5);
 
         List<Song> favorites = libraryModel.getFavoriteSongsPlaylist();
         assertEquals(2, favorites.size());
     }
 
     @Test
     public void testGetTopRatedSongsPlaylist() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         libraryModel.addSongToLibrary("Someone Like You", "Adele");
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
 
         libraryModel.rateSong("Rolling in the Deep", "Adele", 5);
         libraryModel.rateSong("Someone Like You", "Adele", 4);
         libraryModel.rateSong("Daydreamer", "Adele", 3);
 
         List<Song> topRated = libraryModel.getTopRatedSongsPlaylist();
         assertEquals(2, topRated.size());
 
         boolean containsRolling = false;
         boolean containsSomeone = false;
 
         for (Song song : topRated) {
             if (song.getTitle().equals("Rolling in the Deep")) {
                 containsRolling = true;
             } else if (song.getTitle().equals("Someone Like You")) {
                 containsSomeone = true;
             }
         }
 
         assertTrue(containsRolling);
         assertTrue(containsSomeone);
 
         libraryModel.logout();
         assertTrue(libraryModel.getTopRatedSongsPlaylist().isEmpty());
     }
 
 
     @Test
     public void testGetGenrePlaylist() {
         libraryModel.createUser("a", "1");
         libraryModel.login("a", "1");
 
         libraryModel.addSongToLibrary("Daydreamer", "Adele");
         libraryModel.addSongToLibrary("Best for Last", "Adele");
         libraryModel.addSongToLibrary("Chasing Pavements", "Adele");
         libraryModel.addSongToLibrary("Cold Shoulder", "Adele");
         libraryModel.addSongToLibrary("Crazy for You", "Adele");
         libraryModel.addSongToLibrary("Melt My Heart to Stone", "Adele");
         libraryModel.addSongToLibrary("First Love", "Adele");
         libraryModel.addSongToLibrary("Right as Rain", "Adele");
         libraryModel.addSongToLibrary("Make You Feel My Love", "Adele");
         libraryModel.addSongToLibrary("My Same", "Adele");
         libraryModel.addSongToLibrary("Tired", "Adele");
         libraryModel.addSongToLibrary("Hometown Glory", "Adele");
 
         List<Song> genrePlaylist = libraryModel.getGenrePlaylist("Pop");
         assertNotNull(genrePlaylist);
         assertEquals(12, genrePlaylist.size());
 
         libraryModel.addSongToLibrary("Rolling in the Deep", "Adele");
         assertNull(libraryModel.getGenrePlaylist("Soul"));
 
         libraryModel.logout();
         assertNull(libraryModel.getGenrePlaylist("Pop"));
     }
}