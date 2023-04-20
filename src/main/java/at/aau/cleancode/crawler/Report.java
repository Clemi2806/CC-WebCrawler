package at.aau.cleancode.crawler;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private Website startingSite;
    // wieso targetLanguage da?
    private final String targetLanguage;
    private List<Website> websites;

    public Report(Website website, String targetLanguage) {
        this.startingSite = website;
        this.targetLanguage = targetLanguage;
        websites = new ArrayList<>();
    }

    public Report(String startingSite, int crawlingDepth, String targetLanguage) {
        this.startingSite = new Website(startingSite, crawlingDepth);
        this.targetLanguage = targetLanguage;
        websites = new ArrayList<>();
    }

    public void createReport() {
        startingSite.crawlWebsite();
        websites.add(startingSite);
        if(this.startingSite.getCrawlDepth() <= 1){
            return;
        }
        for(Link link : startingSite.getLinks()){

            if(link.isBroken()){
                continue;
            }
            Report report = new Report(link.getHref(), this.startingSite.getCrawlDepth()-1, this.targetLanguage);
            report.createReport();
            websites.addAll(report.getAllWebsites());
        }
    }

    public List<Website> getAllWebsites(){
        return this.websites;
    }

    public String getStartingSite() {
        return startingSite.getUrl();
    }

    public int getCrawlingDepth() {
        return this.startingSite.getCrawlDepth();
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }
}


