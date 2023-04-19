package at.aau.cleancode;

import at.aau.cleancode.translation.DeeplTranslator;

import java.util.Scanner;

public class Main {
    public static Scanner userInputScanner;

    public static void main(String[] args) {
        userInputScanner = new Scanner(System.in);

        String targetUrl = readTargetUrl();
        int crawlDepth = readCrawlDepth();
        String targetLanguage = readTargetLanguage();
    }

    public static String readTargetUrl(){
        System.out.print("Enter URL to crawl: ");
        return userInputScanner.nextLine();
    }

    public static int readCrawlDepth(){
        System.out.println("Enter crawl depth (default 2): ");
        int crawlDepth = 2;
        String enteredDepth = userInputScanner.nextLine();
        try{
            crawlDepth = Integer.parseInt(enteredDepth);
            if(crawlDepth < 1){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException nfe){
            System.out.println("Invalid depth continuing with default (2)");
            crawlDepth = 2;
        }

        return crawlDepth;
    }

    public static String readTargetLanguage(){
        System.out.println("Enter the target language, following languages are available: ");
        DeeplTranslator.printTargetLanguages();
        boolean isSupportedLanguage;
        String targetLanguage;
        do{
            System.out.print("Enter your preferred language, ex. DE: ");
            targetLanguage = userInputScanner.nextLine();
            isSupportedLanguage = DeeplTranslator.checkLanguage(targetLanguage);
            if(!isSupportedLanguage){
                System.out.println("Unsupported language. Please select one from the list and enter the shortcode.");
            }
        } while(!isSupportedLanguage);

        return targetLanguage;
    }

}
