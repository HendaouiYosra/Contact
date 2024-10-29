package com.example.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class ContactManager {
    SQLiteDatabase db = null;
    Context con;

    public ContactManager(Context con) {
        this.con = con;
    }

    public void ouvrir() {
        DBHandler helper = new DBHandler(con, "mabase.db", null, 2);
        db = helper.getWritableDatabase();
    }

    public long ajout(String nom, int num) {
        if (db == null) {
            throw new IllegalStateException("Database is not open. Call ouvrir() first.");
        }
        long a=0;
        ContentValues values = new ContentValues();
        values.put(DBHandler.col_nom, nom);
        values.put(DBHandler.col_num, num);
        a= db.insert(DBHandler.table_contact, null, values);
        return a;
    }

    public ArrayList<Contact> getAllContact() {
        ArrayList<Contact> contact = new ArrayList<>();

        if (db == null) {
            throw new IllegalStateException("Database is not open. Call ouvrir() first.");
        }

        try {
            Cursor cr = db.query(DBHandler.table_contact,
                    new String[]{DBHandler.col_id, DBHandler.col_nom, DBHandler.col_num},
                    null, null, null, null, null);

            if (cr != null && cr.moveToFirst()) {
                do {
                    int id = cr.getInt(0);
                    String name = cr.getString(1);
                    int number = cr.getInt(2);
                    contact.add(new Contact(id, name, number));
                } while (cr.moveToNext());

                cr.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contact;
    }

    public void deleteContactById(int id) {
        if (db == null) {
            throw new IllegalStateException("Database is not open. Call ouvrir() first.");
        }
        db.delete(DBHandler.table_contact, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void modifier(int contactId, String newName, String newPhone) {
        ContentValues values = new ContentValues();
        values.put(DBHandler.col_nom, newName);
        values.put(DBHandler.col_num, newPhone);

        // Update the contact in the database where the ID matches
        db.update(DBHandler.table_contact, values, DBHandler.col_id + " = ?", new String[]{String.valueOf(contactId)});
    }

    public void fermer() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
