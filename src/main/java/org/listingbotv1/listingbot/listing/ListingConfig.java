package org.listingbotv1.listingbot.listing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class ListingConfig {
	@Profile({"dev","prod"})
	@Bean
	CommandLineRunner commandLineRunner(ListingRepository repository) {
		return args -> {
			//Listing kjiji_listing = new Listing();
			//Listing craigslist_listing = new Listing();
			//repository.saveAll(List.of(kjiji_listing,craigslist_listing));
		};
	}
	

}
