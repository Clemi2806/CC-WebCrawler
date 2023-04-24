package at.aau.cleancode.test;

import at.aau.cleancode.crawler.Crawler;
import at.aau.cleancode.crawler.Link;
import at.aau.cleancode.crawler.Website;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebsiteTest {

    Link mockSiteUrl;
    Link workingLink;
    Link brokenLink;
    Crawler mockCrawler;
    Document mockDocument;

    Element linkElement;
    Element headerElement;

    Website website;

    @BeforeEach
    void setUp(){
        mockSiteUrl = mock(Link.class);
        workingLink = mock(Link.class);
        brokenLink = mock(Link.class);
        mockCrawler = mock(Crawler.class);
        mockDocument = mock(Document.class);
        linkElement = mock(Element.class);
        headerElement = mock(Element.class);

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

        assertEquals(mockDocument.title(), website.getTitle());
    }

    @Test
    void crawlWebsite() throws IOException {
        //when
        initWebsite();

        //then
        website.crawlWebsite();

        //verify
        verify(linkElement).attr("href");
        verify(headerElement).text();

        verify(mockDocument).select("h1");
        verify(mockDocument).select("a[href]");
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
        when(headerElement.text()).thenReturn("Header Text");
        when(linkElement.attr("href")).thenReturn("A href");

        Elements linkElements = new Elements(linkElement);
        Elements headerElements = new Elements(headerElement);

        website = new Website(mockSiteUrl, 1);
        when(mockSiteUrl.isBroken()).thenReturn(false);
        website.setCrawler(mockCrawler);

        when(mockCrawler.getDocument(any())).thenReturn(mockDocument);
        when(mockDocument.title()).thenReturn("Wikipedia");
        when(mockDocument.select(anyString())).thenReturn(new Elements());
        when(mockDocument.select("h1")).thenReturn(headerElements);
        when(mockDocument.select("a[href]")).thenReturn(linkElements);
    }
}