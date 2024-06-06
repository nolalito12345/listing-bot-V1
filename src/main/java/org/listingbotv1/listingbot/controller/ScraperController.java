package org.listingbotv1.listingbot.controller;

import org.listingbotv1.listingbot.service.ScraperService;
import org.listingbotv1.listingbot.service.TaskQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping
public class ScraperController {


    @Autowired
    private final ScraperService scraperService;

    @Autowired
    private TaskQueueService taskQueueService;

    private final AtomicBoolean scheduleIsRunning = new AtomicBoolean(false);
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

    @Scheduled(fixedRate = 60000)
    public void startWorker(){
        if (!scheduleIsRunning.get()) {
            return;
        }
        // Start the worker
        String url = scraperService.getKijijiUrl();
        taskQueueService.enqueueScraperTask(url);
    }

    @PutMapping("/set-url")
    public ResponseEntity<String> setUrl(@RequestBody String url) {
        // Set the URL for the scraper
        scraperService.setKijijiUrl(url);

        // Return a response indicating success
        return ResponseEntity.ok("URL set to: " + url);
    }

    @PostMapping("/stop-schedule")
    public ResponseEntity<String> stopSchedule() {
        // Stop the worker
        scheduleIsRunning.set(false);

        // Return a response indicating success
        return ResponseEntity.ok("Scheduler stopped!");
    }

    @PostMapping("/start-schedule")
    public ResponseEntity<String> startSchedule() {
        // Start the worker
        scheduleIsRunning.set(true);

        // Return a response indicating success
        return ResponseEntity.ok("Scheduler started!");
    }
}
