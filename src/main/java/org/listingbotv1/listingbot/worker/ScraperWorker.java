package org.listingbotv1.listingbot.worker;

import org.listingbotv1.listingbot.service.ScraperService;
import org.listingbotv1.listingbot.service.TaskQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScraperWorker {

    @Autowired
    private TaskQueueService taskQueueService;

    @Autowired
    private ScraperService scraperService;

    @Async
    public void startWorker() throws IOException {
        while (true) {
            String url = taskQueueService.dequeueScraperTask();
            if (url != null) {
                // Execute web scraping task
                scraperService.kijiji_scrape(url);
            }else {
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
