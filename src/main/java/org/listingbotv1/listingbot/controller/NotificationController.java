package org.listingbotv1.listingbot.controller;

import org.listingbotv1.listingbot.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {
    NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/sendSMS/{to}")
    public ResponseEntity<Void> sendSMS(@PathVariable String to,@RequestBody String body) {
        notificationService.sendSMS(to, body);
        return ResponseEntity.ok().build();
    }



    // Other methods for notification management
}
