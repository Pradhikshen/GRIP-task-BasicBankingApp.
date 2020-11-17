package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewAllCustomers extends AppCompatActivity {

    SqlHandler sqlHandler;
    ListView lvCustomList, transactionList;
    int transactionWindow = 0;
    int intValue =0, intPosition = 0, amount =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_customers);
        this.setTitle("All Customers");
        final ArrayList<String> transactions = new ArrayList<String>();
        lvCustomList = (ListView) findViewById(R.id.lv_custom_list);
        transactionList = (ListView) findViewById(R.id.trans_list);
        sqlHandler = new SqlHandler(this);

        // Creating Arrayadapter for making list of transactions
        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, transactions);
        final ListView listView = (ListView) findViewById(R.id.trans_list);

        // getting values from other activities using intents to pass the values
         intValue = getIntent().getIntExtra("intVariableName", 0);
         intPosition = getIntent().getIntExtra("intPosition", 0);
        amount = getIntent().getIntExtra("amount", 0);

         final String[][] items = {
                 {"Jon Snow", "jonsnow@gmail.com", String.valueOf(20000)},
                {"Danaerys Targaryen", "dany@gmail.com", String.valueOf(30000)},
                {"Tyrion Lannister", "tyrion@gmail.com", String.valueOf(15000)},
                {"Arya Stark", "arya@gmail.com", String.valueOf(10000)},
                {"Sansa Stark", "sansa@gmail.com", String.valueOf(15000)},
                {"Jaime Lannister", "jaime@gmail.com", String.valueOf(10000)},
                {"White walker", "nightking@gmail.com", String.valueOf(30000)},
                {"Ned Stark", "ned@gmail.com", String.valueOf(11000)},
                {"Tormund", "tormund@gmail.com", String.valueOf(8000)},
                {"Sandor Clegane", "clegane@gmail.com", String.valueOf(9000)},
        };

         // Control flow statements for either transferring or to open customer details
        if(intValue == 1){
               for (int i = 0; i < items.length; ++i) {
                    String query11 = "INSERT INTO PHONE_CONTACTS(name,phone,balance) values ('"
                            + items[i][0] + "','" + items[i][1] + "','" + items[i][2] + "')";
                    sqlHandler.executeQuery(query11);
                }
                showList();
                if(amount<=Integer.parseInt(items[intPosition][2])) {
                    lvCustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int temp = Integer.parseInt(items[position][2]) + amount;
                        transactions.add("From: "+items[intPosition][0]+", To: "+items[position][0]+", Amount: "+amount+"$");
                        sqlHandler.updateValues(items[position][0], temp);
                        int newBalance = Integer.parseInt(items[intPosition][2])-amount;
                        sqlHandler.updateValues(items[intPosition][0], newBalance);
                        showList();
                        listView.setAdapter(itemsAdapter);
                        intValue = 2;
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "Not sufficient Balance in account",
                        Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ViewAllCustomers.this, MainActivity.class);
                ViewAllCustomers.this.startActivity(myIntent);
            }
        }
        else if(intValue  == 0) {
            for (int i = 0; i<items.length ; ++i) {
                String query11 = "INSERT INTO PHONE_CONTACTS(name,phone,balance) values ('"
                        + items[i][0] + "','" + items[i][1] + "','" + items[i][2] + "')";
                sqlHandler.executeQuery(query11);
            }
            showList();
            lvCustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String TempListView = items[position].toString();
                    Intent myIntent = new Intent(ViewAllCustomers.this, CustomerDetails.class);
                    myIntent.putExtra("CustomerName", items[position][0]);
                    myIntent.putExtra("CustomerEmail", items[position][1]);
                    myIntent.putExtra("CustomerBalance", items[position][2]);
                    int sender = position;
                    myIntent.putExtra("position", sender);
                    ViewAllCustomers.this.startActivity(myIntent);
                }
            });
        }
        else{
            showList();;
        }


    }

    // Creating show list method to show the items from the database
    private void showList() {
        ArrayList<ContactListItems> contactList = new ArrayList<ContactListItems>();
        contactList.clear();
        String query = "SELECT * FROM PHONE_CONTACTS ";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1.moveToFirst()) {
            do {
                ContactListItems contactListItems = new ContactListItems();
                contactListItems.setName(c1.getString(c1
                        .getColumnIndex("name")));
                contactListItems.setPhone(c1.getString(c1
                        .getColumnIndex("phone")));
                contactListItems.setBalance(c1.getString(c1
                        .getColumnIndex("balance")));
                contactList.add(contactListItems);
            } while (c1.moveToNext());
        }
        c1.close();
        ContactListAdapter contactListAdapter = new ContactListAdapter(
                ViewAllCustomers.this, contactList);
        lvCustomList.setAdapter(contactListAdapter);

    }

    // method to open main activity
    public void openHome(View view){
        Intent myIntent = new Intent(ViewAllCustomers.this, MainActivity.class);
        ViewAllCustomers.this.startActivity(myIntent);
    }

    // method to open transaction list
    public void openTransList(View view){
        if(transactionWindow == 0){
            LinearLayout layout = (LinearLayout) findViewById(R.id.list_layout);
            layout.setVisibility(view.VISIBLE);
            transactionWindow = 1;
        }else{
            LinearLayout layout = (LinearLayout) findViewById(R.id.list_layout);
            layout.setVisibility(view.INVISIBLE);
            transactionWindow = 0;
        }
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getLayoutInflater().inflate(R.layout.activity_view_all_customers, null);
        return true;
    }

}