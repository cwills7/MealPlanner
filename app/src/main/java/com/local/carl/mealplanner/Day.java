package com.local.carl.mealplanner;

/**
 * Created by carlr on 9/7/2017.
 */

class Day {
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


}
