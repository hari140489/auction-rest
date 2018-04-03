package com.prokarma.opa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineProductAuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineProductAuctionApplication.class, args);
	}

}
