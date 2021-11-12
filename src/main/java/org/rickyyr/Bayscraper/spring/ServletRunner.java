package org.rickyyr.Bayscraper.spring;

import org.rickyyr.Bayscraper.scraper.*;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/bayscraper")
public class ServletRunner {

  private Ui scraperUi = new Ui();
  private Browser browser = new Browser();

  @GetMapping(path="/")
  public String scraperService(@RequestParam(value = "scrape", defaultValue = "")String scrape) {

    if(!scrape.equals("")){
      System.out.println(scraperUi.printLogo());
      ArrayList<ListingItem> items = Bayscraper.getItemsForSearchword(browser.getBrowser(), scrape);

      if(items.size() > 0) {
        return "<pre>" + scraperUi.printLogo().replaceAll("\n", "<br>") + "<br>" +
          "Scan for " + "[" + scrape + "]" + " complete!" + "</pre>";
      } else {
        return "<pre>" + scraperUi.printLogo().replaceAll("\n", "<br>") + "<br>" +
          "ERROR! No items found for " + "[" + scrape + "]" + " or scan blocked. Try again in a bit.";
      }

    } else {
      return "<pre>" + scraperUi.printLogo().replaceAll("\n", "<br>") + "<br>" +
        "-ERROR! No parameter. Please provide ?scrape=yourSearchHere at end of url.." ;
    }
  }
}