package org.rickyyr.Bayscraper.scraper;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class Browser {

  private WebClient browser;

  public Browser() {
    final BrowserVersion.BrowserVersionBuilder mozillaBuilder = new BrowserVersion.BrowserVersionBuilder(BrowserVersion.FIREFOX);
    mozillaBuilder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:53.0) Gecko/20100101 Firefox/53.0");
    final BrowserVersion firefox = mozillaBuilder.build();
    com.gargoylesoftware.htmlunit.WebClient webClient = new com.gargoylesoftware.htmlunit.WebClient(firefox);
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setJavaScriptEnabled(false);
    webClient.getOptions().setTimeout(5000);

    this.browser = webClient;
  }

  public WebClient getBrowser() {
    return this.browser;
  }
}
