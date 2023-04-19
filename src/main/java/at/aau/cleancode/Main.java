package at.aau.cleancode;

import java.util.Scanner;

public class Main {
    public static Scanner userInputScanner;

    public static String[] supportedLanguages = {"BG - Bulgarian",
            "CS - Czech",
            "DA - Danish",
            "DE - German",
            "EL - Greek",
            "EN-GB - English (British)",
            "EN-US - English (American)",
            "ES - Spanish",
            "ET - Estonian",
            "FI - Finnish",
            "FR - French",
            "HU - Hungarian",
            "ID - Indonesian",
            "IT - Italian",
            "JA - Japanese",
            "KO - Korean",
            "LT - Lithuanian",
            "LV - Latvian",
            "NB - Norwegian (Bokmål)",
            "NL - Dutch",
            "PL - Polish",
            "PT-BR - Portuguese (Brazilian)",
            "PT-PT - Portuguese (all Portuguese varieties excluding Brazilian Portuguese)",
            "RO - Romanian",
            "RU - Russian",
            "SK - Slovak",
            "SL - Slovenian",
            "SV - Swedish",
            "TR - Turkish",
            "UK - Ukrainian",
            "ZH - Chinese (simplified)"};

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
        } catch (NumberFormatException nfe){
            System.out.println("Invalid depth continuing with default (2)");
        }
        return crawlDepth;
    }

    public static String readTargetLanguage(){
        System.out.println("Enter the target language, following languages are available: ");
        printTargetLanguages();
        boolean isSupportedLanguage;
        String targetLanguage;
        do{
            System.out.print("Enter your preferred language, ex. DE: ");
            targetLanguage = userInputScanner.nextLine();
            isSupportedLanguage = checkLanguage(targetLanguage);
            if(!isSupportedLanguage){
                System.out.println("Unsupported language. Please select one from the list and enter the shortcode.");
            }
        } while(!isSupportedLanguage);

        return targetLanguage;
    }

    public static void printTargetLanguages() {
        for(String lang : supportedLanguages){
            System.out.println(lang);
        }
    }

    public static boolean checkLanguage(String selectedLanguage){
        for(String lang : supportedLanguages){
            if(selectedLanguage.equals(lang.substring(0, selectedLanguage.length()))){
                return true;
            }
        }
        return false;
    }
}
