package org.listingbotv1.listingbot.worker;

import org.listingbotv1.listingbot.model.Listing;
import org.listingbotv1.listingbot.service.ScraperService;
import org.listingbotv1.listingbot.service.TaskQueueService;
import org.listingbotv1.listingbot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;

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
    public void startWorker(){
        LOGGER.info("Starting worker");

        while (true) {
            if ((taskQueueService.getRedisTemplate()).opsForList().size("scraperQueue") > 0) {
                String url = taskQueueService.dequeueScraperTask();
                if(url == null|| url.isEmpty()){
                    // Skip this task if it is null
                    LOGGER.error("Dequeued task is null, skipping...");
                   continue;
                }
                // Execute web scraping task
                HashSet<Listing> listings = null;
                try {
                    listings = scraperService.kijiji_scrape(url);
                } catch (IOException ioe) {
                    LOGGER.error("Error scraping kijiji", ioe);
                }
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