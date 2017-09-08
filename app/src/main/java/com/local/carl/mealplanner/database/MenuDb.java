package com.local.carl.mealplanner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.local.carl.mealplanner.DBHelper;
import com.local.carl.mealplanner.Day;
import com.local.carl.mealplanner.Meal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by carlr on 9/7/2017.
 */

public class MenuDb extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "MenuHelper.db";
    public static final String MENU_TABLE_NAME = "menu";
    public static final String MENU_COLUMN_ID = "id";
    public static final String MENU_COLUMN_DATE = "date";
    public static final String MENU_COLUMN_MEAL = "meal";
    public static final String MENU_COLUMN_NAME = "name";
    public static final String MENU_COLUMN_URL = "url";
    public static final String MENU_COLUMN_NOTES = "notes";
    public static final String MENU_COLUMN_FAVORITE = "favorite";



    public MenuDb(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table menu " +
                "(id integer primary key, name text, date integer, meal integer, name text, url text, notes text, favorite int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // TODO Define onUpgrade
    }

    public ArrayList<Day> getOneWeek(int today, int future){
        ArrayList<Day> dayList = new ArrayList<Day>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from menu where date >= " + today +" and date <= " + future +" order by date, meal", null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
        //TODO Parse out Meals, and combine into Days. Adding empties where necessary
        }


        return dayList;
    }


    public boolean insertMeal (int date, int meal, String name, String url, String notes, int favorite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("meal", meal);
        contentValues.put("name", name);
        contentValues.put("url", url);
        contentValues.put("notes", notes);
        contentValues.put("favorite", favorite);
        db.insert(MENU_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getDay(int date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from menu where date=" + date+"", null);
        return res;
    }

    public Meal getMeal(int date, int meal){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from menu where date=" +date+" and meal=" +meal+"", null);
        res.moveToFirst();
        int dae = res.getInt(res.getColumnIndex(MENU_COLUMN_DATE));

        return new Meal(res.getString(res.getColumnIndex(MENU_COLUMN_NAME)),
                res.getString(res.getColumnIndex(MENU_COLUMN_URL)),
                res.getString(res.getColumnIndex(MENU_COLUMN_NOTES)),
                convertIntToBool(res.getInt(res.getColumnIndex(MENU_COLUMN_FAVORITE))),
                res.getInt(res.getColumnIndex(MENU_COLUMN_DATE)),
                res.getInt(res.getColumnIndex(MENU_COLUMN_MEAL)));
    }

    private boolean convertIntToBool(int in){
        return in==0;
    }

}

