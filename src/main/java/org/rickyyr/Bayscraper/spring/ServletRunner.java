package org.rickyyr.Bayscraper.spring;

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

    if(!scrape.equals("")){

      ArrayList<ListingItem> items = bayscraper.getItemsForSearchword(browser.getBrowser(), scrape);

      if(items.size() > 0) {

        return "<pre>" + Ui.printLogo().replaceAll("\n", "<br>") + "<br>" +
          "Scan for " + "[" + scrape + "]" + " complete!" + "<br>" + "Scraped " + items.size() + " items." + "<br>" +
         "DONE! Please wait 5 minutes before scanning again to avoid getting blocked." + "</pre>";

      } else {
        return "<pre>" + Ui.printLogo().replaceAll("\n", "<br>") + "<br>" +
          "-ERROR! No items found for " + "[" + scrape + "]" + " or scan blocked. Try again in a bit.";
      }

    } else {
      return "<pre>" + Ui.printLogo().replaceAll("\n", "<br>") + "<br>" +
        "-ERROR! No parameter. Please provide ?scrape=yourSearchHere at end of url..." ;
    }
  }
}