package com.example.restaurant_delivery.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "location_updates")
public class LocationUpdate {
    @Id
    private String id;
    private String orderId;
    private String driverId;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private double[] location; // [longitude, latitude]

    private Instant timestamp;
    private double speed; // km/h
    private double heading; // degrees

    // Constructors
    public LocationUpdate() {
    }

    public LocationUpdate(String orderId, String driverId, double longitude, double latitude,
                          double speed, double heading) {
        this.orderId = orderId;
        this.driverId = driverId;
        this.location = new double[]{longitude, latitude};
        this.speed = speed;
        this.heading = heading;
        this.timestamp = Instant.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public double getLongitude() {
        return location[0];
    }

    public double getLatitude() {
        return location[1];
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }
}