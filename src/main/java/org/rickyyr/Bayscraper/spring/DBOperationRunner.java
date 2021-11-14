package org.rickyyr.Bayscraper.spring;

import org.rickyyr.Bayscraper.scraper.ListingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

public class DBOperationRunner implements CommandLineRunner {

  @Autowired
  ListingRepository eRepo;


  @Override
  public void run(String... args) throws Exception {

    eRepo.saveAll(List.of(

      new ListingItem("TestTitle","Some Url",1337),
      new ListingItem("TestTitle","Some Url",1337),
      new ListingItem("TestTitle","Some Url",1337),
      new ListingItem("TestTitle","Some Url",1337),
      new ListingItem("TestTitle","Some Url",1337)));

  }
}
