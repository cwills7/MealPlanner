package com.local.carl.mealplanner;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlr on 9/7/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MealViewHolder>{



    public static class MealViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView dayName;
        TextView mealName;

        MealViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.dayCards);
            dayName = (TextView) itemView.findViewById(R.id.day_name);
            mealName = (TextView) itemView.findViewById(R.id.meal_name);
        }
    }

    List<Day> days;

    RVAdapter(List<Day> days){
        this.days = days;
    }

    @Override
    public int getItemCount(){
        return days.size();
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.day_cards, viewGroup, false);
        MealViewHolder mvh = new MealViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MealViewHolder mealViewHolder, int i){
        mealViewHolder.dayName.setText(days.get(i).name);
        mealViewHolder.mealName.setText(days.get(i).breakfast.getName());

         // TODO: 9/7/2017  FINISH SETTING THESE VARS AND BUTTONS
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}
