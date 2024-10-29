package com.example.contact;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    RecyclerView rv;
    Button btnajout;
    ArrayList<Contact> data; // List of all contacts
    ArrayList<Contact> filtered; // List of filtered contacts
    MyContactRecyclerAdapter adapter;
    ContactManager manager;
    EditText searchtxt;
    ImageView logout;
    Button btnannuler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);
        logout=findViewById(R.id.ivlogout);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        btnajout = findViewById(R.id.btnajout);
        manager = new ContactManager(this);
        manager.ouvrir();

        data = manager.getAllContact(); // Fetch all contacts
        filtered = new ArrayList<>(); // Initialize the filtered list
        filtered.addAll(data); // Now this will work
        adapter = new MyContactRecyclerAdapter(this, filtered);
        rv.setAdapter(adapter);

        searchtxt = findViewById(R.id.search);

        manager.fermer(); // Close the database after use
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogout();
            }
        });
        // Add a TextWatcher to the search field
        searchtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString()); // Call the filter method when text changes
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Contacts.this, Ajout.class);
                startActivityForResult(i, 1);
            }
        });
    }

    protected void filter(String s) {
        filtered.clear(); // Clear the filtered list
        if (s.isEmpty()) {
            filtered.addAll(data); // If search is empty, show all contacts
        } else {
            for (Contact c : data) {
                // Check if the name or number contains the search string
                if (c.getNom().toLowerCase().contains(s.toLowerCase()) ||
                        String.valueOf(c.getNum()).contains(s)) {
                    filtered.add(c); // Add contact to filtered list if matches
                }
            }
        }

        adapter.notifyDataSetChanged(); // Notify the adapter of the updated list
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Contact was added successfully
            refreshContacts(); // Method to refresh the contact list
        }
    }

    private void refreshContacts() {
        manager.ouvrir(); // Open the database
        data.clear(); // Clear the existing data
        data.addAll(manager.getAllContact()); // Fetch updated contact list
        filtered.clear(); // Clear filtered list
        filtered.addAll(data); // Repopulate filtered list
        adapter.notifyDataSetChanged(); // Notify the adapter of the changes
        manager.fermer(); // Close the database
    }
    @Override
    protected void onResume() {

            super.onResume();
            refreshContacts(); // Refresh contacts on resume

    }


    private void performLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Proceed with logout
                        Intent intent = new Intent(Contacts.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

}
