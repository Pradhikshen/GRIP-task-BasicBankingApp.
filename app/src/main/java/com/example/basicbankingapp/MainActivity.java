package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // intent to open customer list
    public void openViewAllCustomers(View view){
        Intent myIntent = new Intent(MainActivity.this, ViewAllCustomers.class);
        MainActivity.this.startActivity(myIntent);
    }
}