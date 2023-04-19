package at.aau.cleancode.crawler;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WebsiteTest {

    @Test
    void createWebsite() {
        Website site = new Website("http://wikipedia.org",1);

        site.crawlWebsite();

        assertEquals("Wikipedia", site.getTitle());
    }

    @Test
    void addHeading() {
    }

    @Test
    void addLink() {
    }

    @Test
    void getCrawlingDepth() {
    }

    @Test
    void getTitle() {
    }

    @Test
    void crawlWebsite() {
    }

    @Test
    void getHeadings() {
    }

    @Test
    void getLinks() {
    }
}