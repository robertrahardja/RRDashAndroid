package com.example.rrdashandroid.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rrdashandroid.Activities.MealListActivity;
import com.example.rrdashandroid.Objects.Restaurant;
import com.example.rrdashandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Restaurant> restaurantList;

    public RestaurantAdapter(Activity activity, ArrayList<Restaurant> restaurantList) {
        this.activity = activity;
        this.restaurantList = restaurantList;
    }


    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_restaurant, null);
        }

        final Restaurant restaurant = restaurantList.get(position);

        TextView resName = (TextView) convertView.findViewById(R.id.res_name);
        TextView resAddress = (TextView) convertView.findViewById(R.id.res_address);
        ImageView resLogo = (ImageView) convertView.findViewById(R.id.res_logo);

        resName.setText(restaurant.getName());
        resAddress.setText(restaurant.getAddress());
        Picasso.with(activity.getApplicationContext()).load(restaurant.getLogo()).fit().into(resLogo);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MealListActivity.class);
                intent.putExtra("restaurantId", restaurant.getId());
                intent.putExtra("restaurantName", restaurant.getName());
                activity.startActivity(intent);
            }
        });

        return convertView;
    }
}
