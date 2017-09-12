package com.local.carl.mealplanner;

/**
 * Created by Carl.Wills on 9/12/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.local.carl.mealplanner.database.MenuDb;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.R.attr.resource;

public class CustomListViewAdapter extends ArrayAdapter <Meal>{
    List<Meal> modelItems = null;
    Context context;
    MenuDb menuDb;

    public CustomListViewAdapter(Context context, List<Meal> resource) {
        super(context,R.layout.fav_list_row, resource);
        menuDb = new MenuDb(context);
        modelItems = new ArrayList<>();
        final ArrayList<String> favoriteNames = new ArrayList<>();
        this.modelItems = resource;
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.fav_list_row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.edit_fav_tv);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.fav_checkbox);
        name.setText(modelItems.get(position).getName());
        if(modelItems.get(position).isFavorite() == 1){
            cb.setChecked(true);
        } else{
            cb.setChecked(false);
        }


        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modelItems.get(position).isFavorite() == 1) {
                    modelItems.get(position).setFavorite(0);
                    menuDb.updateFavorite(modelItems.get(position));
                }
                else {
                    modelItems.get(position).setFavorite(1);
                    menuDb.updateFavorite(modelItems.get(position));
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }


}