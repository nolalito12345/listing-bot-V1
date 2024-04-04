package org.listingbotv1.listingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ListingBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListingBotApplication.class, args);
	}

}
