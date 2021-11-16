package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void generate_address(View view) {
        System.out.println("Generate address called from the Add Activity");
    }

    // On click listener for a button to generate random address
    public void generate_random_address(View view) {
        System.out.println("Generate Random address called from the Add activity");
    }

    // On click listener to add the location and return to the main activity
    public void add_location_return(View view) {
        System.out.println("Add location and return method called from the Add activity");
        // Go back to the main activity
        finish();
    }
}