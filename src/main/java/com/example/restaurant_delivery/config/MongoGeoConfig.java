package com.example.restaurant_delivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
//@EnableMongoRepositories(basePackages = "com.example.restaurant_delivery.repository")
public class MongoGeoConfig {
}