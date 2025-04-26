package com.example.restaurant_delivery.model;

public class NearbyRequest {
    private double longitude;
    private double latitude;
    private double distanceKm;

    // Getters and Setters
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getDistanceKm() {
        return distanceKm;
    }
    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }
}
