package com.local.carl.mealplanner.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentSender;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.local.carl.mealplanner.DBHelper;
import com.local.carl.mealplanner.Day;
import com.local.carl.mealplanner.Meal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

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
    public SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");




    public MenuDb(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table menu " +
                "(id integer primary key, date integer, meal integer, name text, url text, notes text, favorite int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // TODO Define onUpgrade
    }

    public Cursor getOneWeek(){
        Date today = new Date();
        String todayString = dt.format(today);
        int noOfDays = 7; //i.e two weeks
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date future = calendar.getTime();
        String futureString = dt.format(future);
        int todayInt = Integer.parseInt(todayString);
        int futureInt = Integer.parseInt(futureString);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from menu where date >= " + todayInt +" and date <= " + futureInt +" order by date, meal", null);
        return res;
    }

    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(MenuDb.MENU_TABLE_NAME, null, null);
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

    public int updateMeal(Meal meal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", meal.getDate());
        values.put("meal", meal.getMealVal());
        values.put("name", meal.getName());
        values.put("url", meal.getUrl());
        values.put("notes", meal.getNotes());
        values.put("favorite", meal.isFavorite());

        String selection = MENU_COLUMN_DATE + " =? and " + MENU_COLUMN_MEAL + " =?";
        String[] selectionArgs= {Integer.toString(meal.getDate()), Integer.toString(meal.getMealVal())};

        int count = db.update(MenuDb.MENU_TABLE_NAME, values, selection, selectionArgs);
        return count;
    }

    public int updateFavorite(Meal meal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favorite", meal.isFavorite());

        String selection = MENU_COLUMN_NAME + " =?";
        String[] selectionArgs= {meal.getName()};

        int count = db.update(MenuDb.MENU_TABLE_NAME, values, selection, selectionArgs);
        return count;

    }

    public Cursor getDay(int date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from menu where date=" + date+"", null);
        return res;
    }

    public List<Meal> getFavoriteNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from menu where favorite= 1", null);
        List<Meal> favs = new ArrayList<Meal>();
        res.moveToFirst();
        while(!res.isAfterLast()){
            favs.add(convertCurToMeal(res));
            res.moveToNext();
        }
        return favs;
    }

    public Meal getMeal(int date, int meal){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from menu where date=" +date+" and meal=" +meal+"", null);
        res.moveToFirst();
        int dae = res.getInt(res.getColumnIndex(MENU_COLUMN_DATE));

        return convertCurToMeal(res);
    }

    private boolean convertIntToBool(int in){
        return in==0;
    }

    public Meal convertCurToMeal(Cursor res){
        return new Meal(res.getString(res.getColumnIndex(MENU_COLUMN_NAME)),
                res.getString(res.getColumnIndex(MENU_COLUMN_URL)),
                res.getString(res.getColumnIndex(MENU_COLUMN_NOTES)),
                res.getInt(res.getColumnIndex(MENU_COLUMN_FAVORITE)),
                res.getInt(res.getColumnIndex(MENU_COLUMN_DATE)),
                res.getInt(res.getColumnIndex(MENU_COLUMN_MEAL)));
    }
}