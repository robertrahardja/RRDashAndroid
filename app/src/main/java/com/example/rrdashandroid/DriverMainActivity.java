package com.example.rrdashandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class DriverMainActivity extends AppCompatActivity {

//    Toolbar toolbar = findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.icon_menu_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mDrawerLayout = findViewById(R.id.drawer_layout_driver);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        if (id == R.id.nav_orders) {
                            transaction.replace(R.id.content_frame, new OrderListFragment()).commit();
                        } else if (id == R.id.nav_delivery) {
                            transaction.replace(R.id.content_frame, new DeliveryFragment()).commit();
                        } else if (id == R.id.nav_statistic) {
                            transaction.replace(R.id.content_frame, new StatisticFragment()).commit();
                        } else if (id == R.id.nav_logout) {
                        }

                        return true;
                    }
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new OrderListFragment()).commit();

        // Get the User's info
        SharedPreferences sharedPref = getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);

        View header = navigationView.getHeaderView(0);
        ImageView customer_avatar = (ImageView) header.findViewById(R.id.customer_avatar);
        TextView customer_name = (TextView) header.findViewById(R.id.customer_name);

        customer_name.setText(sharedPref.getString("name", ""));
        Picasso.with(this).load(sharedPref.getString("avatar", "")).transform(new CircleTransform()).into(customer_avatar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
