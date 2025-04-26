package com.example.restaurant_delivery.controller;

import com.example.restaurant_delivery.model.LocationUpdate;
import com.example.restaurant_delivery.model.NearbyRequest;
import com.example.restaurant_delivery.model.NearbyResponse;
import com.example.restaurant_delivery.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    // WebSocket Endpoint
    @MessageMapping("/location.update")
    public void handleLocationUpdate(@Payload LocationUpdate update) {
        trackingService.processLocationUpdate(update);
    }

    // REST Endpoint - Submit Location Update via HTTP POST
    @PostMapping("/update")
    public LocationUpdate submitLocationUpdate(@RequestBody LocationUpdate update) {
        return trackingService.processLocationUpdate(update);
    }

    // REST Endpoint - Get location history by orderId
    @GetMapping("/history/{orderId}")
    public List<LocationUpdate> getLocationHistory(@PathVariable String orderId) {
        return trackingService.getLocationHistory(orderId);
    }

    // REST Endpoint - Find drivers near a location
    @PostMapping("/near")
    public List<NearbyResponse> findNearbyDrivers(@RequestBody NearbyRequest request) {
        return trackingService.findNearbyDrivers(request);
    }
}
