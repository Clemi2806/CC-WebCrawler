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
    private ArrayList<Heading> headings;
    private Elements links;

    public Website(String siteLink, int crawlingDepth) {
        this.siteLink = siteLink;
        this.crawlingDepth = crawlingDepth;
        this.headings = new ArrayList<>();
    }

    public int getCrawlingDepth(){
        return this.crawlingDepth;
    }

    public String getTitle(){
        return this.title;
    }

    public void crawlWebsite() throws IOException {
        Document document = Crawler.getDocument(this.siteLink);
        this.title = document.title();
        this.links = document.select("a[href]");
        fetchHeadings(document);
    }

    private void fetchHeadings(Document document){
        for(int i = 1; i <= 6; i++){
            Elements heading = document.select("h"+i);
            for(Element head : heading){
                this.headings.add(new Heading(head.ownText(), i));
            }
        }
    }

    public ArrayList<String> getHeadings(){
        ArrayList<String> heads = new ArrayList<>();
        for(Heading head : this.headings){
            heads.add(head.getHeading());
        }
        return heads;
    }

    @Override
    public String toString() {
        String website = "";


        return headings.toString();
    }
}


