package org.listingbotv1.listingbot.config;

import org.listingbotv1.listingbot.repository.ListingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ListingConfig {
	@Profile({"dev","prod"})
	@Bean
	CommandLineRunner commandLineRunner(ListingRepository repository) {
		return args -> {

		};
	}
	

}
