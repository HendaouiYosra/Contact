package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Accueil extends AppCompatActivity {

    private TextView tvusername;
    private TextView btnajout;
    private TextView btnaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acceuil);
        tvusername=findViewById(R.id.tvuser_acc);
        btnajout=findViewById(R.id.btnajou_acc);
        btnaff=findViewById(R.id.btnaff_acc);

        Intent x=this.getIntent();
        Bundle b=x.getExtras();
        String u=b.getString("USER");
        tvusername.setText("Accueil de M."+u);
        btnaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Accueil.this, Contacts.class);
                startActivity(i);
            }
        });
    }

}