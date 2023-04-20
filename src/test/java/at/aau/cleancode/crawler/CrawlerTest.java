package at.aau.cleancode.crawler;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CrawlerTest {


    @Test
    void getDocument() {
        try {
            Document doc = Crawler.getDocument("http://www.wikipedia.org");
            System.out.println(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}