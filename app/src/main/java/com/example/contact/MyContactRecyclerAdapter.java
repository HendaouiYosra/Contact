package com.example.contact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContactRecyclerAdapter extends RecyclerView.Adapter<MyContactRecyclerAdapter.MyViewHolder>  {

    private Context context; // Context for accessing resources
    private ArrayList<Contact> data; // List of contact data

    // Constructor to initialize the adapter with context and contact data
    public MyContactRecyclerAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.data = contacts;

    }

    // Inflate the item layout and return the view holder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.element, parent, false);
        return new MyViewHolder(view);
    }

    // Bind data to the view holder for each list item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact contact = data.get(position);
        holder.tvName.setText(contact.getNom());
        holder.tvNumber.setText(String.valueOf(contact.getNum()));

        // Set click listeners for each action (edit, call, delete)
        holder.ivEdit.setOnClickListener(v -> showEditDialog(contact, position));
        holder.ivCall.setOnClickListener(v -> callContact(contact));
        holder.ivDelete.setOnClickListener(v -> deleteContact(position));
    }

    // Return the number of items in the contact list
    @Override
    public int getItemCount() {
        return data.size();
    }


    // ViewHolder class to represent each list item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumber;
        ImageView ivCall, ivEdit, ivDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvnom_contact); // Contact name
            tvNumber = itemView.findViewById(R.id.tvnum_contact); // Contact number
            ivCall = itemView.findViewById(R.id.ivcall_contact); // Call action
            ivEdit = itemView.findViewById(R.id.ivedit_contact); // Edit action
            ivDelete = itemView.findViewById(R.id.ivdelete_contact); // Delete action
        }
    }

    // Method to update the data and notify the adapter
    public void updateData(ArrayList<Contact> newContacts) {
        this.data.clear();
        this.data.addAll(newContacts);
        notifyDataSetChanged(); // Notify adapter of the data change
    }

    // Method to show the edit dialog for a contact
    private void showEditDialog(Contact contact, int position) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.edit, null);

        // Dialog fields
        EditText etName = dialogView.findViewById(R.id.et_edit_name);
        EditText etPhone = dialogView.findViewById(R.id.et_edit_phone);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnSave = dialogView.findViewById(R.id.btn_save);

        // Pre-fill the fields with current contact data
        etName.setText(contact.getNom());
        etPhone.setText(String.valueOf(contact.getNum()));

        // Create and show the edit dialog
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        // Cancel button closes the dialog without saving
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Save button updates the contact and refreshes the RecyclerView
        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String phoneStr = etPhone.getText().toString();

            // Validate the phone number input
            try {
                int newPhone = Integer.parseInt(phoneStr);

                // Show confirmation dialog before saving
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Changes")
                        .setMessage("Do you want to save the changes?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            ContactManager manager = new ContactManager(context);

                            manager.ouvrir(); // Open the database
                            manager.modifier(contact.getId(), newName, String.valueOf(newPhone)); // Update contact
                            manager.fermer(); // Close the database
                            contact.setNom(newName);
                            contact.setNum(newPhone);
                            notifyItemChanged(position); // Notify adapter
                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                        .show();
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Invalid phone number. Please enter a valid number.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show(); // Show the dialog
    }

    // Method to make a call to a contact
    private void callContact(Contact contact) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        } else {
            // Make the phone call
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contact.getNum()));
            context.startActivity(callIntent);
        }
    }

    // Method to delete a contact
    private void deleteContact(int position) {
        Contact contactToDelete = data.get(position);
        new AlertDialog.Builder(context)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    ContactManager manager = new ContactManager(context);
                    manager.ouvrir(); // Open the database
                    manager.deleteContactById(contactToDelete.getId()); // Delete from DB
                    manager.fermer(); // Close the database


                    data.remove(position); // Remove from the list
                    notifyItemRemoved(position); // Notify the adapter
                    Toast.makeText(context, "Contact deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
