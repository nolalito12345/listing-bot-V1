package org.listingbotv1.listingbot;

import jakarta.annotation.PostConstruct;
import org.listingbotv1.listingbot.worker.ScraperWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;

import java.io.IOException;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ListingBotApplication {

	@Autowired
	private ScraperWorker worker;

	public static void main(String[] args){
		SpringApplication.run(ListingBotApplication.class, args);
	}

	@PostConstruct
	public void init() throws IOException {
		worker.startWorker();
	}


}
