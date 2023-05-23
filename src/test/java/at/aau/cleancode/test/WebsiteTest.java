package at.aau.cleancode.test;

import at.aau.cleancode.crawler.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class WebsiteTest {

    private static String websiteTitle = "Wikipedia";
    Link mockSiteUrl;
    Link workingLink;
    Link brokenLink;
    Crawler mockJsoupCrawler;
    Link linkElement;
    Headline headerElement;
    Website website;

    @BeforeEach
    void setUp() {
        mockSiteUrl = mock(Link.class);
        workingLink = mock(Link.class);
        brokenLink = mock(Link.class);
        mockJsoupCrawler = mock(JsoupCrawler.class);
        linkElement = mock(Link.class);
        headerElement = mock(Headline.class);

        website = null;
    }


    @Test
    void createWebsite() {
        assertNotNull(new Website("testurl", 1));
    }

    @Test
    void getTitle() throws IOException {
        initWebsite();

        website.crawlWebsite();

        assertEquals(websiteTitle, website.getTitle());
    }

    @Test
    void crawlWebsite() throws IOException {
        //when
        initWebsite();

        //then
        website.crawlWebsite();

        //verify
    }

    @Test
    void getHeadlines() throws IOException {
        initWebsite();

        website.crawlWebsite();

        assertEquals(1, website.getHeadlines().size());
    }

    @Test
    void getLinks() throws IOException {
        initWebsite();

        website.crawlWebsite();

        assertEquals(1, website.getLinks().size());
    }

    @Test
    void getUrl() {
        website = new Website("https://wikipedia.org", 1);
        when(workingLink.getHref()).thenReturn("https://wikipedia.org");
        assertEquals("https://wikipedia.org", workingLink.getHref());
        verify(workingLink, times(1)).getHref();
    }

    @Test
    void getCrawlDepth() throws IOException {
        initWebsite();
        assertEquals(1, website.getCrawlDepth());
    }

    void initWebsite() throws IOException {
        when(headerElement.getHeading()).thenReturn("Header Text");
        when(linkElement.getHref()).thenReturn("A href");

        List<Link> linkElements = new ArrayList<>();
        linkElements.add(workingLink);
        List<Headline> headerElements = new ArrayList<>();
        headerElements.add(headerElement);

        website = new Website(mockSiteUrl, 1);
        when(mockSiteUrl.isBroken()).thenReturn(false);
        website.setCrawler(mockJsoupCrawler);

        when(mockJsoupCrawler.getTitle()).thenReturn(websiteTitle);
        when(mockJsoupCrawler.getHeadlines()).thenReturn(headerElements);
        when(mockJsoupCrawler.getLinks()).thenReturn(linkElements);
    }
}