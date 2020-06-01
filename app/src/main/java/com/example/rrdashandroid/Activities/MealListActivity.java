package com.example.rrdashandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rrdashandroid.Adapters.MealAdapter;
import com.example.rrdashandroid.Objects.Meal;
import com.example.rrdashandroid.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MealListActivity extends AppCompatActivity {

    private ArrayList<Meal> mealArrayList;
    private MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        Intent intent = getIntent();
        String restaurantId = intent.getStringExtra("restaurantId");
        String restaurantName = intent.getStringExtra("restaurantName");

        getSupportActionBar().setTitle(restaurantName );

        mealArrayList = new ArrayList<Meal>();
        adapter = new MealAdapter(this, mealArrayList, restaurantId);
//        adapter = new MealAdapter(this, mealArrayList);


        ListView listView = (ListView) findViewById(R.id.meal_list);
        listView.setAdapter(adapter);
//        listView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return 3;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return LayoutInflater.from(MealListActivity.this).inflate(R.layout.list_item_meal, null);
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MealListActivity.this, MealDetailActivity.class);
//                startActivity(intent);
//            }
//        });

        getMeals(restaurantId);
    }

    private void getMeals(String restaurantId) {
        String url = getString(R.string.API_URL) + "/customer/meals/" + restaurantId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MEAL LIST", response.toString());

                        // Convert JSON data to JSON Array
                        JSONArray mealsJSONArray = null;

                        try {
                            mealsJSONArray = response.getJSONArray("meals");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Convert Json Array to Restaurant Array
                        Gson gson = new Gson();
                        Meal[] meals = gson.fromJson(mealsJSONArray.toString(), Meal[].class);

                        // Refresh ListView with up-to-date data
                        mealArrayList.clear();
                        mealArrayList.addAll(new ArrayList<Meal>(Arrays.asList(meals)));
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

}
