package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class AddActivity extends AppCompatActivity {

    // Make the location to be added a class variable
    LocationModel added_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void generate_address(View view) {
        System.out.println("Generate address called from the Add Activity");

        EditText inputLatitude = findViewById(R.id.inputLatitude);
        EditText inputLongitude = findViewById(R.id.inputLongitude);
        String latitude_string = inputLatitude.getText().toString();
        String longitude_string = inputLongitude.getText().toString();

        // Convert string to float values
        Float latitude = Float.parseFloat(latitude_string);
        Float longitude = Float.parseFloat(longitude_string);

        boolean flag = false;

        // Validate the input values
        if (latitude >= -90.0 && latitude <= 90.0) {
            System.out.println("Latitude valid. generating address");
            flag = true;
        } else {
            System.out.println("Error: Latitude invalid");
            Toast.makeText(this, "Enter a valid latitude from -90 to 90", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if (longitude >= -180.0 && longitude <= 80.0) {
            System.out.println("Longitude valid. generating address");
            flag = true;
        } else {
            System.out.println("Error: Longitude invalid");
            Toast.makeText(this, "Enter a valid longitude from -180 to 80", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if (flag) {
            String address = "Generated Address";
            added_location = new LocationModel(99999, address, latitude, longitude);
            System.out.println(added_location.toString());
        }
    }

    // On click listener for a button to generate random address
    public void generate_random_address(View view) {
        System.out.println("Generate Random address called from the Add activity");

        // Generate a random latitude and longitude and display
        float latitude = (float) Math.random() * (180) - 90;
        float longitude = (float) Math.random() * (260) - 180;
        System.out.println("Random Latitude: " + latitude);
        System.out.println("Random Longitude: " + longitude);

        String address = "Randomly Generated Address";
        added_location = new LocationModel(99999, address, latitude, longitude);
        System.out.println(added_location.toString());

    }

    // On click listener to add the location and return to the main activity
    public void add_location_return(View view) {
        System.out.println("Add location and return method called from the Add activity");
        // Go back to the main activity
        finish();
    }
}