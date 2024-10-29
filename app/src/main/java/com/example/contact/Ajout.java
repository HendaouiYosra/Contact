package com.example.contact;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Ajout extends AppCompatActivity {
    private Button btnajouter,btnannuler;
    private EditText ednom;
    private EditText ednum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        btnannuler = findViewById(R.id.btn_annuler);
        btnajouter = findViewById(R.id.btn_ajouter);
        ednom = findViewById(R.id.ednom);
        ednum = findViewById(R.id.ednum);
        btnannuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ednom.setText("");
                ednum.setText("");
            }
        });


        btnajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ednom.getText().toString().trim();
                String numString = ednum.getText().toString().trim();
                int num = 0;

                try {
                    num = Integer.parseInt(numString); // Convert string to integer
                } catch (NumberFormatException e) {
                    Toast.makeText(Ajout.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    return; // Exit if the number is invalid
                }

                ContactManager contactManager = new ContactManager(Ajout.this);
                contactManager.ouvrir();
                long result = contactManager.ajout(nom, num);
                contactManager.fermer(); // Close the database

                if (result == -1) {
                    Toast.makeText(Ajout.this, "Failed to add contact", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Ajout.this, "Contact added", Toast.LENGTH_SHORT).show();
                    finish(); // Close this activity and return to Contacts
                }
            }
        });
    }
}
