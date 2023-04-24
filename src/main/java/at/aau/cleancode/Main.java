package at.aau.cleancode;

import at.aau.cleancode.crawler.Report;
import at.aau.cleancode.crawler.ReportWriter;
import at.aau.cleancode.translation.DeeplAPIUtils;
import at.aau.cleancode.translation.DeeplTranslator;
import at.aau.cleancode.translation.DeeplTranslatorException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner userInputScanner;

    public static void main(String[] args) {
        userInputScanner = new Scanner(System.in);

        String targetUrl = readTargetUrl();
        int crawlDepth = readCrawlDepth();
        String targetLanguage = readTargetLanguage();

        String translationApiKey = DeeplAPIUtils.loadApiKey();
        DeeplTranslator translator = null;
        try {
            translator = new DeeplTranslator(targetLanguage, translationApiKey);
        } catch (DeeplTranslatorException e) {
            System.out.println("Unable to create Translation service.");
        }
        Report report = new Report(targetUrl, crawlDepth);
        report.createReport();
        ReportWriter reportWriter;
        try {
            reportWriter = new ReportWriter(report, translator, null); // use null to use default writer
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

    public static String readTargetUrl() {
        String url = "";
        do {
            System.out.print("Enter URL to crawl e.g. (https://example.com): ");
            url = userInputScanner.nextLine();
        } while (!url.matches("https?://.*"));
        return url;
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
