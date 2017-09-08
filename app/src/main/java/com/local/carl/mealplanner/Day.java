package com.local.carl.mealplanner;

/**
 * Created by carlr on 9/7/2017.
 */

public class Day {
    String name;
    Meal breakfast;
    Meal lunch;
    Meal dinner;

    Day(String name, Meal breakfast, Meal lunch, Meal dinner){
        this.name = name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meal getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Meal breakfast) {
        this.breakfast = breakfast;
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
    }


}
