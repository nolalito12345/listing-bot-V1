package org.listingbotv1.listingbot.service;


import org.listingbotv1.listingbot.model.Listing;
import org.listingbotv1.listingbot.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service // Works with @Autowired to specify that this is the Service that needs to be instantiated.
public class ListingService {

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




}