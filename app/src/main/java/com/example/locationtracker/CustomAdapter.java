package com.example.locationtracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Create a custom adapter to work between recycler view layout and linear layout
public class CustomAdapter extends RecyclerView.Adapter {

    LocationCRUD locationsHelper;
    List<LocationModel> locations;
    Context context;

    // Implement the constructor for the Adapter class
    public CustomAdapter(List<LocationModel> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }

    // This method inflates the location_list_item.xml layout
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View locationListLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(locationListLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewHolder listViewHolder = (ViewHolder) holder;

        // Set the attributes and onClickListener
        listViewHolder.textViewLocation.setText(locations.get(position).getAddress());

        // Convert Float to String
        Float latitude_float = locations.get(position).getLatitude();
        Float longitude_float = locations.get(position).getLongitude();
        listViewHolder.textViewLatitude.setText("Latitude: " + latitude_float.toString());
        listViewHolder.textViewLongitude.setText("Longitude: " + longitude_float.toString());

        listViewHolder.updateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Update Location button clicked for location: " + locations.get(position).toString());

                // Move the control to another activity which inputes the values required to update
                Intent updateIntent = new Intent(context, UpdateActivity.class);
                ArrayList<String> location_arraylist = new ArrayList<>();

                // Add information about the location to the intent
                Integer id = locations.get(position).getId();
                location_arraylist.add(id.toString());
                location_arraylist.add(locations.get(position).getAddress());
                Float latitude = locations.get(position).getLatitude();
                location_arraylist.add(latitude.toString());
                Float longitude = locations.get(position).getLongitude();
                location_arraylist.add(longitude.toString());
                updateIntent.putExtra("location", location_arraylist);

                context.startActivity(updateIntent);

                System.out.println("Back to custom adapter post update");

            }
        });

        listViewHolder.deleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationsHelper = new LocationCRUD(v.getContext());

                System.out.println("Delete Location button clicked for location: " + locations.get(position));

                // Display an alert dialog box
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage("Are you sure you wish to delete?");

                // Set a button and listener for positive response
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle yes clicked from alert box
                                System.out.println("Yes clicked in the alert box");
                                boolean result = locationsHelper.deleteLocation(locations.get(position).getId());
                                if (result) {
                                    System.out.println("Adapter: Row deleted successfully");
                                    Toast.makeText(v.getContext(), "Deletion successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("Could not find row to delete");
                                    Toast.makeText(v.getContext(), "Could not find row to delete", Toast.LENGTH_SHORT).show();
                                }

                                // Restart the activity upon making any changes
                                Intent deleteIntent = new Intent(context, MainActivity.class);
                                context.startActivity(deleteIntent);
                            }
                        });
                // Set a button for negative response
                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("Delete not confirmed");
                                Toast.makeText(v.getContext(), "Delete not confirmed", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Create the dialog box with the provided attributes and listeners and show
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    // Create a view holder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLocation;
        TextView textViewLatitude;
        TextView textViewLongitude;

        Button updateLocation;
        Button deleteLocation;

        public ViewHolder(@NonNull View location_list_item) {
            super(location_list_item);
            textViewLocation = location_list_item.findViewById(R.id.textViewLocation);
            textViewLatitude = location_list_item.findViewById(R.id.textViewLatitude);
            textViewLongitude = location_list_item.findViewById(R.id.textViewLongitude);
            updateLocation = location_list_item.findViewById(R.id.updateLocation);
            deleteLocation = location_list_item.findViewById(R.id.deleteLocation);

        }
    }
}
