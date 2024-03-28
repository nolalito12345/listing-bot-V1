package org.listingbotv1.listingbot.listing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Specifies this is the Controller (In our case its also the API Layer)
@RequestMapping(path = "api/v1/listing") // *Input from View
public class ListingController {
	
	
	private final ListingService listingservice; 
	
	@Autowired // This annotation basically tells our program to instantiate listingservice so it can be passed. We need to specify the @Component or @Service to be instantiated
	public ListingController(ListingService listingservice) { //**Input from Model (Service)
		this.listingservice = listingservice;
	}
	
	
	@GetMapping // *Output to View
	public List<Listing> getListings() { // GET
		return listingservice.getListings();
	}
	
	@PostMapping 
	public void addNewListing(@RequestBody Listing listing) { //POST
		listingservice.addNewListing(listing);
	}
	
	@DeleteMapping(path = "{listingID}")
	public void deleteListing(@PathVariable("listingID") Long listingID ) { // DELETE
		
		listingservice.deleteListing(listingID);
		
	}
	
	@PutMapping(path = "{listingID}")
	public void updateListing(@PathVariable("listingID") Long listingID,@RequestParam(required=false) String link,@RequestParam(required=false) Double price) { // PUT
	listingservice.updateListing(listingID,link,price);
		
		
		System.out.println("Successful Put!");
	}
	
}

