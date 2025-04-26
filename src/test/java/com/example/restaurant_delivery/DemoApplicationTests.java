package com.example.restaurant_delivery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Use a test profile
class DemoApplicationTests {

	@Test
	void contextLoads() {
		// This will verify if your application context loads
	}
}

