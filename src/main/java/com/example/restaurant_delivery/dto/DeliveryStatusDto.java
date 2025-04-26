package com.example.restaurant_delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusDto {
    private String orderId;
    private String deliveryPersonnelId;
    private String status; // "ASSIGNED", "PICKED_UP", "ON_THE_WAY", "DELIVERED"
    private double currentLatitude;
    private double currentLongitude;
    private String estimatedTime;
}
