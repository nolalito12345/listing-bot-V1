package org.listingbotv1.listingbot.repository;

import org.listingbotv1.listingbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Add custom methods if needed
}