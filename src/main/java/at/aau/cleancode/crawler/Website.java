package at.aau.cleancode.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Website {
    private final Link url;
    private String title;
    private List<Headline> siteHeadlines;
    private List<Link> siteLinks;
    private int crawlDepth;
    private Crawler crawler;

    public Website(Link siteLink, int crawlDepth) {
        this.url = siteLink;
        this.siteHeadlines = new ArrayList<>();
        this.siteLinks = new ArrayList<>();
        this.crawlDepth = crawlDepth;
        crawler = null;
    }

    public Website(String siteLink, int crawlDepth) {
        this.url = new Link(siteLink);
        this.siteHeadlines = new ArrayList<>();
        this.siteLinks = new ArrayList<>();
        this.crawlDepth = crawlDepth;
        crawler = null;
    }

    public String getUrl() {
        return url.getHref();
    }

    public int getCrawlDepth() {
        return crawlDepth;
    }

    public String getTitle() {
        return this.title;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public void crawlWebsite() {
        try {
            if (this.url.isBroken()) {
                throw new IOException();
            }
            if (crawler == null) {
                crawler = new JsoupCrawler(this.url.getHref());
            }
            this.title = crawler.getTitle();
            this.siteHeadlines = crawler.getHeadlines();
            this.siteLinks = crawler.getLinks();
        } catch (IOException e) {
            System.out.println("Error while accessing website " + this.url.getHref());
        }
    }

    public List<Headline> getHeadlines() {
        return this.siteHeadlines;
    }

    public List<Link> getLinks() {
        return this.siteLinks;
    }
}


