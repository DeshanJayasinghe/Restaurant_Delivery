package com.example.restaurant_delivery.service.impl;


import com.example.restaurant_delivery.dto.DeliveryAssignmentDto;
import com.example.restaurant_delivery.dto.DeliveryStatusDto;
import com.example.restaurant_delivery.dto.DeliveryPersonnelDto;
import com.example.restaurant_delivery.exception.DeliveryPersonnelNotAvailableException;
import com.example.restaurant_delivery.exception.OrderAlreadyAssignedException;
import com.example.restaurant_delivery.model.Delivery;
import com.example.restaurant_delivery.repository.DeliveryRepository;
import com.example.restaurant_delivery.service.DeliveryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, ModelMapper modelMapper) {
        this.deliveryRepository = deliveryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Delivery assignDeliveryPersonnel(DeliveryAssignmentDto assignmentDto) {
        // Check if order is already assigned
        if (deliveryRepository.findByCurrentOrderId(assignmentDto.getOrderId()) != null) {
            throw new OrderAlreadyAssignedException("Order is already assigned to a delivery personnel");
        }

        // Find available delivery personnel near the restaurant
        List<Delivery> availablePersonnel = deliveryRepository.findByIsAvailableTrue();

        if (availablePersonnel.isEmpty()) {
            throw new DeliveryPersonnelNotAvailableException("No delivery personnel available at the moment");
        }

        // Simple distance calculation (in a real app, use proper geospatial queries)
        Delivery nearestPersonnel = findNearestDeliveryPersonnel(
                availablePersonnel,
                assignmentDto.getRestaurantLatitude(),
                assignmentDto.getRestaurantLongitude()
        );

        // Assign the order
        nearestPersonnel.setCurrentOrderId(assignmentDto.getOrderId());
        nearestPersonnel.setAvailable(false);
        nearestPersonnel.setCurrentLatitude(assignmentDto.getRestaurantLatitude());
        nearestPersonnel.setCurrentLongitude(assignmentDto.getRestaurantLongitude());

        return deliveryRepository.save(nearestPersonnel);
    }

    @Override
    public DeliveryStatusDto updateDeliveryStatus(DeliveryStatusDto statusDto) {
        Delivery delivery = deliveryRepository.findByCurrentOrderId(statusDto.getOrderId());
        if (delivery == null) {
            throw new RuntimeException("Delivery not found for order: " + statusDto.getOrderId());
        }

        // Update delivery status
        delivery.setCurrentLatitude(statusDto.getCurrentLatitude());
        delivery.setCurrentLongitude(statusDto.getCurrentLongitude());

        // If status is DELIVERED, mark as available again
        if ("DELIVERED".equals(statusDto.getStatus())) {
            delivery.setAvailable(true);
            delivery.setCurrentOrderId(null);
        }

        deliveryRepository.save(delivery);
        return statusDto;
    }

    @Override
    public DeliveryStatusDto getDeliveryStatus(String orderId) {
        Delivery delivery = deliveryRepository.findByCurrentOrderId(orderId);
        if (delivery == null) {
            throw new RuntimeException("Delivery not found for order: " + orderId);
        }

        DeliveryStatusDto statusDto = new DeliveryStatusDto();
        statusDto.setOrderId(orderId);
        statusDto.setDeliveryPersonnelId(delivery.getId());
        statusDto.setCurrentLatitude(delivery.getCurrentLatitude());
        statusDto.setCurrentLongitude(delivery.getCurrentLongitude());

        // Determine status based on current state
        if (delivery.getCurrentOrderId() == null) {
            statusDto.setStatus("COMPLETED");
        } else {
            // This is simplified - in a real app you'd have more detailed status tracking
            statusDto.setStatus("IN_PROGRESS");
        }

        return statusDto;
    }

    @Override
    public List<DeliveryPersonnelDto> getAllAvailableDeliveryPersonnel() {
        return deliveryRepository.findByIsAvailableTrue()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryPersonnelDto getDeliveryPersonnelById(String id) {
        return deliveryRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Delivery personnel not found"));
    }

    private DeliveryPersonnelDto convertToDto(Delivery delivery) {
        return modelMapper.map(delivery, DeliveryPersonnelDto.class);
    }

    private Delivery findNearestDeliveryPersonnel(List<Delivery> personnel, double lat, double lng) {
        // This is a simplified version - in production, use MongoDB's geospatial queries
        Delivery nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Delivery dp : personnel) {
            double distance = calculateDistance(lat, lng, dp.getCurrentLatitude(), dp.getCurrentLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = dp;
            }
        }

        return nearest;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula to calculate distance between two points on Earth
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}