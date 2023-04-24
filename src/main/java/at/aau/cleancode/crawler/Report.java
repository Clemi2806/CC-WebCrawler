package at.aau.cleancode.crawler;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private Website startingSite;
    private List<Website> websites;

    public Report(Website website) {
        this.startingSite = website;
        websites = new ArrayList<>();
    }

    public Report(String startingSite, int crawlingDepth) {
        this.startingSite = new Website(startingSite, crawlingDepth);
        websites = new ArrayList<>();
    }

    public void createReport() {
        startingSite.crawlWebsite();
        websites.add(startingSite);
        if (this.startingSite.getCrawlDepth() <= 1) {
            return;
        }
        for (Link link : startingSite.getLinks()) {

            if (link.isBroken()) {
                continue;
            }
            // create reports for every sublink
            Report report = new Report(link.getHref(), this.startingSite.getCrawlDepth() - 1);
            report.createReport();
            // merge subreports
            websites.addAll(report.getAllWebsites());
        }
    }

    public List<Website> getAllWebsites() {
        return this.websites;
    }

    public String getStartingSite() {
        return startingSite.getUrl();
    }

    public int getCrawlingDepth() {
        return this.startingSite.getCrawlDepth();
    }

}


