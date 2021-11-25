package org.rickyyr.Bayscraper.scraper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Logger {

  private StringBuilder stringBuilder;
  private FileWriter fileWriter;
  private BufferedWriter bufferedWriter;
  private DateTimeFormatter formatter;
  private String[] currentDateAndTime;

  public Logger(String fileName) {
    this.stringBuilder = new StringBuilder();
    try {
      this.fileWriter = new FileWriter(fileName, true);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.bufferedWriter = new BufferedWriter(this.fileWriter);

    this.formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
      .withLocale(Locale.getDefault())
      .withZone(ZoneId.systemDefault());

    String dateAndTime = this.formatter.format(Instant.now());
    String[] splitted = dateAndTime.split(",");
    this.currentDateAndTime = new String[]{splitted[0].replaceAll("\\s+", ""),
      splitted[1].replaceAll("\\s+", "")};
  }

  public void logSuccessfulScan(String searchword, String numberOfItemsScraped) {

    this.stringBuilder.append("SUCCESS! ")
      .append(this.currentDateAndTime[0])
      .append(" - ")
      .append(this.currentDateAndTime[1])
      .append(". [")
      .append(searchword)
      .append("]. Scraped ")
      .append(numberOfItemsScraped)
      .append(" Items.")
      .append("\n");

    try {
      this.bufferedWriter.write(this.stringBuilder.toString());
      this.stringBuilder.setLength(0);
      this.bufferedWriter.close();
      this.fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void logScanWithException(String searchword, Exception thrownException) {

    this.stringBuilder.append("FAILED! ")
      .append(this.currentDateAndTime[0])
      .append(" - ")
      .append(this.currentDateAndTime[1])
      .append(". [")
      .append(searchword)
      .append("]. Exception: ")
      .append(thrownException.toString())
      .append("\n");

    try {
      this.bufferedWriter.write(this.stringBuilder.toString());
      this.stringBuilder.setLength(0);
      this.bufferedWriter.close();
      this.fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void logEmptyOrBlockedScan(String searchword) {

    this.stringBuilder.append("FAILED! ")
      .append(this.currentDateAndTime[0])
      .append(" - ")
      .append(this.currentDateAndTime[1])
      .append(". [")
      .append(searchword)
      .append("]. No items found or scan blocked")
      .append("\n");

    try {
      this.bufferedWriter.write(this.stringBuilder.toString());
      this.stringBuilder.setLength(0);
      this.bufferedWriter.close();
      this.fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getCurrentDate() {
    return this.currentDateAndTime[0];
  }

  public String getCurrentTime() {
    return this.currentDateAndTime[1];
  }

}
