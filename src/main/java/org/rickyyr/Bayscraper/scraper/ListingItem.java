package org.rickyyr.Bayscraper.scraper;

public class ListingItem {

  private String title;
  private String url ;
  private int price;

  public ListingItem(String title, String url, int price) {
    this.init(title, url, price);
  }

  private void init(String title, String url, int price){
    this.title = title;
    this.url = url;
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

  public int getPrice() {
    return this.price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String toString() {
    return this.title + ": " + this.price + "\n" + this.url;
  }
}
