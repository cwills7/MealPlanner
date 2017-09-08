package com.local.carl.mealplanner;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlr on 9/7/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MealViewHolder> implements View.OnClickListener{



    public static class MealViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView dayName;
        TextView breakfastName;
        TextView lunchName;
        TextView dinnerName;
        RelativeLayout dayExpandArea;

        public MealViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.dayCards);
            dayName = (TextView) itemView.findViewById(R.id.day_name);
            breakfastName = (TextView) itemView.findViewById(R.id.breakfast_name);
            lunchName = (TextView) itemView.findViewById(R.id.lunch_name);
            dinnerName = (TextView) itemView.findViewById(R.id.dinner_name);

            dayExpandArea = (RelativeLayout) itemView.findViewById(R.id.dayExpandArea);
        }

    }

    private List<Day> days;
    private Context mContext;
    int mExpandedPosition = -1;

    RVAdapter(Context context, List<Day> days){
        this.mContext = context;
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
        mvh.itemView.setOnClickListener(RVAdapter.this);
        mvh.itemView.setTag(mvh);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MealViewHolder mealViewHolder, int i){
        final boolean isExpanded = i==mExpandedPosition;

        mealViewHolder.dayName.setText(days.get(i).name);
        mealViewHolder.breakfastName.setText(days.get(i).breakfast.getName());
        mealViewHolder.lunchName.setText(days.get(i).lunch.getName());
        mealViewHolder.dinnerName.setText(days.get(i).dinner.getName());


        if (i == mExpandedPosition){
            mealViewHolder.dayExpandArea.setVisibility(View.VISIBLE);
        } else {
            mealViewHolder.dayExpandArea.setVisibility(View.GONE);
        }

         // TODO: 9/7/2017  FINISH SETTING THESE VARS AND BUTTONS
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View view){
        MealViewHolder holder = (MealViewHolder) view.getTag();
        Day theDay = days.get(holder.getPosition());

        if (mExpandedPosition >= 0) {
            int prev = mExpandedPosition;
            notifyItemChanged(prev);
        }

        mExpandedPosition = holder.getPosition();
        notifyItemChanged(mExpandedPosition);
        Toast.makeText(view.getContext(), "Clicked: " +theDay.name, Toast.LENGTH_SHORT).show();
    }
}
