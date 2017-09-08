package com.local.carl.mealplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.local.carl.mealplanner.database.MenuDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public SimpleDateFormat dt = new SimpleDateFormat("yyyyyMMdd");

    private MenuDb menuDb;
    private List<Day> days;
    static RecyclerView mealList;
    private static Context mContext;
    Button breakfastButton;
    Button lunchButton;
    Button dinnerButton;
    static RVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting content and setting up CardView
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        menuDb = new MenuDb(this);

        mealList = (RecyclerView) findViewById(R.id.mealList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mealList.setLayoutManager(llm);

        initializeData();
        initializeAdapter();

        //Initializing Buttons
        breakfastButton = (Button) findViewById(R.id.breakfast_button);
        lunchButton = (Button) findViewById(R.id.lunch_button);
        dinnerButton = (Button) findViewById(R.id.dinner_button);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    private void initializeAdapter() {
        rvAdapter = new RVAdapter(this.getApplicationContext(), days);
        mealList.setAdapter(rvAdapter);
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    public static void startEditMeal(View v, Meal meal) {
        Toast.makeText(v.getContext(), "Clicked: " +meal.name, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), EditMealActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("meal", meal);
        mContext.startActivity(intent);
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {


                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initializeData(){
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
        //TODO menuDb.getOneWeek(todayInt, futureInt);

        days = new ArrayList<>();
        days.add(new Day("Monday", new Meal("BreakFast1", "url", "notes", false, 20170908, Meal.MealVal.BREAKFAST.getVal()), new Meal("LUNCH!", "url", "notes", false, 20170908, Meal.MealVal.LUNCH.getVal()), new Meal("DIN1", "url", "notes", false, 20170908, Meal.MealVal.DINNER.getVal())));
        days.add(new Day("Tuesday", new Meal("BreakFast2", "url", "notes", false, 20170909, Meal.MealVal.BREAKFAST.getVal()), new Meal("LUNCH2", "url", "notes", false, 20170909, Meal.MealVal.LUNCH.getVal()), new Meal("DINNER2", "url", "notes", false, 20170909, Meal.MealVal.DINNER.getVal())));
        days.add(new Day("Wednesday", new Meal("BreakFast3", "url", "notes", false, 20170910, Meal.MealVal.BREAKFAST.getVal()), new Meal("LUNCH2", "url", "notes", false, 20170910, Meal.MealVal.LUNCH.getVal()), new Meal("DINDIN3", "url", "notes", false, 20170910, Meal.MealVal.DINNER.getVal())));
    }



}
