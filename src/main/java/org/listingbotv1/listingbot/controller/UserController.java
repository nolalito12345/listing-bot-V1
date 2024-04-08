package org.listingbotv1.listingbot.controller;

import org.listingbotv1.listingbot.model.SearchProfile;
import org.listingbotv1.listingbot.service.UserService;
import org.listingbotv1.listingbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){this.userService = userService;}


    @PostMapping("/createuser")
    public User createUser(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

    @PostMapping("/{userId}/addsearchprofile")
    public void addSearchProfileToUser(@PathVariable Long userId, @RequestBody SearchProfile profile) {
        userService.addSearchProfileToUser(userId, profile);
    }

    @GetMapping("/{id}")
    public SearchProfile getSearchProfile(@PathVariable Long id) {
        return userService.findSearchProfileById(id)
                .orElseThrow(() -> new RuntimeException("SearchProfile not found"));
    }

    @GetMapping("/{userId}/searchprofiles")
    public Set<SearchProfile> getSearchProfilesByUserId(@PathVariable Long userId) {
        return userService.getSearchProfilesByUserId(userId);
    }




}