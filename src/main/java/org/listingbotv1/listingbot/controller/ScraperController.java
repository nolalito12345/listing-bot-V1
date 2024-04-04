package org.listingbotv1.listingbot.controller;

import org.listingbotv1.listingbot.model.Listing;
import org.listingbotv1.listingbot.service.ListingService;
import org.listingbotv1.listingbot.service.ScraperService;
import org.listingbotv1.listingbot.service.TaskQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping
public class ScraperController {



    private final ScraperService scraperService;

    @Autowired
    private TaskQueueService taskQueueService;
    @Autowired
    // This annotation basically tells our program to instantiate listingservice so it can be passed. We need to specify the @Component or @Service to be instantiated
    public ScraperController(ScraperService scraperService) { //**Input from Model (Service)
        this.scraperService = scraperService;
    }

    @GetMapping("/testurl")
    public void scrapeListingKijiji() throws IOException {
        String exurl = "https://www.kijiji.ca/v-appartement-condo/ville-de-montreal/condo-appartement-3-1-2-a-louer-ville-marie-tout-inclus/1688943578";
        String exurl1 = "https://www.kijiji.ca/v-appartement-condo/ville-de-montreal/libre-immediatement-4-1-2-renove-1600-mois-metro-papineau/1689691312";
        String exurl2 = "https://www.kijiji.ca/v-appartement-condo/ville-de-montreal/condo-neuf-1cc-a-louer-a-ville-marie-pres-du-vieux-port/1683946955";

        scraperService.kijiji_scrape_listing_details(exurl);
        //listingservice.scrapeListingKijiji(exurl1);
        //listingservice.scrapeListingKijiji(exurl2);



    }
    @GetMapping("/testmainpage")
    public void scrapeMainPageKijiji() throws IOException {
        String url = "https://www.kijiji.ca/b-apartments-condos/ville-de-montreal/c37l1700281?sort=dateDesc&radius=2.0&address=Montreal%2C+QC+H2X+2Y4&ll=45.514161%2C-73.57067";
        HashSet<String> links = scraperService.kijiji_scrape_listing_links(url);
        int count = 0;
        for(String link : links){
            System.out.println(link);
            count++;
        }
        System.out.println(count);
    }
    @GetMapping("/testfilldatabase")
    public String fill_database_kijiji()throws IOException{
        String url = "https://www.kijiji.ca/b-apartments-condos/ville-de-montreal/c37l1700281?sort=dateDesc&radius=2.0&address=Montreal%2C+QC+H2X+2Y4&ll=45.514161%2C-73.57067";
        scraperService.kijiji_scrape(url);
        return "Scraping Kijiji in background";
    }

    @PostMapping("/start-scraper")
    @ResponseBody // Ensure that the return value is serialized to the response body
    public ResponseEntity<String> startScraper(@RequestBody String url) {
        // Enqueue web scraper task
        taskQueueService.enqueueScraperTask(url);

        // Return a response indicating success
        return ResponseEntity.ok("Web scraper task enqueued successfully.");
    }
}
