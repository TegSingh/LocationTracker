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
    public LocationCRUD(Context context) {
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
                float latitude = cur.getFloat(2);
                float longitude = cur.getFloat(3);

                //make note and add to list
                LocationModel location = new LocationModel(id, address, latitude, longitude);
                location_list.add(location);
            } while(cur.moveToNext());
        }

        db.close();
        cur.close();
        return location_list;
    }

    // Method to delete the location based on specified ID
    public boolean deleteLocation(int id) {

        // Define the database
        SQLiteDatabase db = this.getWritableDatabase();
        Integer col_id = id;
        String[] args = {col_id.toString()};
        int num_rows_deleted = 0;
        try {
            num_rows_deleted = db.delete(TABLE_NAME, COLUMN_ID + " = ? ", args);
        } catch (Exception e) {
            System.out.println("ERROR executing query");
            db.close();
            return false;
        }

        if (num_rows_deleted == 1) {
            System.out.println("Row deleted successfully");
            db.close();
            return true;
        } else if (num_rows_deleted == 0) {
            System.out.println("Error deleting row: Row not found");
            db.close();
            return false;
        } else {
            System.out.println("Error deleting row: too many rows deleted: " + num_rows_deleted);
            db.close();
            return true;
        }
    }

    // Method to update a certain Location
    public boolean updateLocation(LocationModel location, LocationModel location_updated) {
        System.out.println("Update location method called from Location CRUD Database helper");
        System.out.println("Previous location: " + location.toString());
        System.out.println("Updated location: " + location_updated.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", location_updated.getId());
        cv.put("address", location_updated.getAddress());
        cv.put("latitude", location_updated.getLatitude());
        cv.put("longitude", location_updated.getLongitude());
        String whereClause = "id = ?";
        Integer id = new Integer(location_updated.getId());
        String whereArgs[] = {id.toString()};
        int result = db.update(TABLE_NAME, cv, whereClause, whereArgs);
        if (result == 1) {
            System.out.println("Update successful");
            return true;
        } else {
            System.out.println("Could not update");
            return false;
        }
    }

    public ArrayList<LocationModel> search_locations(String search_address) {

        System.out.println("Method to search location called from Location CRUD database helper with input: " + search_address);
        ArrayList<LocationModel> filtered_locations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ADDRESS + " LIKE \'%" + search_address + "%\'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                // Get the variable names from the cursor
                int id = cursor.getInt(0);
                String address = cursor.getString(1);
                float latitude = cursor.getFloat(2);
                float longitude = cursor.getFloat(3);
                LocationModel new_location = new LocationModel(id, address, latitude, longitude);
                filtered_locations.add(new_location);

            } while (cursor.moveToNext());
        }

        return filtered_locations;
    }
}
