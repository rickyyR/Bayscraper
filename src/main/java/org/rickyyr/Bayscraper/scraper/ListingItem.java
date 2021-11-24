package org.rickyyr.Bayscraper.scraper;

public class ListingItem {

  private Integer price;
  private String title;
  private String url;

  public ListingItem(String title, String url, Integer price) {
    super();
    this.price = price;
    this.url = url;
    this.title = title;
  }

  public ListingItem() {
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


}
