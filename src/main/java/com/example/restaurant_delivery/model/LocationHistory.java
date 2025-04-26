package com.example.restaurant_delivery.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.Instant;
import java.util.Objects;

@Document(collection = "location_history")
public class LocationHistory {
    @Id
    private String id;

    private String orderId;
    private String driverId;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    private Instant timestamp;

    // Constructors
    public LocationHistory() {
    }

    public LocationHistory(String orderId, String driverId, GeoJsonPoint location) {
        this.orderId = orderId;
        this.driverId = driverId;
        this.location = location;
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

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationHistory that = (LocationHistory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(driverId, that.driverId) &&
                Objects.equals(location, that.location) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, driverId, location, timestamp);
    }

    // toString
    @Override
    public String toString() {
        return "LocationHistory{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", driverId='" + driverId + '\'' +
                ", location=" + location +
                ", timestamp=" + timestamp +
                '}';
    }
}
