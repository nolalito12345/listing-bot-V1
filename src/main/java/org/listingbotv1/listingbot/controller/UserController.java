package org.listingbotv1.listingbot.controller;

import org.listingbotv1.listingbot.service.UserService;
import org.listingbotv1.listingbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){this.userService = userService;}


    @PostMapping
    public void createUser(@RequestBody User user) {
         userService.createUser(user);
    }

    // Other CRUD operations endpoints
}