package com.example.restaurant_delivery.service.impl;

import com.example.restaurant_delivery.dto.TrackingUpdateDto;
import com.example.restaurant_delivery.model.LocationUpdate;
import com.example.restaurant_delivery.model.NearbyRequest;
import com.example.restaurant_delivery.model.NearbyResponse;
import com.example.restaurant_delivery.repository.LocationUpdateRepository;
import com.example.restaurant_delivery.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {

    private static final double EARTH_RADIUS_KM = 6371;

    @Autowired
    private LocationUpdateRepository locationRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public LocationUpdate processLocationUpdate(LocationUpdate update) {
        // Set current timestamp
        update.setTimestamp(Instant.now());

        // Save to database
        LocationUpdate savedUpdate = locationRepo.save(update);

        // Calculate ETA (simplified example)
        double etaMinutes = calculateEta(savedUpdate.getOrderId());

        // Broadcast to subscribed clients
        messagingTemplate.convertAndSend(
                "/topic/locations/" + savedUpdate.getOrderId(),
                new TrackingUpdateDto(
                        savedUpdate.getLongitude(),
                        savedUpdate.getLatitude(),
                        savedUpdate.getSpeed(),
                        savedUpdate.getHeading(),
                        etaMinutes
                )
        );

        return savedUpdate;
    }

    @Override
    public List<LocationUpdate> getLocationHistory(String orderId) {
        return locationRepo.findByOrderIdOrderByTimestampDesc(orderId);
    }

    @Override
    public double calculateEta(String orderId) {
        List<LocationUpdate> updates = locationRepo.findByOrderIdOrderByTimestampDesc(orderId);

        if (updates.size() < 2) {
            return -1; // Not enough data
        }

        LocationUpdate current = updates.get(0);
        LocationUpdate previous = updates.get(1);

        // Simplified distance calculation (Haversine formula)
        double distance = calculateDistance(
                previous.getLatitude(), previous.getLongitude(),
                current.getLatitude(), current.getLongitude()
        );

        double timeDiffHours = (current.getTimestamp().toEpochMilli() -
                previous.getTimestamp().toEpochMilli()) / 3600000.0;

        double speedKph = distance / timeDiffHours;
        double remainingDistance = 5.0; // Example - replace with actual destination distance
        return (remainingDistance / speedKph) * 60; // ETA in minutes
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS_KM * c;
    }

    @Override
    public List<NearbyResponse> findNearbyDrivers(NearbyRequest request) {
        List<LocationUpdate> allUpdates = locationRepo.findAll();

        List<NearbyResponse> nearbyDrivers = new ArrayList<>();

        for (LocationUpdate update : allUpdates) {
            double distance = calculateDistance(
                    request.getLatitude(), request.getLongitude(),
                    update.getLatitude(), update.getLongitude()
            );

            if (distance <= request.getDistanceKm()) {
                NearbyResponse response = new NearbyResponse();
                response.setDriverId(update.getDriverId());
                response.setTimestamp(update.getTimestamp().toString());
                response.setDistance(distance);

                nearbyDrivers.add(response);
            }
        }

        return nearbyDrivers;
    }
}
