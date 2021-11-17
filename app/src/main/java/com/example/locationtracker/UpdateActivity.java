package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    LocationCRUD locationsHelper;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

    }

    public void update_location_click_listener(View v) {

        locationsHelper = new LocationCRUD(getApplicationContext());

        Button buttonSubmitLocation = findViewById(R.id.buttonSubmitLocation);
        EditText editTextAddress = findViewById(R.id.editTextAddress);
        EditText editTextLatitude = findViewById(R.id.editTextLatitude);
        EditText editTextLongitude = findViewById(R.id.editTextLongitude);

        // Get the String inputs from the views
        String address_updated = editTextAddress.getText().toString();
        String latitude_updated = editTextLatitude.getText().toString();
        String longitude_updated = editTextLongitude.getText().toString();

        // Validate input
        if (address_updated.equals("") || latitude_updated.equals("") || longitude_updated.equals("")) {
            System.out.println("Input values not provided");
            Toast.makeText(this, "Add final values for address, latitude and longitude", Toast.LENGTH_SHORT).show();

        } else {
            // Get the extras value provided from the custom adapter that started the intent
            Bundle extras = getIntent().getExtras();
            ArrayList<String> extrasList = extras.getStringArrayList("location");
            int id = new Integer(extrasList.get(0));
            String address = extrasList.get(1);
            float latitude = Float.parseFloat(extrasList.get(2));
            float longitude = Float.parseFloat(extrasList.get(3));

            // Create an object for previous location
            LocationModel location = new LocationModel(id, address, latitude, longitude);

            // Create an object for location to be updated
            LocationModel location_updated = new LocationModel(id, address_updated, Float.parseFloat(latitude_updated), Float.parseFloat(longitude_updated));
            boolean result = locationsHelper.updateLocation(location, location_updated);

            // Check if update method in database helper was executed successfully
            if (result) {
                System.out.println("Location updated successfully");
            } else {
                System.out.println("Could not update location");
            }

            // Move to the main activity upon making any changes
            Intent updateIntentReload = new Intent(this, MainActivity.class);
            startActivity(updateIntentReload);

        }


    }
}