package at.aau.cleancode.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Website {
    private final String siteLink;
    private String title;
    private final int crawlingDepth;
    private Elements headings;
    private Elements links;

    public Website(String siteLink, int crawlingDepth) {
        this.siteLink = siteLink;
        this.crawlingDepth = crawlingDepth;
    }

    public void addHeading(Element heading){
        headings.add(heading);
    }

    public void addLink(Element link){
        links.add(link);
    }

    public int getCrawlingDepth(){
        return this.crawlingDepth;
    }

    public void crawlWebsite() throws IOException {
        Document doc = Crawler.getDocument(this.siteLink);
        this.title = doc.title();
        this.links = doc.select("a[href]");
        this.headings = doc.select("h");
        System.out.println(headings);
    }

    @Override
    public String toString() {
        String website = "";


        return website;
    }
}


