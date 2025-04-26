package com.example.restaurant_delivery.repository;


import com.example.restaurant_delivery.model.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {
    List<Delivery> findByIsAvailableTrue();
    Delivery findByCurrentOrderId(String orderId);
}