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
        String from = "+14245431565";
        notificationService.sendSMS(to, from, body);
        return ResponseEntity.ok().build();
    }



    // Other methods for notification management
}
