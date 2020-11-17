package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CustomerDetails extends AppCompatActivity {

    TextView detailsName, detailsEmail, detailsBalance;
    int intPosition;
    int transfer = 1;
    EditText text;
    String value;
    int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        this.setTitle("Customer Details");


        String customerName = getIntent().getStringExtra("CustomerName");
        detailsName = (TextView) findViewById(R.id.details_name);
        detailsName.setText("Name: "+customerName);
        String customerEmail = getIntent().getStringExtra("CustomerEmail");
        detailsEmail = (TextView) findViewById(R.id.details_email);
        detailsEmail.setText("Email: "+customerEmail);
        String customerBalance = getIntent().getStringExtra("CustomerBalance");
        detailsBalance = (TextView) findViewById(R.id.details_balance);
        detailsBalance.setText("Balance: "+customerBalance+"$");
         intPosition = getIntent().getIntExtra("position", 0);

    }



    public void openViewAllCustomers(View view){
        text = (EditText)findViewById(R.id.et_amount);
        value = text.getText().toString();
        amount = Integer.parseInt(value);

        Intent myIntent = new Intent(CustomerDetails.this, ViewAllCustomers.class);
        myIntent.putExtra("intVariableName", transfer);
        myIntent.putExtra("intPosition", intPosition);
        myIntent.putExtra("amount", amount);
        CustomerDetails.this.startActivity(myIntent);
    }






}