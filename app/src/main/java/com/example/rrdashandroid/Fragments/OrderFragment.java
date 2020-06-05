package com.example.rrdashandroid.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rrdashandroid.Adapters.TrayAdapter;
import com.example.rrdashandroid.Objects.Tray;
import com.example.rrdashandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private ArrayList<Tray> trayList;
    private TrayAdapter adapter;
    private Button statusView;

//    private GoogleMap mMap;
//    private Timer timer = new Timer();
//    private Marker driverMarker;
//    private static final int DEFAULT_ZOOM = 15;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        trayList = new ArrayList<Tray>();
        adapter = new TrayAdapter(this.getActivity(), trayList);

        ListView listView = (ListView) getActivity().findViewById(R.id.tray_list);
        listView.setAdapter(adapter);

        statusView = (Button) getActivity().findViewById(R.id.status);

        // Get The Latest Order Data
        getLatestOrder();

//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.order_map);
//        mapFragment.getMapAsync(this);
//
//        // Get the Driver's location
//        getDriverLocation();

    }

    private void getLatestOrder() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);
        String url = getString(R.string.API_URL) + "/customer/order/latest/?access_token=" + sharedPref.getString("token", "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LATEST ORDER", response.toString());

                        // Get Order details in JSONArray type
                        JSONArray orderDetailsArray = null;
                        String status = "";

                        try {
                            orderDetailsArray = response.getJSONObject("order").getJSONArray("order_details");
                            status = response.getJSONObject("order").getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Check if the current user have no order, then show a message
                        if (orderDetailsArray == null || orderDetailsArray.length() == 0) {
                            TextView alertText = new TextView(getActivity());
                            alertText.setText("You have no order");
                            alertText.setTextSize(17);
                            alertText.setGravity(Gravity.CENTER);
                            alertText.setLayoutParams(
                                    new TableLayout.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            1
                                    ));

                            LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.order_layout);
                            linearLayout.removeAllViews();
                            linearLayout.addView(alertText);
                        }

                        // Add this to the ListView. Convert JSON object to Tray object
                        for (int i = 0; i < orderDetailsArray.length(); i++) {
                            Tray tray = new Tray();
                            try {
                                JSONObject orderDetail = orderDetailsArray.getJSONObject(i);
                                tray.setMealName(orderDetail.getJSONObject("meal").getString("name"));
                                tray.setMealPrice(orderDetail.getJSONObject("meal").getInt("price"));
                                tray.setMealQuantity(orderDetail.getInt("quantity"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            trayList.add(tray);
                        }

                        // Update the ListView with Order Details data
                        adapter.notifyDataSetChanged();

                        // Update Status View
                        statusView.setText(status);
//
//                        // Show Restaurant and Customer on the map
//                        drawRouteOnMap(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);
    }

}
