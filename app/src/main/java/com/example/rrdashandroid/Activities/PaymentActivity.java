package com.example.rrdashandroid.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rrdashandroid.R;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setTitle("");
    }
}
