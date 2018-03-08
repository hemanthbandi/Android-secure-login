package com.example.hemanth.FinalProject;

/**
 * Created by hemanth on 7/2/17.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names

    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS +  "("
                  + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    long addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_EMAIL, contact.getEmail()); // Contact Phone

        // Inserting Row
       long v= db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        return  v;
    }

    // Getting single contact
    String getContact() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {
                KEY_NAME, KEY_EMAIL }, null,
                null, null, null, null, null);
        Log.e("CURSOR", "CURSOR=" + cursor.getCount());

        if (cursor != null&&cursor.getCount()>0) {
            cursor.moveToFirst();
            Contact contact = new Contact(cursor.getString(0),
                    cursor.getString(1));

            return contact.getEmail();
        }else
        {
            return null;
        }
    }

    // Getting All Contacts


}

