package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Create a helper for CRUD operations on the database
    LocationCRUD locationsHelper;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Listing the Locations after creation of Main activity");
        // Call the method to display the locations on creation of the main activity
        displayLocationList();
    }

    // Method to add_location to the view
    public void add_location(View v) {

        System.out.println("Add location method has been called from Main activity");

        // ADD INTENT TO NEW ACTIVITY HERE
        Intent addIntent = new Intent(this, AddActivity.class);
        startActivity(addIntent);

    }

    // Method to display the entire list
    public void displayLocationList() {
        locationsHelper = new LocationCRUD(this);

        // Display the list once the main activity is created
        RecyclerView recyclerViewLocations = (RecyclerView) findViewById(R.id.LocationListRecyclerView);

        // Get the array list of all locations
        List<LocationModel> locationModelList = locationsHelper.getAllLocations();

        System.out.println("Number of items in the list: " + locationModelList.size());
        for (LocationModel location: locationModelList) {
            System.out.println(location.toString());
        }

        // Create a Linear Layout Manager object
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewLocations.setLayoutManager(linearLayoutManager);

        // Create the Custom Adapter class
        CustomAdapter customAdapter = new CustomAdapter(locationModelList, MainActivity.this);
        recyclerViewLocations.setAdapter(customAdapter);
    }

    // On click listener for search location button
    public void search_location(View v) {

        locationsHelper = new LocationCRUD(this);

        System.out.println("Method to search Location called from Main activity");
        EditText editTextSearchAddress = findViewById(R.id.editTextSearchAddress);
        String search_address = editTextSearchAddress.getText().toString();

        ArrayList<LocationModel> filtered_locations = locationsHelper.search_locations(search_address);

        if (filtered_locations.size() >= 1) {
            System.out.println("Locations found: " + filtered_locations.size());
        } else {
            System.out.println("No locations match the search input");
        }

        RecyclerView recyclerViewSearchLocations = (RecyclerView) findViewById(R.id.LocationListRecyclerView);

        // Create a Linear Layout Manager object
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((getApplicationContext()));
        recyclerViewSearchLocations.setLayoutManager(linearLayoutManager);

        CustomAdapter customAdapter = new CustomAdapter(filtered_locations, MainActivity.this);
        recyclerViewSearchLocations.setAdapter(customAdapter);

    }

}