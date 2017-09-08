package com.local.carl.mealplanner;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carlr on 9/7/2017.
 */

public class Meal implements Parcelable{
    int date;
    int mealVal;
    String name;
    String url;
    double rating;
    String notes;
    boolean isFavorite;

    public enum MealVal{
        BREAKFAST(1),
        LUNCH(2),
        DINNER(3);

        private int val;
        MealVal(int val){
            this.val = val;
        }
        public int getVal(){
            return val;
        }
    }

    public Meal(String name, String url, String notes, boolean isFavorite, int date, int mealVal){
        this.name = name;
        this.url = url;
        this.notes = notes;
        this.isFavorite = isFavorite;
        this.date = date;
        this.mealVal = mealVal;
    }

    public Meal(int date, int mealVal){
        this.name = "";
        this.url = "";
        this.notes = "";
        this.isFavorite = false;
        this.date = date;
        this.mealVal = mealVal;
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
        this.date = in.readInt();
        this.mealVal = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
        this.notes =  in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.date);
        dest.writeInt(this.mealVal);
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
