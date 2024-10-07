package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {
    RecyclerView rv;
    Button btnajout;
    ArrayList<Contact> data = new ArrayList<>();
    MyContactRecyclerAdapter ad; // Declare adapter here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);

        btnajout = findViewById(R.id.btnajout);
        rv = findViewById(R.id.rv);

        ad = new MyContactRecyclerAdapter(Contacts.this,data);
        LinearLayoutManager manager= new LinearLayoutManager(Contacts.this,LinearLayoutManager.VERTICAL,true);
        rv.setLayoutManager(manager);
        rv.setAdapter(ad); // Set the adapter here

        // Set click listener to open Ajout activity
        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Contacts.this, Ajout.class);
                startActivity(i); // Start Ajout activity
            }
        });

        refreshContactList(); // Load contacts on first create
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshContactList(); // Refresh contacts whenever the activity resumes
    }

    private void refreshContactList() {
        ContactManager cm = new ContactManager(Contacts.this);
        cm.ouvrir();
        data.clear(); // Clear the existing data
        data.addAll(cm.getAllContact()); // Re-fetch and add all contacts
        ad.notifyDataSetChanged(); // Notify the adapter to refresh the ListView
        cm.fermer(); // Close the database
    }
}
