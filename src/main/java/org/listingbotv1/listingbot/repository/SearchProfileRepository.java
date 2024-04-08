package org.listingbotv1.listingbot.repository;

import org.listingbotv1.listingbot.model.SearchProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchProfileRepository extends JpaRepository<SearchProfile, Long> {
    // Additional query methods if needed
}
