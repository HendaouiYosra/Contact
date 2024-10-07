package com.example.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContactRecyclerAdapter extends RecyclerView.Adapter<MyContactRecyclerAdapter.MyViewHolder> {
    Context con;
    ArrayList<Contact> data;

    public MyContactRecyclerAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
    }

    @NonNull
    @Override
    public MyContactRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creation des viewholder selon l ecran +2
        //creation view
        CardView l=null;
        LayoutInflater inf=LayoutInflater.from(con);
        l=(CardView) inf.inflate(R.layout.element,null);
        return new MyViewHolder(l);
    }

    @Override
    public void onBindViewHolder(@NonNull MyContactRecyclerAdapter.MyViewHolder holder, int position) {
        Contact c=data.get(position);
        //affectation des holders
        holder.tvnom.setText(""+c.getNom());
        holder.tvnum.setText(""+c.getNum());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvnom;
        ImageView ivdelete;
        ImageView ivedit;
        ImageView ivcall;
        TextView tvid;
        TextView tvnum;
        public MyViewHolder(@NonNull View l) {
            super(l);
            // recuperation des holders
            TextView tvnom=l.findViewById(R.id.tvnom_contact);
            TextView tvnum=l.findViewById(R.id.tvnum_contact);
            TextView tvid=l.findViewById(R.id.tvid_contact);
            ImageView ivcall=l.findViewById(R.id.ivcall_contact);
            ImageView ivedit=l.findViewById(R.id.ivedit_contact);
            ImageView ivdelete=l.findViewById(R.id.ivdelete_contact);

        }
    }
}
