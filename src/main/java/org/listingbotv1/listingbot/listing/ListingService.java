package org.listingbotv1.listingbot.listing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service // Works with @Autowired to specify that this is the Service that needs to be instantiated.
public class ListingService { // Business layer

	private final ListingRepository listingrepository;

	@Autowired
	public ListingService(ListingRepository listingrepository) {// input from ListingRepository
		this.listingrepository = listingrepository;
	}

	public List<Listing> getListings() { //*Output to Controller // GET
		return listingrepository.findAll();
	}

	public void addNewListing(Listing listing) { // POST

		listingrepository.save(listing);
	}

	public void deleteListing(Long id) { // DELETE
		boolean exists = listingrepository.existsById(id);
		if (!exists) {
			throw new IllegalStateException("Listing with ID: " + id + " does not exist!");
		}

		listingrepository.deleteById(id);
	}


	@Transactional
	public void updateListing(Long listingID, String link, Double price) {
		Listing listing = listingrepository.findById(listingID).orElseThrow(() -> new IllegalStateException("A listing with ID: " + listingID + " does not exist!"));


		if (link != null && link.length() > 0 && !(link.equals(listing.getLink()))) {
			listing.setLink(link);
			System.out.println("updated link");
		}


		if (price != null && price > 0 && price != listing.getPrice()) {
			listing.setPrice(price);
			System.out.println("updated price");
		}

	}

	// Takes a url and scrapes info from the listing
	// NEEDS BETTER ERROR CHECKING FOR SCRAPING INFO
	public Listing scrapeListingKijiji(String url)throws IOException{
		Document doc = Jsoup.connect(url).get();
		Element doc_element = doc.getElementById("vip-body"); // Body of document

		String link = url; //Item Url
		System.out.println("URL: " + url);

		Element price_element = doc_element.select("div.priceWrapper-3915768379").first();
		Element price_element_2 = price_element.firstElementChild();
		Double price = 0.0;
		try {
			price = Double.parseDouble(price_element_2.attr("content")); //Item Price
		} catch(NumberFormatException nfe) {
			System.out.println("Invalid Price: " + url);
		}
		System.out.println("Price: " + price);

		Element house_size_and_bathroom_element = doc_element.select("div.titleAttributes-183069789").first();
		Element house_size_element = house_size_and_bathroom_element.child(1);
		String house_size_string = house_size_element.getElementsByTag("span").text();
		Double house_size = parseFraction(house_size_string);
		System.out.println("House Size: " + house_size);

		Element address_element = doc_element.getElementsByClass("address-2094065249").first();
		String address = address_element.text();
		System.out.println("Address: " + address);

		Elements lease_term_elements = doc_element.select("li.twoLinesAttribute-633292638");
		String lease_term = null;
		try {
			lease_term = lease_term_elements.select("span").first().text();
		} catch(Exception e){
			lease_term = "Not Found";
		}
		System.out.println("Lease Start: " + lease_term);


		// Get the <time> element
		Element timeElement = doc_element.select("time").first();

		// Extract the value of the datetime attribute
		String datetimeAttributeValue = timeElement.attr("datetime");

		ZonedDateTime datetime = ZonedDateTime.parse(datetimeAttributeValue).withZoneSameInstant(ZoneId.of("UTC"));

		String datetimestr = datetime + "";

		Listing templisting = new Listing(url,house_size,lease_term,address,price,datetimestr);

		return templisting;
	}

	//Method that extracts formats house size field from kijiji listing
	private static double parseFraction(String text) throws NumberFormatException{
		String fractionPart = text.replaceAll("[^0-9 /]+", "").trim(); // Extract fraction part
		String[] parts = fractionPart.split(" ");
		if (parts.length == 2) {

			double whole = Double.parseDouble(parts[0]);
			String[] fractionParts = parts[1].split("/");
			if (fractionParts.length == 2) {
				double numerator = Double.parseDouble(fractionParts[0]);
				double denominator = Double.parseDouble(fractionParts[1]);
				return whole + (numerator / denominator);
			}
		} else if(parts.length>2){
			return 1.5;
		}

		return Double.NaN; // Return NaN if parsing fails
	}

	// Gets all the listing links on the page
	public HashSet<String> scrapeMainPageKijiji(String url) throws IOException{
		HashSet<String> listing_links = new HashSet<>();
		Document doc = Jsoup.connect(url).get();

		Elements links = doc.select("ul[data-testid=srp-search-list]");
		Element link1 = links.last();



		links = link1.select("a[data-testid=listing-link]");
		for(Element link : links){

			listing_links.add("https://www.kijiji.ca/"+link.attr("href"));
		}
		return listing_links;

	}

	public HashSet<String> check_kijiji_listing_duplicates(HashSet<String> links){
		HashSet<String> dupes = new HashSet<>();
		for(String link : links){
			Optional<Listing> listingOptional = listingrepository.findListingByLink(link);
			if(listingOptional.isPresent()){
				dupes.add(link);
			}
		}
		return dupes;
	}

	public void fill_database_kijiji(String url) throws IOException{
		HashSet<String> listing_links = scrapeMainPageKijiji(url);
		HashSet<String> duplicate_links = check_kijiji_listing_duplicates(listing_links);
		listing_links.removeAll(duplicate_links);

		if(listing_links.isEmpty()){
			System.out.println("No new listings!");
			return;
		}
		for(String link : listing_links){
			Listing exlisting;
			try {
				if ((exlisting = scrapeListingKijiji(link)) != null) {
					addNewListing(exlisting);
				}
			}catch(Exception e){
				System.out.println("Error with listing: " + link);
			}
		}

	}


}