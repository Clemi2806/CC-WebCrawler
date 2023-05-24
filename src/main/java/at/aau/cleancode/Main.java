package at.aau.cleancode;

import at.aau.cleancode.crawler.Report;
import at.aau.cleancode.crawler.ReportBuilder;
import at.aau.cleancode.crawler.ReportWriter;
import at.aau.cleancode.translation.DeeplAPIUtils;
import at.aau.cleancode.translation.DeeplTranslator;
import at.aau.cleancode.translation.DeeplTranslatorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner userInputScanner;

    public static void main(String[] args) {
        userInputScanner = new Scanner(System.in);

        List<String> targetUrls = readTargetUrl();
        int crawlDepth = readCrawlDepth();
        String targetLanguage = readTargetLanguage();

        String translationApiKey = DeeplAPIUtils.loadApiKey();
        DeeplTranslator translator = null;
        try {
            translator = new DeeplTranslator(targetLanguage, translationApiKey);
        } catch (DeeplTranslatorException e) {
            System.out.println("Unable to create Translation service.");
        }

        ReportBuilder builder = new ReportBuilder(targetUrls, crawlDepth);
        List<Report> reports = null;
        try {
            reports = builder.buildReports();
        } catch (InterruptedException e) {
            System.out.println("Upsi. Threads putt.");
        }

        ReportWriter reportWriter;
        try {
            reportWriter = new ReportWriter(reports.get(0), translator, null); // use null to use default writer
        } catch (IOException e) {
            System.out.println("Unable to create report file!");
            return;
        }
        try {
            reportWriter.writeReport();
        } catch (IOException e) {
            System.out.println("Unable to write report file!");
        }
    }

    public static List<String> readTargetUrl() {
        List<String> targetUrls = new ArrayList<>();
        boolean validUrls = true;
        do {
            System.out.print("Enter one or more URLs to crawl each seperated by a space e.g. (https://example.com http://website.org): ");
            String line = userInputScanner.nextLine();

            // check each individual website
            for(String targetUrl : line.split(" ")){
                if(!targetUrl.matches("https?://.*")){
                    validUrls = false;
                    targetUrls = new ArrayList<>();
                    break;
                }
                targetUrls.add(targetUrl);
            }

        } while (!validUrls);
        return targetUrls;
    }

    public static int readCrawlDepth() {
        System.out.print("Enter crawl depth (default 2): ");
        int crawlDepth = 2;
        String enteredDepth = userInputScanner.nextLine();
        try {
            crawlDepth = Integer.parseInt(enteredDepth);
            if (crawlDepth < 1) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException nfe) {
            System.out.println("Invalid depth continuing with default (2)");
            crawlDepth = 2;
        }

        return crawlDepth;
    }

    public static String readTargetLanguage() {
        System.out.println("Enter the target language, following languages are available: ");
        DeeplTranslator.printTargetLanguages();
        boolean isSupportedLanguage;
        String targetLanguage;
        do {
            System.out.print("Enter your preferred language, ex. DE: ");
            targetLanguage = userInputScanner.nextLine();
            isSupportedLanguage = DeeplTranslator.isSupportedLanguage(targetLanguage);
            if (!isSupportedLanguage) {
                System.out.println("Unsupported language. Please select one from the list and enter the shortcode.");
            }
        } while (!isSupportedLanguage);

        return targetLanguage;
    }

}
