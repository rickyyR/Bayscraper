package org.rickyyr.Bayscraper.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bayscraper {

  public ArrayList<ListingItem> getItemsForSearchword(WebClient webClient, String searchword) {

    System.out.println("Extracting pages from website.");

    ArrayList<HtmlPage> pages = this.getPages(webClient, searchword);

    System.out.println("Extracting items from pages.");

    ArrayList<ListingItem> items = this.convertElementsToItems(getListingElementsFromPages(pages));
    if(items.size() > 0) {

      System.out.println("SUCCESS! Scraped: " + items.size() + " items." + "\n" + "Exporting to Json...");

      JsonExporter<ListingItem> jsonExporter = new JsonExporter<>(searchword + "_ScrapedItems" + ".json");
      for(ListingItem l:items) {

        System.out.print("Item: " + items.indexOf(l) + "\r");

        jsonExporter.addObjectToList(l);
      }

      System.out.println("DONE! Please wait 5 minutes before scanning again to avoid getting blocked.");

    } else {

      System.out.println("ERROR! No items found for " + "[" + searchword + "]" +" or scan blocked. Try again in a bit.");
    }

    return items;
  }

  private ArrayList<HtmlPage> getPages(WebClient webClient, String searchword) {

    int siteHelperInt = 1;

    String url = "https://www.ebay-kleinanzeigen.de/s-" + searchword + "/k0";
    ArrayList<HtmlPage> pages = new ArrayList<>();

    while (true) {
      HtmlPage page = getSinglePage(webClient, url);
      if(page.getFirstByXPath
        ("./html/body/div[1]/div[2]/div/div[3]/div[2]/div[3]") != null) {
        pages.add(page);
        System.out.println("Page added! " + siteHelperInt);
        url = "https://www.ebay-kleinanzeigen.de/s-seite" + siteHelperInt + "/" + searchword + "/k0" + "\r";
        siteHelperInt++;
      } else {
        System.out.println("No Listings found on page, breaking...");
        break;
      }
    }

    return pages;
  }

  public HtmlPage getSinglePage(WebClient webClient, String url) {
    HtmlPage page = null;
    try {
      page = webClient.getPage(url);
    } catch (IOException e) {
      System.out.println("ERROR! " + url + " Not found.");
    }
    return page;
  }

  private ArrayList<HtmlElement> getListingElementsFromPages(ArrayList<HtmlPage> pages) {

    ArrayList<HtmlElement> allElements = new ArrayList<>();

    for(HtmlPage p:pages) {
      final List<HtmlElement> listings = p.getByXPath("//article[@class='aditem']");
      allElements.addAll(listings);
    }

    return allElements;
  }

  private ArrayList<ListingItem> convertElementsToItems(List<HtmlElement> pages) {

    ArrayList<ListingItem> items  = new ArrayList<>();

    for (HtmlElement h : pages) {

      String url = "https://www.ebay-kleinanzeigen.de" + ((HtmlAnchor) h.getFirstByXPath("./div[2]/div[2]/h2/a")).getHrefAttribute();
      String title = ((HtmlAnchor) h.getFirstByXPath("./div[2]/div[2]/h2/a")).getTextContent();
      String priceS = ((HtmlParagraph) h.getFirstByXPath("./div[2]/div[2]/p[2]")).getTextContent()
        .replaceAll("[^0-9]","");
      if(priceS.equals("")) {
        priceS = "0";
      }
      int price = Integer.parseInt(priceS);

      ListingItem li = new ListingItem(title, url, price);

      if(!title.contains("Suche")) {
        items.add(li);
        System.out.print("Item: " + (items.size() + 1) + "\r");
      }

    }

    return items;
  }

}
