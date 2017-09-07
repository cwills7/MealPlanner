package com.local.carl.mealplanner;

/**
 * Created by carlr on 9/7/2017.
 */

public class Meal {
    String name;
    String url;
    double rating;
    String notes;
    boolean isFavorite;

    Meal(String name, String url, String notes, boolean isFavorite){
        this.name = name;
        this.url = url;
        this.notes = notes;
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


}
