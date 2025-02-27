/*
 * File: LibraryModel.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Models a user's music library...to be continued
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
 }