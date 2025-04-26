package com.example.restaurant_delivery.service;


import com.example.restaurant_delivery.dto.DeliveryAssignmentDto;
import com.example.restaurant_delivery.dto.DeliveryStatusDto;
import com.example.restaurant_delivery.dto.DeliveryPersonnelDto;
import com.example.restaurant_delivery.model.Delivery;

import java.util.List;

public interface DeliveryService {
    Delivery assignDeliveryPersonnel(DeliveryAssignmentDto assignmentDto);
    DeliveryStatusDto updateDeliveryStatus(DeliveryStatusDto statusDto);
    DeliveryStatusDto getDeliveryStatus(String orderId);
    List<DeliveryPersonnelDto> getAllAvailableDeliveryPersonnel();
    DeliveryPersonnelDto getDeliveryPersonnelById(String id);
}
