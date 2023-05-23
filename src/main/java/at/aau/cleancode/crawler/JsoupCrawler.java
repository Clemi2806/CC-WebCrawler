package at.aau.cleancode.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupCrawler implements Crawler{
    private String href;
    private Document document;
    List<Headline> headlines;
    List<Link> links;
    public JsoupCrawler(String href) {
        super();
        this.href = href;
        headlines = new ArrayList<>();
        links = new ArrayList<>();
    }

    private Document getDocument() throws IOException {
        if(this.document == null){
            return Jsoup.connect(this.href).get();
        }
        return this.document;
    }

    @Override
    public List<Headline> getHeadlines() throws IOException {
        if(headlines.isEmpty()){
            for (int i = 1; i <= 6; i++) {
                Elements headlines = getDocument().select("h" + i);
                for (Element headline : headlines) {
                    this.headlines.add(new Headline(headline.text(), i));
                }
            }
        }
        return headlines;
    }

    @Override
    public List<Link> getLinks() throws IOException {
        if(links.isEmpty()){
            Elements links = getDocument().select("a[href]");
            for (Element link : links) {
                this.links.add(new Link(link.attr("href")));
            }
        }
        return links;
    }

    @Override
    public String getTitle() throws IOException {
        return getDocument().title();
    }
}
