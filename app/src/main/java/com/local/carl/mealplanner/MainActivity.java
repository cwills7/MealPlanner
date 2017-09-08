package com.local.carl.mealplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
    public DateFormat df5 = new SimpleDateFormat("E, MMM dd");

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

//    public void startEditMeal(View v, int resultCode, Meal meal) {
//        Intent intent = new Intent(getContext(), EditMealActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("meal", meal);
//        this.startActivityForResult(intent, resultCode);
//    }

    public static View.OnClickListener onEditButtonClick(final Meal meal){


        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditMealActivity.class);
                intent.putExtra("meal", meal);
                ((Activity) v.getContext()).startActivityForResult(intent, 2);
            }
        };
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 2) {

            if(resultCode== RESULT_OK){
                Meal returnMeal = (Meal) data.getParcelableExtra("newMeal");
                menuDb.updateMeal(returnMeal);
                for(int i=0; i < days.size(); i++){
                    String formatName = null;
                    try {
                        formatName = df5.format(dt.parse(Integer.toString(returnMeal.getDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (days.get(i).getName().equals(formatName)){
                        Day newDay = days.get(i).replaceMeal(returnMeal, returnMeal.getMealVal());
                        break;
                    }
                }

                rvAdapter.notifyDataSetChanged();
            }
            super.onActivityResult(requestCode, resultCode, data);
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
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date queryDate = calendar.getTime();
        String queryDateString = dt.format(queryDate);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays +1);
        Date future = calendar.getTime();
        String futureString = dt.format(future);
        int todayInt = Integer.parseInt(todayString);
        int queryDateInt = Integer.parseInt(queryDateString);
        int futureInt = Integer.parseInt(futureString);
        Cursor cur = menuDb.getOneWeek(queryDateInt, futureInt);
        days = new ArrayList<>();

        try {
            cur.moveToFirst();
            Date lastDate = null;
            int lastMealVal = 0;
            Day day = null;
            int datesLeft = 7;
            while (!cur.isAfterLast()) {
                Date thisDate = dt.parse(Integer.toString(cur.getInt(cur.getColumnIndex(MenuDb.MENU_COLUMN_DATE))));
                int thisMealVal = cur.getInt(cur.getColumnIndex(MenuDb.MENU_COLUMN_MEAL));
                if (!thisDate.equals(lastDate) || thisMealVal!=lastMealVal) {
                    lastDate = thisDate;
                    lastMealVal = thisMealVal;
                    Meal meal = menuDb.convertCurToMeal(cur);
                    if (meal.getMealVal() == Meal.MealVal.BREAKFAST.getVal()) {
                        day = new Day(df5.format(lastDate), meal, null, null);
                    } else if (meal.getMealVal() == Meal.MealVal.LUNCH.getVal()) {
                        day.setLunch(meal);
                    } else if (meal.getMealVal() == Meal.MealVal.DINNER.getVal()) {
                        day.setDinner(meal);
                        days.add(day);
                    }
                }
                cur.moveToNext();
            }
            if (lastDate != null){
                datesLeft = (int)( (future.getTime() - lastDate.getTime()) / (1000 * 60 * 60 * 24) );
            }else{
                lastDate = today;
            }
            Calendar workCalendar = Calendar.getInstance();
            workCalendar.setTime(lastDate);
            for(int i = 0; i < datesLeft; i ++){
                Date currentDate = calendar.getTime();
                Meal breakfast = new Meal(Integer.parseInt(dt.format(currentDate)), Meal.MealVal.BREAKFAST.getVal());
                Meal lunch = new Meal(Integer.parseInt(dt.format(currentDate)), Meal.MealVal.LUNCH.getVal());
                Meal dinner = new Meal(Integer.parseInt(dt.format(currentDate)), Meal.MealVal.DINNER.getVal());
                days.add(new Day(df5.format(currentDate),
                        breakfast,
                        lunch,
                        dinner));
                menuDb.insertMeal(breakfast.getDate(),breakfast.getMealVal(),breakfast.getName(), "", "", 0 );
                menuDb.insertMeal(lunch.getDate(),lunch.getMealVal(),breakfast.getName(), "", "", 0 );
                menuDb.insertMeal(dinner.getDate(),dinner.getMealVal(),breakfast.getName(), "", "", 0 );
                calendar.add(Calendar.DAY_OF_YEAR, 1);

            }

        } catch(ParseException e){

        } catch (NullPointerException n){

        }
    }



}
