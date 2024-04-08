package org.listingbotv1.listingbot.controller;

import org.listingbotv1.listingbot.service.ScraperService;
import org.listingbotv1.listingbot.service.TaskQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ScraperController {


    @Autowired
    private final ScraperService scraperService;

    @Autowired
    private TaskQueueService taskQueueService;
    @Autowired
    // This annotation basically tells our program to instantiate listingservice so it can be passed. We need to specify the @Component or @Service to be instantiated
    public ScraperController(ScraperService scraperService) { //**Input from Model (Service)
        this.scraperService = scraperService;
    }

    @PostMapping("/start-scraper")// Ensure that the return value is serialized to the response body
    public ResponseEntity<String> startScraper(@RequestBody String url) {
        // Enqueue web scraper task
        taskQueueService.enqueueScraperTask(url);

        // Return a response indicating success
        return ResponseEntity.ok("Post request received!");
    }
}
