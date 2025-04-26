package com.example.restaurant_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.restaurant_delivery")
@EnableMongoRepositories
public class DeliveryApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeliveryApplication.class, args);
	}
}
