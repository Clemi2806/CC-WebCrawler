package at.aau.cleancode.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public String getTitle(){
        return this.title;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public void crawlWebsite() {
        try{
            if(this.url.isBroken()){
                throw new IOException();
            }
            if(crawler == null){
                crawler = new Crawler();
            }
            Document document = crawler.getDocument(this.url.getHref());
            this.title = document.title();
            fetchLinks(document);
            fetchHeadlines(document);
        }catch (IOException e){
            System.out.println("Error while accessing website " + this.url.getHref());
        }
    }

    private void fetchHeadlines(Document document){
        for(int i = 1; i <= 6; i++){
            Elements headlines = document.select("h"+i);
            for(Element headline : headlines){
                this.siteHeadlines.add(new Headline(headline.text(), i));
            }
        }
    }

    private void fetchLinks(Document document){
        Elements links = document.select("a[href]");
        for(Element link : links){
            this.siteLinks.add(new Link(link.attr("href")));
        }
    }

    public List<Headline> getHeadlines(){
        return this.siteHeadlines;
    }

    public List<Link> getLinks(){
        return this.siteLinks;
    }


}


