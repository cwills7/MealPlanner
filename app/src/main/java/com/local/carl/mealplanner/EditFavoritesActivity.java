package com.local.carl.mealplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.local.carl.mealplanner.database.MenuDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl.Wills on 9/12/2017.
 */

public class EditFavoritesActivity extends Activity{

    MenuDb menuDb;
    CustomListViewAdapter adapter;
    ListView lv;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_favorites);
        menuDb = new MenuDb(this);
        List<Meal> modelItems = new ArrayList<>();
        List<String> favoriteNames = new ArrayList<>();
        List<Meal> favoriteList = menuDb.getFavoriteNames();
        for(int i=0; i < favoriteList.size(); i++){
            if (!favoriteNames.contains(favoriteList.get(i).getName())) {
                favoriteNames.add(favoriteList.get(i).getName());
                modelItems.add(favoriteList.get(i));
            }
        }
        lv = (ListView) findViewById(R.id.fav_lv);
        adapter = new CustomListViewAdapter(this, modelItems);
        lv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
