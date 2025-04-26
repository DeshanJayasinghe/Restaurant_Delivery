package com.example.restaurant_delivery.repository;

import com.example.restaurant_delivery.model.LocationUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.List;

public interface LocationUpdateRepository extends MongoRepository<LocationUpdate, String> {
    List<LocationUpdate> findByOrderIdOrderByTimestampDesc(String orderId);

    @Query("{'orderId': ?0, 'timestamp': {$gt: ?1}}")
    List<LocationUpdate> findRecentUpdates(String orderId, Instant since);
}