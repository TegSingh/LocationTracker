package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AddActivity extends AppCompatActivity {

    // Make the location to be added a class variable
    LocationModel added_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // Reinitialize the added location variable every time the new activity is created
        added_location = null;
    }

    public void generate_address(View view) {

        System.out.println("Generate address called from the Add Activity");

        // Create a geocoder object
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        EditText inputLatitude = findViewById(R.id.inputLatitude);
        EditText inputLongitude = findViewById(R.id.inputLongitude);
        TextView textViewDisplayAddress = findViewById(R.id.textViewDisplayAddress);

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

        String address_final = "";

        if (flag) {

            try {
                // Returns a list of Address objects
                List<Address> generated_addresses = geocoder.getFromLocation((double) latitude, (double) longitude, 1);
                if (generated_addresses.size() == 0) {
                    Toast.makeText(this, "Geocoder could not generate address for input latitude and longitude. Try again with different values", Toast.LENGTH_SHORT).show();
                    address_final = "Geocoder cannot generate address";
                } else {

                    Address generated_address = generated_addresses.get(0); // Get the first element from the list of generated address

                    // Build a string for the final address
                    address_final = generated_address.getAddressLine(0).toString();

                    // Add postal code if available
                    if (generated_address.getPostalCode() != null) {
                        address_final += " ";
                        address_final += generated_address.getPostalCode().toString();
                    } else {
                        System.out.println("Could not load postal code");
                    }

                    System.out.println("Generated Address: " + address_final);
                    textViewDisplayAddress.setText(address_final);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            added_location = new LocationModel(99999, address_final, latitude, longitude);
            System.out.println(added_location.toString());
        }
    }

    // On click listener for a button to generate random address
    public void generate_random_address(View view) {

        System.out.println("Generate Random address called from the Add activity");

        // Create a geocoder object
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        TextView textViewDisplayAddress = findViewById(R.id.textViewDisplayAddress);

        // Generate a random latitude and longitude and display
        float latitude = (float) Math.random() * (180) - 90;
        float longitude = (float) Math.random() * (260) - 180;
        System.out.println("Random Latitude: " + latitude);
        System.out.println("Random Longitude: " + longitude);

        String address_final = "";

        try {
            List<Address> generated_addresses = geocoder.getFromLocation((double) latitude, (double) longitude, 1);

            if (generated_addresses.size() == 0) {
                Toast.makeText(this, "Geocoder could not generate address. Try again", Toast.LENGTH_SHORT).show();
                address_final = "Geocoder cannot generate address";
            } else {
                Address generated_address = generated_addresses.get(0); // Get the first element from the list of generated address

                // Build a string for the final address
                address_final = generated_address.getAddressLine(0).toString();

                if (generated_address.getPostalCode() != null) {
                    address_final += " ";
                    address_final += generated_address.getPostalCode().toString();
                }

                System.out.println("Generated Address: " + address_final);
                textViewDisplayAddress.setText(address_final);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        added_location = new LocationModel(99999, address_final, latitude, longitude);
        System.out.println(added_location.toString());
    }

    // Method to add 50 random locations
    public void add_random_locations() {
        System.out.println("Method to add 50 random locations to the database");
    }

    // On click listener to add the location and return to the main activity
    public void add_location_return(View view) {
        System.out.println("Add location and return method called from the Add activity");

        if (added_location == null) {
            System.out.println("No locations added");
            Intent add_location_return = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(add_location_return);

        } else {
            // Adding last generated location
            LocationCRUD locationsHelper = new LocationCRUD(getApplicationContext());
            boolean result = locationsHelper.insertLocation(added_location);
            if (result) {
                System.out.println("Location added successfully");
            } else {
                System.out.println("Could not add location");
            }

            Intent add_location_return = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(add_location_return);
        }

        // Go back to the main activity
        finish();
    }
}