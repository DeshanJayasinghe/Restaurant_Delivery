package com.example.restaurant_delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveTrackingDto {
    private String orderId;
    private String driverId;
    private double longitude;
    private double latitude;
    private double speed;
    private double heading;
    private Instant timestamp;
    private String eta;
}