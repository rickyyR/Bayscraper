package org.rickyyr.Bayscraper.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Bayscraper {

  public static JsonExporter<ListingItem> jsonExporter;

  public static ArrayList<ListingItem> getItemsForSearchword(WebClient webClient, String searchword) {

    System.out.println("Extracting pages from website.");

    ArrayList<HtmlPage> pages = Bayscraper.getPages(webClient, searchword);

    System.out.println("Extracting items from pages.");

    ArrayList<ListingItem> items = Bayscraper.convertElementsToItems(getListingElementsFromPages(pages));
    if(items.size() > 0) {

      System.out.println("SUCCESS! Scraped: " + items.size() + " items." + "\n" + "Exporting to Json...");

      jsonExporter = new JsonExporter<>(searchword + "_ScrapedItems" + ".json");
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

  public static ArrayList<HtmlPage> getPages(WebClient webClient, String searchword) {

    String url = "https://www.ebay-kleinanzeigen.de/s-" + searchword + "/k0";
    ArrayList<HtmlPage> pages = new ArrayList<>();
    boolean iterating = true;

    while (iterating) {
      HtmlPage page = getSinglePage(webClient, url);
      pages.add(page);
      if(page.getFirstByXPath
        ("./html/body/div[1]/div[2]/div/div[3]/div[2]/div[5]/div[2]/div/div[2]/a") == null) {
        break;
      } else {

        System.out.print("Page: " + (pages.size() + 1) + "\r");

        HtmlAnchor anchor = page.getFirstByXPath
          ("./html/body/div[1]/div[2]/div/div[3]/div[2]/div[5]/div[2]/div/div[2]/a");
        url = "https://www.ebay-kleinanzeigen.de" + anchor.getHrefAttribute();
      }
    }

    return pages;
  }

  public static HtmlPage getSinglePage(WebClient webClient, String url) {
    HtmlPage page = null;
    try {
      page = webClient.getPage(url);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return page;
  }

  public static ArrayList<HtmlElement> getListingElementsFromPages(ArrayList<HtmlPage> pages) {

    ArrayList<HtmlElement> allElements = new ArrayList<>();

    for(HtmlPage p:pages) {
      final List<HtmlElement> listings = p.getByXPath("//article[@class='aditem']");
      allElements.addAll(listings);
    }

    return allElements;
  }

  public static ArrayList<ListingItem> convertElementsToItems(List<HtmlElement> pages) {

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
