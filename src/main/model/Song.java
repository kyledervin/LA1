/*
 * File: Song.java
 * Authors: Jaden Gee + Kyle Dervin
 * Purpose: Represents a song with attributes like title, artist, genre, year, album title, rating, and favorite status.
 * Course: CSC 335 Spring 2025
 */

package model;

 public class Song {
    private String title;
    private String artist;
    private String genre;
    private String year;
    private String albumTitle; 
    private int rating;   
    private boolean favorite;  

    public Song(String title, String artist, String genre, String year) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        this.rating = 0;
        this.favorite = false;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            this.rating = 0;
            return;
        }
        this.rating = rating;
        if (rating == 5) {
            this.favorite = true;
        }
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(" by ").append(artist);
        if (albumTitle != null && !albumTitle.isEmpty()) {
            sb.append(" (").append(albumTitle).append(")");
        }
        if (rating > 0) {
            sb.append(" [rating: ").append(rating).append("]");
        }
        if (favorite) {
            sb.append(" *favorite*");
        }
        return sb.toString();
    }
}