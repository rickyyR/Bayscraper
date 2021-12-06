package org.rickyyr.Bayscraper.scraper;

public abstract class Ui {

  public static String printLogo() {
    return "__________                                                              " + "\n" +
      "\\______   \\_____  ___.__. ______ ________________  ______   ___________ " + "\n" +
      " |    |  _/\\__  \\<   |  |/  ___// ___\\_  __ \\__  \\ \\____ \\_/ __ \\_  __ \\" + "\n" +
      " |    |   \\ / __ \\\\___  |\\___ \\\\  \\___|  | \\// __ \\|  |_> >  ___/|  | \\/" + "\n" +
      " |______  /(____  / ____/____  >\\___  >__|  (____  /   __/ \\___  >__|   " + "\n" +
      "        \\/      \\/\\/         \\/     \\/           \\/|__|        \\/       " + "\n" +
      "<--------------------------------org.rickyyr---------------------------->" + "\n";
  }

  public static void printScanFinished(String searchword, int itemsScanned) {
    System.out.print("Scan for [" + searchword + "] complete! Scraped [" + itemsScanned + "] items. \r");
  }

  public static void printErrorMessage(String searchword) {
    System.out.print("-ERROR! No items found for " + "[" + searchword + "]" + " or scan blocked. Try again in a bit. \r");
  }

  public static void printCrawlingPages() {
    System.out.print("Crawling Pages \r");
  }

  public static void printExtractingItems() {
    System.out.print("Extracting items \r");
  }

  public static void printWritingToCSV() {
    System.out.print("Writing to .csv \r");
  }

  public static void cleanOutput() {
    System.out.print("                                                                     \r" );
  }
}
