package com.example.restaurant_delivery.model;

public class NearbyResponse {
    private String driverId;
    private String timestamp;
    private double distance;

    // Getters and Setters
    public String getDriverId() {
        return driverId;
    }
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
