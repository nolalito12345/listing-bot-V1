package org.listingbotv1.listingbot.controller;

import org.listingbotv1.listingbot.worker.ScraperWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ScraperWorkerController {

    @Autowired
    private ScraperWorker scraperWorker;

    @PostMapping("/start-worker")
    public void startWorker() throws IOException {
        scraperWorker.startWorker();
    }
}
