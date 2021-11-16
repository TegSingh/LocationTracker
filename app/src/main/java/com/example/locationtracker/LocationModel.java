package com.example.locationtracker;

public class LocationModel {

    // Declare the variables that are part of the model/table
    private int id;
    private String address;
    private float latitude;
    private float longitude;

    // Define the constructor
    public LocationModel(int id, String address, Float latitude, Float longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    // Create a toString method
    @Override
    public String toString() {
        return "Location ID: " + id +
                ", Address=" + address +
                ", Latitude=" + latitude +
                ", Longitude=" + longitude;
    }
}
