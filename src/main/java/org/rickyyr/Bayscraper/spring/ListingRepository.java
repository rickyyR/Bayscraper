package org.rickyyr.Bayscraper.spring;

import org.rickyyr.Bayscraper.scraper.ListingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<ListingItem, Integer> {
}
