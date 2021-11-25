package org.rickyyr.Bayscraper.spring;

import org.rickyyr.Bayscraper.Ui;
import org.rickyyr.Bayscraper.scraper.*;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/bayscraper")
public class ServletRunner {

  private Browser browser = new Browser();
  private Bayscraper bayscraper = new Bayscraper();

  @GetMapping(path="/")
  public String scraperService(@RequestParam(value = "scrape", defaultValue = "")String scrape) {

    Logger logger = new Logger("ScraperLog.txt");

    if(!scrape.equals("")){
      System.out.print(Ui.printScrapeStarted(scrape));
      ArrayList<ListingItem> items = this.bayscraper.getItemsForSearchword(this.browser.getBrowser(), scrape,
        logger.getCurrentDate(), logger.getCurrentTime());

      if(items.size() > 0) {
        logger.logSuccessfulScan(scrape, String.valueOf(items.size()));
        System.out.print(Ui.printScrapeFinished(scrape, String.valueOf(items.size())));
        return "<pre>" + Ui.printLogo().replaceAll("\n", "<br>") + "<br>" +
          "Scan for " + "[" + scrape + "]" + " complete!" + "<br>" + "Scraped " + items.size() + " items." + "<br>" +
         "DONE! Please wait 5 minutes before scanning again to avoid getting blocked." + "</pre>";

      } else {
        logger.logEmptyOrBlockedScan(scrape);
        System.out.print(Ui.printScrapeFailed(scrape));
        return "<pre>" + Ui.printLogo().replaceAll("\n", "<br>") + "<br>" +
          "-ERROR! No items found for " + "[" + scrape + "]" + " or scan blocked. Try again in a bit.";
      }

    } else {
      return "<pre>" + Ui.printLogo().replaceAll("\n", "<br>") + "<br>" +
        "-ERROR! No parameter. Please provide ?scrape=yourSearchHere at end of url..." ;
    }
  }
}