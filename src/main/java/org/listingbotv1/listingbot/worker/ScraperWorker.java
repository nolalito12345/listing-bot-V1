package org.listingbotv1.listingbot.worker;

import org.listingbotv1.listingbot.model.Listing;
import org.listingbotv1.listingbot.service.ScraperService;
import org.listingbotv1.listingbot.service.TaskQueueService;
import org.listingbotv1.listingbot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

@Component
public class ScraperWorker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScraperWorker.class);


    @Autowired
    private TaskQueueService taskQueueService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScraperService scraperService;


    @Async
    public void startWorker() throws IOException {
        LOGGER.info("Starting worker");

        while (true) {
            Long size = (taskQueueService.getRedisTemplate()).opsForList().size("scraperQueue");
            LOGGER.info("Queue size: {}", size);
            if ((taskQueueService.getRedisTemplate()).opsForList().size("scraperQueue") > 0) {
                String url = taskQueueService.dequeueScraperTask();
                // Execute web scraping task
                HashSet<Listing> listings = scraperService.kijiji_scrape(url);
                if (listings != null) {
                    LOGGER.info("Scraped {} new listings from kijiji", listings.size());
                    userService.notifyUsersOfMatchingListings(listings);
                }

            } else {
                // No tasks available, wait for some time before trying again
                try {
                    Thread.sleep(5000); // Wait for 5 seconds before trying again
                } catch (InterruptedException e) {
                    // Handle interruption
                    e.printStackTrace();
                }
            }
        }
    }
}