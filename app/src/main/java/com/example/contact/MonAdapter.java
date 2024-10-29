package com.example.contact;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class MonAdapter extends BaseAdapter {
    ArrayList<Contact> data;
    Context con;

    public MonAdapter(ArrayList<Contact> data, Context con) {
        this.data = data;
        this.con = con;
    }

    public MonAdapter(Contacts contacts, ArrayList<Contact> data) {
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //creation view
        CardView l=null;
        LayoutInflater inf=LayoutInflater.from(con);
        l=(CardView) inf.inflate(R.layout.element,null);
        // recuperation des holders
        TextView tvnom=l.findViewById(R.id.tvnom_contact);
        TextView tvnum=l.findViewById(R.id.tvnum_contact);

        ImageView ivcall=l.findViewById(R.id.ivcall_contact);
        ImageView ivedit=l.findViewById(R.id.ivedit_contact);
        ImageView ivdelete=l.findViewById(R.id.ivdelete_contact);
        Contact c=data.get(position);

        //affectation des holders
        tvnom.setText(""+c.getNom());
        tvnum.setText(""+c.getNum());

        ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.remove(position);
                notifyDataSetChanged();
            }
        });
        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel= String.valueOf(c.getNum());
                Intent callIntent= new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+tel));
                con.startActivity(callIntent);

            }
        });


        return l;
    }
}
