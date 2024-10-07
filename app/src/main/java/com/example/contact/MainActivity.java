package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //declaration
    EditText ednom,edmdp;
    private Button btnval,btnquit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Recuperation des composants
        edmdp=findViewById(R.id.edpass_auth);
        ednom=findViewById(R.id.ednom_auth);
        btnval=findViewById(R.id.btnvalid_auth);
        btnquit=findViewById(R.id.btnquit_auth);

        // ecouteur d'action
        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();

            }
        });
        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom=ednom.getText().toString();
                String mp=edmdp.getText().toString();
                if(nom.equalsIgnoreCase("test")&& mp.equals("test"))
                {
                    Intent i=new Intent(MainActivity.this, Contacts.class);
                    i.putExtra("USER",nom);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "valeur non valise", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}