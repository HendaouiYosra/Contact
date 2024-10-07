package com.example.contact;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ContactManager {
    SQLiteDatabase db=null;
    Context con;
    public ContactManager(Context con) {
        this.con = con;
    }
    public void ouvrir(){
        DBHandler helper=new DBHandler(con,"mabase.db",null,2);
        db=helper.getWritableDatabase();
    }
    public long ajout(String nom,int num){
        if (db == null) {
            // Log an error message or throw an exception
            throw new IllegalStateException("Database is not open. Call ouvrir() first.");
        }
        ContentValues values= new ContentValues();
        values.put(DBHandler.col_nom,nom);
        values.put(DBHandler.col_num,num);
        long a=0;
        a=db.insert(DBHandler.table_contact,null,values);
        return a;
    }

    public ArrayList<Contact>  getAllContact(){
        ArrayList<Contact> contact =new ArrayList<Contact>();
        Cursor cr = db.query(
                DBHandler.table_contact,
                new String[]{
                        DBHandler.col_id,
                        DBHandler.col_nom,
                        DBHandler.col_num},
                null,null,null,null,null);

        cr.moveToFirst();
        while(!cr.isAfterLast()){
            int i1=cr.getInt(0);
            String i2=cr.getString(1);
            int i3=cr.getInt(2);
            contact.add(new Contact(i1,i2,i3));
            cr.moveToNext();
        }

        cr.close();
        return contact;
    }
    public void supprimer(){}
    public void fermer (){ if (db != null && db.isOpen()) {
        db.close();
    }}
}
