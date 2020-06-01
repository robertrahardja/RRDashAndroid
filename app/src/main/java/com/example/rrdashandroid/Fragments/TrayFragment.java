package com.example.rrdashandroid.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.example.rrdashandroid.Activities.PaymentActivity;
import com.example.rrdashandroid.Adapters.TrayAdapter;
import com.example.rrdashandroid.AppDatabase;
import com.example.rrdashandroid.Objects.Tray;
import com.example.rrdashandroid.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrayFragment extends Fragment {
//    public class TrayFragment extends Fragment implements OnMapReadyCallback {

        private AppDatabase db;
        private ArrayList<Tray> trayList;
        private TrayAdapter adapter;
//
//        private FusedLocationProviderClient mFusedLocationProviderClient;
//        private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
//        private boolean mLocationPermissionGranted;
//        private GoogleMap mMap;
//
//        private Location mLastKnownLocation;
//        private static final int DEFAULT_ZOOM = 15;
//
//        private EditText address;
    public TrayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tray, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialise DB
        db = AppDatabase.getAppDatabase(getContext());
        listTray();

        trayList = new ArrayList<Tray>();
        adapter = new TrayAdapter(this.getActivity(), trayList);

        ListView listView = (ListView) getActivity().findViewById(R.id.tray_list);
        listView.setAdapter(adapter);

//        // Construct a FusedLocationProviderClient.
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.tray_map);
//        mapFragment.getMapAsync(this);
//
//        // Address EditText
//        address = (EditText) getActivity().findViewById(R.id.tray_address);
//
//        // Handle Map Address
//        handleMapAddress();
//
//        // Handle Add Payment Button Click event
//        handleAddPayment();


        Button buttonAddPayment = (Button) getActivity().findViewById(R.id.button_add_payment);
        buttonAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void listTray() {
        new AsyncTask<Void, Void, List<Tray>>() {

            @Override
            protected List<Tray> doInBackground(Void... voids) {
                return db.trayDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Tray> trays) {
                super.onPostExecute(trays);
                if (!trays.isEmpty()) {
                    // Refresh our listview
                    trayList.clear();
                    trayList.addAll(trays);
                    adapter.notifyDataSetChanged();

                    // Calculate the total
                    float total = 0;
                    for (Tray tray: trays) {
                        total += tray.getMealQuantity() * tray.getMealPrice();
                    }
                    TextView totalView = (TextView) getActivity().findViewById(R.id.tray_total);
                    totalView.setText("$" + total);
                } else {
                    // Display a message
                    TextView alertText = new TextView(getActivity());
                    alertText.setText("Your tray is empty. Please order a meal");
                    alertText.setTextSize(17);
                    alertText.setGravity(Gravity.CENTER);
                    alertText.setLayoutParams(
                            new TableLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    1
                            ));

                    LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.tray_layout);
                    linearLayout.removeAllViews();
                    linearLayout.addView(alertText);
                }
            }
        }.execute();

    }


}
