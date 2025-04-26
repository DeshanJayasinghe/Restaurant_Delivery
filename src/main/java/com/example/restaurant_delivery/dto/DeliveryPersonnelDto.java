package com.example.restaurant_delivery.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPersonnelDto {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private String vehicleNumber;
    private boolean isAvailable;
    private double currentLatitude;
    private double currentLongitude;
}

