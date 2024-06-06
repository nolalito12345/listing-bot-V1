This Listing bot is a cloud based REST API that regularly scraped kijiji rental listing information and stores it. 
It is written fully in Java, uses Jsoup for html document traversal and Maven for dependencies.

It operates using a redis task queueing system, which takes scraping "jobs" that will be executed upon a select kijiji link. It periodically scans this link, storing every single new listing within the 
specified radius, notifiying users through Twilio if a rental listing matches their search profile. Multiple scraping jobs can be active at the same time, I just have to port the app to Facebook marketplace
and Craigslist so it can handle scraping jobs of multiple sites at once. 

The goal of the application is to give the user a competitive "edge" when trying to find a rental listing, and being able to send that first important message.

