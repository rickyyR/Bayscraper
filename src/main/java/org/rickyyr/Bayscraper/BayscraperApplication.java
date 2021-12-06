package org.rickyyr.Bayscraper;

import org.rickyyr.Bayscraper.scraper.Ui;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})

public class BayscraperApplication {

	public static void main(String[] args) {

		SpringApplication.run(BayscraperApplication.class, args);

		System.out.println(Ui.printLogo() + "\n" +
			"Connect to thisMachinesIP:8080/bayscraper/ Pass search parameter with '?scrape=YourSearchWord'");
	}

}
