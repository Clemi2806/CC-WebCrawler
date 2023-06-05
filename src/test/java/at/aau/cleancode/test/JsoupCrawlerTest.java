package at.aau.cleancode.test;

import at.aau.cleancode.crawler.Crawler;
import at.aau.cleancode.crawler.Headline;
import at.aau.cleancode.crawler.JsoupCrawler;
import at.aau.cleancode.crawler.Link;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsoupCrawlerTest {

    static String href = "https://aau.at";
    static String page = "<head><title>Testsite</title></head><body><h1>Heading 1</h1><a href=\"testsite.com\"></a><h2>Heading 2</body>";
    JsoupCrawler crawler;
    Document document = Jsoup.parse(page);

    @BeforeEach
    void setUp() {
        crawler = new JsoupCrawler(href);
        crawler.setDocument(document);
    }

    @Test
    void getHeadlines() throws IOException {
        List<Headline> expectedHeadlines = new ArrayList<>();
        expectedHeadlines.add(new Headline("Heading 1", 1));
        expectedHeadlines.add(new Headline("Heading 2", 2));

        List<Headline> actualHeadlines = crawler.getHeadlines();

        assertTrue(equalHeadlineList(expectedHeadlines, actualHeadlines));
    }

    private boolean equalHeadlineList(List<Headline> list1, List<Headline> list2) {
        if(list1.size()!= list2.size()){
            return false;
        }

        for(int i = 0; i < list1.size(); i++){
            if(!list1.get(i).getHeading().equals(list2.get(i).getHeading())){
                return false;
            }
            if(list1.get(i).getDepth() != (list2.get(i).getDepth())){
                return false;
            }
        }
        return true;
    }

    @Test
    void getLinks() throws IOException {
        List<Link> expectedLinks = new ArrayList<>();
        expectedLinks.add(new Link("testsite.com"));

        List<Link> actualLinks = crawler.getLinks();

        assertTrue(equalLinkList(expectedLinks, actualLinks));
    }

    private boolean equalLinkList(List<Link> list1, List<Link> list2) {
        if(list1.size()!= list2.size()){
            return false;
        }

        for(int i = 0; i < list1.size(); i++){
            if(!list1.get(i).getHref().equals(list2.get(i).getHref())){
                return false;
            }
        }
        return true;
    }

    @Test
    void getTitle() throws IOException {
        String expectedTitle = "Testsite";
        String actualTitle = crawler.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    void testInstance() {
        assertInstanceOf(Crawler.class, crawler);
    }
}