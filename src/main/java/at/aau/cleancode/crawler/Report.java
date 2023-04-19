package at.aau.cleancode.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private final String startingSite;
    private final int crawlingDepth;
    ArrayList<Website> websites;

    public Report(String startingSite, int crawlingDepth) {
        this.startingSite = startingSite;
        this.crawlingDepth = crawlingDepth;
        websites = new ArrayList<>();
    }

    public void createReport() throws IOException {
        Website startingSite = new Website(this.startingSite, this.crawlingDepth);
        startingSite.crawlWebsite();
        websites.add(startingSite);
        if(this.crawlingDepth <= 1){
            return;
        }
        for(String link : startingSite.getLinks()){
            Report report = new Report(link, this.crawlingDepth-1);
            report.createReport();
            websites.addAll(report.getAllWebsites());
        }
    }

    public List<Website> getAllWebsites(){
        return this.websites;
    }
}


