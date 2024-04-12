package org.listingbotv1.listingbot.service;

import org.listingbotv1.listingbot.model.Listing;
import org.listingbotv1.listingbot.model.SearchProfile;
import org.listingbotv1.listingbot.repository.SearchProfileRepository;
import org.listingbotv1.listingbot.repository.UserRepository;
import org.listingbotv1.listingbot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SearchProfileRepository searchProfileRepository;

    private final NotificationService notificationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public UserService(UserRepository userRepository, SearchProfileRepository searchProfileRepository, NotificationService notificationService){this.userRepository = userRepository;
        this.searchProfileRepository = searchProfileRepository;
        this.notificationService = notificationService;}

    public void createUser(User user) {
        userRepository.save(user);
    }

    // Other methods for user management
    @Transactional
    public void addSearchProfileToUser(Long userId, SearchProfile profile) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        profile.setUser(user);
        user.getSearchProfiles().add(profile);
        userRepository.save(user);
    }


    public Optional<SearchProfile> findSearchProfileById(Long id) {
        return searchProfileRepository.findById(id);
    }

    public List<SearchProfile> findAllSearchProfiles() {
        return searchProfileRepository.findAll();
    }

    @Transactional
    public void updateSearchProfile(SearchProfile updatedSearchProfile) {
        // Ensure the search profile exists before updating
        searchProfileRepository.findById(updatedSearchProfile.getId())
                .orElseThrow(() -> new RuntimeException("SearchProfile not found"));
        searchProfileRepository.save(updatedSearchProfile);
    }

    @Transactional
    public void deleteSearchProfile(Long id) {
        SearchProfile searchProfile = searchProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SearchProfile not found"));
        searchProfileRepository.delete(searchProfile);
    }

    public Set<SearchProfile> getSearchProfilesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getSearchProfiles();
    }


    @Transactional
    @Async
    public void notifyUsersOfMatchingListings(HashSet<Listing> listings) {
        if(listings.isEmpty()){
            return;
        }

        LOGGER.info("Notifying users of matching listings");
        List<User> users = userRepository.findAll();

        for (User user : users) {
            Set<SearchProfile> searchProfiles = user.getSearchProfiles();

            for (SearchProfile searchProfile : searchProfiles) {
                for (Listing listing : listings) {
                    if (searchProfile.equals(listing)) {
                        String notif_str = "New " + listing.gethouse_size() + " for " + listing.getPrice() + "\n Link: " + listing.getLink();
                        String phone_number = String.valueOf(user.getPhoneNumber());
                        System.out.println("Notification sending to: " + phone_number);
                        notificationService.sendSMS(phone_number, notif_str);

                    }
                }
            }
        }
        LOGGER.info("Finished notifying users of matching listings");
    }

}