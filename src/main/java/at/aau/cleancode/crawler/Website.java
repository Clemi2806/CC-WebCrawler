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
    private final int crawlingDepth;
    private List<Heading> siteHeadings;
    private List<Link> siteLinks;

    public Website(String siteLink, int crawlingDepth) {
        this.url = new Link(siteLink);
        this.crawlingDepth = crawlingDepth;
        this.siteHeadings = new ArrayList<>();
        this.siteLinks = new ArrayList<>();
    }

    public int getCrawlingDepth(){
        return this.crawlingDepth;
    }

    public String getTitle(){
        return this.title;
    }

    public void crawlWebsite() throws IOException {
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
                this.siteHeadings.add(new Heading(head.ownText(), i));
            }
        }
    }

    private void fetchLinks(Document document){
        Elements links = document.select("a[href]");
        for(Element link : links){
            this.siteLinks.add(new Link(link.attr("href")));
        }
    }

    public List<String> getHeadings(){
        List<String> heads = new ArrayList<>();
        for(Heading head : this.siteHeadings){
            heads.add(head.getHeading());
        }
        return heads;
    }

    public List<Link> getLinks(){
        return siteLinks;
    }
}


