package org.rickyyr.Bayscraper.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bayscraper {


  public ArrayList<ListingItem> getItemsForSearchword(WebClient webClient, String searchword, String date, String time) {
    Ui.cleanOutput();
    Ui.printCrawlingPages();
    ArrayList<HtmlPage> pages = this.getPages(webClient, searchword);
    Ui.printExtractingItems();
    ArrayList<ListingItem> items = this.convertElementsToItems(getListingElementsFromPages(pages));

    if(items.size() > 0) {
      Ui.printWritingToCSV();

      try {
        FileWriter fileWriter = new FileWriter(searchword  + date.replaceAll("\\.", "") +
          time.replaceAll(":", "") + ".csv");
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("Title", "Price", "Url"));

        for(ListingItem i:items) {
          csvPrinter.printRecord(i.getTitle(),i.getPrice(),i.getUrl());
        }
        csvPrinter.flush();

      } catch (IOException e) {e.printStackTrace();}

    } else {
      Ui.printErrorMessage(searchword);
      return items;
    }

    Ui.printScanFinished(searchword, items.size());
    return items;

  }

  private ArrayList<HtmlPage> getPages(WebClient webClient, String searchword) {

    String url = "https://www.ebay-kleinanzeigen.de/s-" + searchword + "/k0";
    ArrayList<String> linksToPages = new ArrayList<>();
    ArrayList<HtmlPage> pages = new ArrayList<>();

    while(true) {

      HtmlPage page = getSinglePage(webClient, url);
      HtmlAnchor nextSiteAnch =  page.getFirstByXPath("//span[@class='pagination-current']/following-sibling::a[1]");
      pages.add(page);

      if(nextSiteAnch != null) {
        url = "https://www.ebay-kleinanzeigen.de" + nextSiteAnch.getHrefAttribute();

      } else {break;}
    }

    return pages;
  }

  private HtmlPage getSinglePage(WebClient webClient, String url) {
    HtmlPage page = null;

    try {
      page = webClient.getPage(url);
    } catch (IOException e) {
      e.printStackTrace();
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
      String url = "https://www.ebay-kleinanzeigen.de" + ((HtmlAnchor) h.getFirstByXPath("./div[2]/div[2]/h2/a")).getHrefAttribute().replaceAll(" ", "").replaceAll(",", "");
      String title = ((HtmlAnchor) h.getFirstByXPath("./div[2]/div[2]/h2/a")).getTextContent().replaceAll(" ", "").replaceAll(",", "");
      String priceS = ((HtmlParagraph) h.getFirstByXPath("./div[2]/div[2]/p[2]")).getTextContent()
        .replaceAll("[^0-9]","");
      if(priceS.equals("")) {
        priceS = "0";
      }
      int price = Integer.parseInt(priceS);

      ListingItem li = new ListingItem(title, url, price);
      if(!title.contains("Suche")) {
        items.add(li);
      }

    }

    return items;
  }

}
