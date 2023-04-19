package at.aau.cleancode.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private final Link startingSite;
    private final int crawlingDepth;
    List<Website> websites;

    public Report(String startingSite, int crawlingDepth) {
        this.startingSite = new Link(startingSite);
        this.crawlingDepth = crawlingDepth;
        websites = new ArrayList<>();
    }

    public void createReport() {
        Website startingSite = new Website(this.startingSite.getHref());
        startingSite.crawlWebsite();
        websites.add(startingSite);
        if(this.crawlingDepth <= 1){
            return;
        }
        for(Link link : startingSite.getLinks()){
            if(link.isBroken()){
                continue;
            }
            Report report = new Report(link.getHref(), this.crawlingDepth-1);
            report.createReport();
            websites.addAll(report.getAllWebsites());
        }
    }

    public List<Website> getAllWebsites(){
        return this.websites;
    }
}


