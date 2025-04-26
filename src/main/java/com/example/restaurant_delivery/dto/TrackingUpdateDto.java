package com.example.restaurant_delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingUpdateDto {
    private double longitude;
    private double latitude;
    private double speed; // km/h
    private double heading; // degrees
    private double etaMinutes; // estimated arrival time
}