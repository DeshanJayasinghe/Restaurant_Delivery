package com.example.restaurant_delivery.controller;


import com.example.restaurant_delivery.dto.DeliveryAssignmentDto;
import com.example.restaurant_delivery.dto.DeliveryStatusDto;
import com.example.restaurant_delivery.dto.DeliveryPersonnelDto;
import com.example.restaurant_delivery.model.Delivery;
import com.example.restaurant_delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/assign")
    public ResponseEntity<Delivery> assignDeliveryPersonnel(@RequestBody DeliveryAssignmentDto assignmentDto) {
        Delivery assignedDelivery = deliveryService.assignDeliveryPersonnel(assignmentDto);
        return ResponseEntity.ok(assignedDelivery);
    }

    @PostMapping("/status")
    public ResponseEntity<DeliveryStatusDto> updateDeliveryStatus(@RequestBody DeliveryStatusDto statusDto) {
        DeliveryStatusDto updatedStatus = deliveryService.updateDeliveryStatus(statusDto);
        return ResponseEntity.ok(updatedStatus);
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<DeliveryStatusDto> getDeliveryStatus(@PathVariable String orderId) {
        DeliveryStatusDto status = deliveryService.getDeliveryStatus(orderId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DeliveryPersonnelDto>> getAvailableDeliveryPersonnel() {
        List<DeliveryPersonnelDto> availablePersonnel = deliveryService.getAllAvailableDeliveryPersonnel();
        return ResponseEntity.ok(availablePersonnel);
    }

    @GetMapping("/personnel/{id}")
    public ResponseEntity<DeliveryPersonnelDto> getDeliveryPersonnel(@PathVariable String id) {
        DeliveryPersonnelDto personnel = deliveryService.getDeliveryPersonnelById(id);
        return ResponseEntity.ok(personnel);
    }
}
