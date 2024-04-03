package org.listingbotv1.listingbot.listing;

import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@RestController // Specifies this is the Controller (In our case its also the API Layer)
@RequestMapping(path = "") // *Input from View
public class ListingController {
	
	
	private final ListingService listingservice; 
	
	@Autowired // This annotation basically tells our program to instantiate listingservice so it can be passed. We need to specify the @Component or @Service to be instantiated
	public ListingController(ListingService listingservice) { //**Input from Model (Service)
		this.listingservice = listingservice;
	}
	
	
	@GetMapping("/listings") // *Output to View
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


	@GetMapping("/testurl")
	public void scrapeListingKijiji() throws IOException{
		String exurl = "https://www.kijiji.ca/v-appartement-condo/ville-de-montreal/condo-appartement-3-1-2-a-louer-ville-marie-tout-inclus/1688943578";
		String exurl1 = "https://www.kijiji.ca/v-appartement-condo/ville-de-montreal/libre-immediatement-4-1-2-renove-1600-mois-metro-papineau/1689691312";
		String exurl2 = "https://www.kijiji.ca/v-appartement-condo/ville-de-montreal/condo-neuf-1cc-a-louer-a-ville-marie-pres-du-vieux-port/1683946955";

		listingservice.scrapeListingKijiji(exurl);
		//listingservice.scrapeListingKijiji(exurl1);
		//listingservice.scrapeListingKijiji(exurl2);



	}

	@GetMapping("/testmainpage")
	public void scrapeMainPageKijiji() throws IOException {
		String url = "https://www.kijiji.ca/b-apartments-condos/ville-de-montreal/c37l1700281?sort=dateDesc&radius=2.0&address=Montreal%2C+QC+H2X+2Y4&ll=45.514161%2C-73.57067";
		HashSet<String> links = listingservice.scrapeMainPageKijiji(url);
		int count = 0;
		for(String link : links){
			System.out.println(link);
			count++;
		}
		System.out.println(count);
	}

	@GetMapping("/testfilldatabase")
	public void fill_database_kijiji()throws IOException{
		String url = "https://www.kijiji.ca/b-apartments-condos/ville-de-montreal/c37l1700281?sort=dateDesc&radius=2.0&address=Montreal%2C+QC+H2X+2Y4&ll=45.514161%2C-73.57067";
		listingservice.fill_database_kijiji(url);
	}
}

