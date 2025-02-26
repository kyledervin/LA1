/*
 * File: MusicStore.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Manages a collection of albums and songs and supports loading album 
 * and song data from files.
 * Course: CSC 335 Spring 2025
 */

 package database;

 import model.*;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.ArrayList;
 
 public class MusicStore {
     private ArrayList<Album> albums; // list of all albums in the store
 
     public MusicStore() {
         this.albums = new ArrayList<>(); // initial store is empty
     }
 
     /*
      * This method loads the music store using the albums.txt file. Within
      * the albums.txt file each album is stored as <Album Title>,<Artist>.
      * Then construct the individual album filename <Album Title>_<Artist>.txt.
      * Lastly call the helper method to parse each individual album file.
      * 
      * Parameters: dirPath (type: String) -- the path to the folder containing albums.txt
      * and all the album files
      * 
      * Returns: none
      */
     public void loadMusicStore(String dirPath) {
         albums.clear();
     
         File albumsFile = new File(dirPath, "albums.txt");
         
         if (!albumsFile.exists()) { // ensure albums.txt exists
             System.err.println("albums.txt not found in " + dirPath);
             return;
         }
     
         try (BufferedReader reader = new BufferedReader(new FileReader(albumsFile))) {
             String line;
 
             while ((line = reader.readLine()) != null) {
                 String[] attributes = line.split(",", 2);
 
                 if (attributes.length < 2) { // skip invalid line
                     continue;
                 }
  
                 String albumTitle = attributes[0].trim();
                 String artist = attributes[1].trim();
 
                 String albumFileName = albumTitle + "_" + artist + ".txt"; // construct individual album filename
                 File albumFile = new File(dirPath, albumFileName);
     
                 if (!albumFile.exists()) {
                     System.err.println("Album in albums.txt is not in folder: " + albumFileName);
                     continue;
                 }
     
                 Album album = parseAlbumFile(albumFile); // read contents of individual album file
                 if (album != null) {
                     albums.add(album);
                 }
             }
 
         } catch (IOException e) { 
             e.printStackTrace();
         }
     }
 
     /*
      * Helper method for the loadMusicStore() method. This method parses each
      * line within an individual album file. It starts with the header formatted
      * as <Album Title>,<Artist>,<Genre>,<Year>. It then reads each following line,
      * which consists of just <Song Name>. It creates song objects for all song names,
      * stores them in an album object, and returns the album object.
      * 
      * Parameters: albumFile (type: File) -- an individual album file with a header line
      * followed by lines of individual song names
      * 
      * Returns: none
      */
     private Album parseAlbumFile(File albumFile) {
         try (BufferedReader reader = new BufferedReader(new FileReader(albumFile))) {
             String heading = reader.readLine();
   
             if (heading == null) { // skip album if there is no header
                 return null;
             }
             String[] attributes = heading.split(",");
             if (attributes.length < 4) { // skip album if heading is incorrect format
                 return null;
             }
 
             String albumTitle = attributes[0].trim();
             String artist = attributes[1].trim();
             String genre = attributes[2].trim();
             String year = attributes[3].trim();
 
             ArrayList<Song> songs = new ArrayList<>(); // ArrayList preserves insertion order
             String songTitle = reader.readLine();
    
             while (songTitle != null) { // read until EOF
                 songTitle = songTitle.trim();
     
                 if (!songTitle.isEmpty()) { // skip empty lines
                     Song s = new Song(songTitle, artist, genre, year);
                     s.setAlbumTitle(albumTitle);
                     songs.add(s);
                 }
                 songTitle = reader.readLine(); // move reader to next line in the loop
             }
 
             return new Album(albumTitle, artist, genre, year, songs);
 
         } catch (IOException e) {
             e.printStackTrace();
         }
   
         return null;
     }    
 }