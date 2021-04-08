package com.example.contacts.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.contacts.model.ContactListModel;
import com.example.contacts.model.ContactsModel;


import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ContactManager";
    // Contacts table name
    private static final String TABLE_CONTACTS = "Contact";

    public String databasePath = "";

    // Contacts Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_ADDRESS = "ADDRESS";
    private static final String KEY_GENDER = "GENDER";
    private static final String KEY_MOBILE = "MOBILE";

    SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
    }


    public void open() {
        db = this.getWritableDatabase();
    }

    public void close() {
        db.close();
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_GENDER + " TEXT, "
                + KEY_MOBILE + "TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);

    }

    //public void addContacts(ContactsModel.Contact result) {
    public void addContacts(ContactListModel result) {
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, result.getName());
        values.put(KEY_ADDRESS, result.getAddress());
        values.put(KEY_EMAIL, result.getEmail());
        values.put(KEY_GENDER, result.getGender());


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        close(); // Closing database connection
    }

    public int getContactCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
/*
    public ArrayList<ContactsModel.Contact> getAllContacts() {
        ArrayList<ContactsModel.Contact> contactList = new ArrayList<ContactsModel.Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactsModel.Contact result = new ContactsModel.Contact();
                result.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                result.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                result.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
                //result.setImgPath(cursor.getString(cursor.getColumnIndex(KEY_IMG_FLAG)));
                result.setGender(cursor.getString(cursor.getColumnIndex(KEY_GENDER)));

                Log.e("log_get", cursor.getString(1));
                // Adding contact to list
                contactList.add(result);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }*/

    public ArrayList<ContactListModel> getAllContacts() {
        ArrayList<ContactListModel> contactList = new ArrayList<ContactListModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //ContactsModel.Contact result = new ContactsModel.Contact();

                ContactListModel result = new ContactListModel();
                result.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                result.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                result.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
                //result.setImgPath(cursor.getString(cursor.getColumnIndex(KEY_IMG_FLAG)));
                result.setGender(cursor.getString(cursor.getColumnIndex(KEY_GENDER)));

                Log.e("log_get", cursor.getString(1));
                // Adding contact to list
                contactList.add(result);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

   // public int Updateuser(ContactsModel.Contact user) {
        public int updateuser(ContactListModel user) {
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_ADDRESS, user.getAddress());
        values.put(KEY_GENDER, user.getGender());

        int i = db.update(TABLE_CONTACTS, values, KEY_ID + " = " + user.getID(), null);
        if (i > 0) {
            Log.e("UPATE", "update data" + String.valueOf(i));
        }
        close();
        return i;
    }



    public void deleteEntry(int row) {

        open();
        db.delete(TABLE_CONTACTS, KEY_ID + "=?", new String[]{String.valueOf(row)});
        close();
    }

    public int existRecord(ContactsModel.Contact result) {

        int i = 0;
        open();
        try {

            String sqlstatament = ("SELECT COUNT(NAME) AS SRNO FROM Contact WHERE NAME = '" + result.getName() + "'"
                    + " AND EMAIL = '" + result.getEmail() + "'" + " AND ADDRESS = '" + result.getAddress() + "'");

            Cursor cursor = db.rawQuery(sqlstatament, null);

            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    i = cursor.getInt(0);
                }
            }
            cursor.close();
            close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("CountRow", String.valueOf(i));
        return i;

    }

    public boolean isDbPresent() {

        Log.v("log", databasePath);
        boolean checkFlag = true;
        SQLiteDatabase testDb;

        try {
            testDb = SQLiteDatabase.openDatabase(databasePath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException sqlException) {
            Log.v("log", "DB absent");
            checkFlag = false;
        }
        Log.v("log", "is DB present Exit!!!");
        return checkFlag;
    }

}