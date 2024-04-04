package org.listingbotv1.listingbot.repository;

import org.listingbotv1.listingbot.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ListingRepository extends JpaRepository<Listing,Long>{
	Optional<Listing> findListingByLink(String link);
}
