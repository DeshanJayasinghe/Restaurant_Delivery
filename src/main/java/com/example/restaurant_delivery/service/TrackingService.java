package com.example.restaurant_delivery.service;


import com.example.restaurant_delivery.model.LocationUpdate;
import com.example.restaurant_delivery.model.NearbyRequest;
import com.example.restaurant_delivery.model.NearbyResponse;

import java.util.List;

public interface TrackingService {

    LocationUpdate processLocationUpdate(LocationUpdate update); // <-- changed

    List<LocationUpdate> getLocationHistory(String orderId);

    double calculateEta(String orderId);

    List<NearbyResponse> findNearbyDrivers(NearbyRequest request);
}