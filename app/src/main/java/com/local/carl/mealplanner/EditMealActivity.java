package com.local.carl.mealplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by carlr on 9/7/2017.
 */

public class EditMealActivity extends Activity implements View.OnClickListener{

    Button submit;
    EditText mealName;
    TextView mealNamePrompt;
    EditText mealUrl;
    EditText mealNotes;
    Meal meal;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meal);
        Bundle data = getIntent().getExtras();
        meal = (Meal) data.getParcelable("meal");

        submit = (Button) findViewById(R.id.edit_meal_submit);
        submit.setOnClickListener(this);

        mealName = (EditText) findViewById(R.id.edit_meal_name_text);
        mealName.setText(meal.getName());
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
