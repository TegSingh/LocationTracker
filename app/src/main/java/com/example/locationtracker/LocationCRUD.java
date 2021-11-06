package com.example.locationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


// Create an instance of this class in Main Activity
public class LocationCRUD extends SQLiteOpenHelper {

    // Declare some constants for database name, table name and column names
    public static final String DATABASE_NAME = "Location.db";
    public static final String TABLE_NAME = "location_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_LATITUDE = "LATITUDE";
    public static final String COLUMN_LONGITUDE = "LONGITUDE";

    // Using default constructor matching super s.t. whenever the constructor is called the database is created
    public LocationCRUD(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        System.out.println("LocationCRUD constructor is called");
        SQLiteDatabase db = this.getWritableDatabase();
        if(db != null) {
            System.out.println("Location.db created successfully");
        }
    }


    // To create the table on creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("On Create table called");
        db.execSQL("CREATE TABLE location_table (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ADDRESS TEXT NOT NULL, LATITUDE TEXT NOT NULL, LONGITUDE TEXT NOT NULL)");
        System.out.println("Table: " + TABLE_NAME + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("On upgrade table called");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to call when data needs to be added
    public boolean insertLocation(LocationModel location) {

        System.out.println("Insert location method called from LocationCRUD");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ADDRESS, location.getAddress());
        cv.put(COLUMN_LATITUDE, location.getLatitude());
        cv.put(COLUMN_LONGITUDE, location.getLongitude());

        // Add the content values to the database
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            // Insert was unsuccessful
            return false;
        } else {
            return true;
        }
    }

    // Method to get all Locations in the table
    public List<LocationModel> getAllLocations() {

        List<LocationModel> location_list = new ArrayList<>();

        // Define a readable database
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cur = db.rawQuery(query, null);

        // While cursor has an element to move to
        if(cur.moveToNext()) {
            do {
                int id = cur.getInt(0);
                String address = cur.getString(1);
                String latitude = cur.getString(2);
                String longitude = cur.getString(3);

                //make note and add to list
                LocationModel location = new LocationModel(id, address, latitude, longitude);
                location_list.add(location);
            } while(cur.moveToNext());
        }

        db.close();
        cur.close();
        return location_list;
    }
}
