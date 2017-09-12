package com.local.carl.mealplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.local.carl.mealplanner.database.MenuDb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by carlr on 9/7/2017.
 */

public class EditMealActivity extends Activity implements View.OnClickListener{

    Button submit;
    AutoCompleteTextView mealName;
    TextView mealNamePrompt;
    EditText mealUrl;
    EditText mealNotes;
    ImageButton favButton;
    Meal meal;
    MenuDb menuDb;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meal);
        Bundle data = getIntent().getExtras();
        meal = (Meal) data.getParcelable("meal");
        menuDb = new MenuDb(this);
        submit = (Button) findViewById(R.id.edit_meal_submit);
        submit.setOnClickListener(this);

        //FAV BUTTON
        favButton = (ImageButton) findViewById(R.id.fav_button);
        if(meal.isFavorite()==0){
            favButton.setImageResource(R.drawable.heart_transparent);
        } else{
            favButton.setImageResource(R.drawable.heart_full);
        }
        favButton.getBackground().setAlpha(0);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(meal.isFavorite()== 0) {
                    favButton.setImageResource(R.drawable.heart_full);
                    meal.setFavorite(1);
                } else if (meal.isFavorite() ==1) {
                    favButton.setImageResource(R.drawable.heart_transparent);
                    meal.setFavorite(0);
                }
            }
        });

        //mealName autocomplete
        final List<Meal> favoriteList = menuDb.getFavoriteNames();
        final ArrayList<String> favoriteNames = new ArrayList<>();
        for(int i=0; i < favoriteList.size(); i++){
            if (!favoriteNames.contains(favoriteList.get(i).getName())) {
                favoriteNames.add(favoriteList.get(i).getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, favoriteNames.toArray(new String[0]));
        mealName = (AutoCompleteTextView)
                findViewById(R.id.edit_meal_name_text);
        mealName.setAdapter(adapter);
        if(mealName.getText().equals("Empty")) {
            mealName.setText("");
            mealName.setHint("Empty");
        }else{
            mealName.setText(meal.getName());
        }
        mealName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = (String) parent.getItemAtPosition(position);
                int index = favoriteNames.indexOf(selectedName);
                Meal thisMeal = favoriteList.get(index);
                mealUrl.setText(thisMeal.getUrl());
                mealNotes.setText(thisMeal.getNotes());
                meal.setFavorite(thisMeal.isFavorite);
                if(meal.isFavorite()==0){
                    favButton.setImageResource(R.drawable.heart_transparent);
                } else{
                    favButton.setImageResource(R.drawable.heart_full);
                }
            }
        });

        //Initializing the other components of the page
        mealNamePrompt = (TextView) findViewById(R.id.edit_meal_name_prompt);
        mealUrl = (EditText) findViewById(R.id.edit_meal_url_text);
        mealUrl.setText(meal.getUrl());
        mealNotes = (EditText) findViewById(R.id.edit_meal_notes_text);
        mealNotes.setText(meal.getNotes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        Toast.makeText(v.getContext(), "Clicked Submit!", Toast.LENGTH_SHORT).show();
        String name = mealName.getText().toString();
        String url = "";
        String notes = "";
        if(name.isEmpty()){
            mealNamePrompt.setTextColor(getResources().getColor(R.color.requiredError));
            Toast.makeText(v.getContext(), "MEAL NAME REQUIRED!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            url = mealUrl.getText().toString();
            notes = mealNotes.getText().toString();
        }

        meal.setName(name);
        meal.setUrl(url);
        meal.setNotes(notes);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newMeal", meal);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }
}
