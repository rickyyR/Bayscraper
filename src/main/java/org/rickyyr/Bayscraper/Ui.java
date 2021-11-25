package org.rickyyr.Bayscraper;

public abstract class Ui {

  public static String printLogo() {
    return "__________                                                              " + "\n" +
      "\\______   \\_____  ___.__. ______ ________________  ______   ___________ " + "\n" +
      " |    |  _/\\__  \\<   |  |/  ___// ___\\_  __ \\__  \\ \\____ \\_/ __ \\_  __ \\" + "\n" +
      " |    |   \\ / __ \\\\___  |\\___ \\\\  \\___|  | \\// __ \\|  |_> >  ___/|  | \\/" + "\n" +
      " |______  /(____  / ____/____  >\\___  >__|  (____  /   __/ \\___  >__|   " + "\n" +
      "        \\/      \\/\\/         \\/     \\/           \\/|__|        \\/       " + "\n" +
      "<--------------------------------org.rickyyr---------------------------->" +"\n";
  }

  public static String printScrapeStarted(String searchword) {
    return "Scan for [" + searchword + "] started!" + "\r";
  }

  public static String printScrapeFinished(String searchword, String numberOfScrapedItems) {
    return "Scan for [" + searchword +"] complete! Scraped: " + numberOfScrapedItems + " items." + "\r";
  }

  public static String printScrapeFailed(String searchword) {
    return "Scan for [" + searchword + "] failed!" + "\r";
  }
}
