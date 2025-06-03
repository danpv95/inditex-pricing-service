package com.bcnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.bcnc.inditex_pricing_service")
public class InditexPricingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InditexPricingServiceApplication.class, args);
	}

}
