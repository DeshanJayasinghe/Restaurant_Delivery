package com.example.restaurant_delivery.config;


import com.example.restaurant_delivery.model.Delivery;
import com.example.restaurant_delivery.repository.DeliveryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(DeliveryRepository repository) {
        return args -> {
            repository.deleteAll();

            repository.save(new Delivery(
                    null, "driver1", "password", "driver1@email.com",
                    "0771234567", "ABC-1234", true, "APPROVED",
                    6.9271, 79.8612, true, null
            ));

            repository.save(new Delivery(
                    null, "driver2", "password", "driver2@email.com",
                    "0772345678", "DEF-5678", true, "APPROVED",
                    6.9200, 79.8600, true, null
            ));

            repository.save(new Delivery(
                    null, "driver3", "password", "driver3@email.com",
                    "0773456789", "GHI-9012", true, "APPROVED",
                    6.9300, 79.8700, false, "order123"
            ));
        };
    }
}
