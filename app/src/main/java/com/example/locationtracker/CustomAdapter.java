package com.example.locationtracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
                boolean result = locationsHelper.deleteLocation(locations.get(position).getId());
                if (result) {
                    System.out.println("Adapter: Row deleted successfully");
                } else {
                    System.out.println("Could not find row to delete");
                }

                // Restart the activity upon making any changes
                Intent deleteIntent = new Intent(context, MainActivity.class);
                context.startActivity(deleteIntent);
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
        Button updateLocation;
        Button deleteLocation;

        public ViewHolder(@NonNull View location_list_item) {
            super(location_list_item);
            textViewLocation = location_list_item.findViewById(R.id.textViewLocation);
            updateLocation = location_list_item.findViewById(R.id.updateLocation);
            deleteLocation = location_list_item.findViewById(R.id.deleteLocation);

        }
    }
}
