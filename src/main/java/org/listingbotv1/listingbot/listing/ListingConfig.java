package org.listingbotv1.listingbot.listing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ListingConfig {
	@Bean
	CommandLineRunner commandLineRunner(ListingRepository repository) {
		return args -> {
			
			Listing kjiji_listing = new Listing("kjijilink.com",5.0,"5 Bedroom kijiji!",2400);
			Listing craigslist_listing = new Listing("craigslistlink.com",3.0,"3 bedroom craigslist",3000);
			
			repository.saveAll(List.of(kjiji_listing,craigslist_listing));
		};
	}
	

}
