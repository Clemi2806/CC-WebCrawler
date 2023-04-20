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

    public Website(String siteLink, int crawlDepth) {
        this.url = new Link(siteLink);
        this.siteHeadlines = new ArrayList<>();
        this.siteLinks = new ArrayList<>();
        this.crawlDepth = crawlDepth;
    }

    public String getTitle(){
        return this.title;
    }

    public void crawlWebsite() {
        try{
            if(this.url.isBroken()){
                throw new IOException();
            }
            Document document = Crawler.getDocument(this.url.getHref());
            this.title = document.title();
            fetchLinks(document);
            fetchHeadings(document);
        }catch (IOException e){
            System.out.println("Error while accessing website " + this.url.getHref());
        }
    }

    private void fetchHeadings(Document document){
        for(int i = 1; i <= 6; i++){
            Elements heading = document.select("h"+i);
            for(Element head : heading){
                this.siteHeadlines.add(new Headline(head.text(), i));
            }
        }
    }

    private void fetchLinks(Document document){
        Elements links = document.select("a[href]");
        for(Element link : links){
            this.siteLinks.add(new Link(link.attr("href")));
        }
    }

    public List<Headline> getHeadings(){
        return this.siteHeadlines;
    }

    public List<Link> getLinks(){
        return this.siteLinks;
    }

    public String getUrl() {
        return url.getHref();
    }

    public int getCrawlDepth() {
        return crawlDepth;
    }
}


