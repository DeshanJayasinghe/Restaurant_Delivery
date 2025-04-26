package com.example.restaurant_delivery.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "delivery_personnel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery implements UserEntity {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private boolean isApproved;

    @Column(nullable = false)
    private String approvalStatus;

    // New fields for delivery management
    private double currentLatitude;
    private double currentLongitude;
    private boolean isAvailable;
    private String currentOrderId; // null if no active delivery
}