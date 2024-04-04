package org.listingbotv1.listingbot.service;

import org.listingbotv1.listingbot.repository.UserRepository;
import org.listingbotv1.listingbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){this.userRepository = userRepository;}

    public void createUser(User user) {
        userRepository.save(user);
    }

    // Other methods for user management
}