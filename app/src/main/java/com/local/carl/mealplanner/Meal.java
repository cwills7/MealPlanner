package com.local.carl.mealplanner;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carlr on 9/7/2017.
 */

public class Meal implements Parcelable{
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

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    // Parcelling part
    public Meal(Parcel in){
        this.name = in.readString();
        this.url = in.readString();
        this.notes =  in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.notes);
    }

    public String getName() {
        return name!=null ? name:"";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url!=null ? url: "";
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
        return notes!=null ? notes:"";
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
