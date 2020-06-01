package com.example.rrdashandroid.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rrdashandroid.Activities.MealDetailActivity;
import com.example.rrdashandroid.Objects.Meal;
import com.example.rrdashandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Meal> mealList;
    private String restaurantId;


    public MealAdapter(Activity activity, ArrayList<Meal> mealList, String restaurantId) {
        this.activity = activity;
        this.mealList = mealList;
        this.restaurantId = restaurantId;
    }


    @Override
    public int getCount() {
        return mealList.size();
    }

    @Override
    public Object getItem(int position) {
        return mealList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_meal, null);
        }

        final Meal meal = mealList.get(position);

        TextView mealName = (TextView) convertView.findViewById(R.id.meal_name);
        TextView mealDesc = (TextView) convertView.findViewById(R.id.meal_desc);
        TextView mealPrice = (TextView) convertView.findViewById(R.id.meal_price);
        ImageView mealImage = (ImageView) convertView.findViewById(R.id.meal_image);

        mealName.setText(meal.getName());
        mealDesc.setText(meal.getShort_description());
        mealPrice.setText("$" + meal.getPrice());
        Picasso.with(activity.getApplicationContext()).load(meal.getImage()).fit().into(mealImage);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                Intent intent = new Intent(activity, MealDetailActivity.class);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("mealId", meal.getId());
                intent.putExtra("mealName", meal.getName());
                intent.putExtra("mealDescription", meal.getShort_description());
                intent.putExtra("mealPrice", meal.getPrice());
                intent.putExtra("mealImage", meal.getImage());
                activity.startActivity(intent);
            }
        });

        return convertView;
    }
}
