package at.aau.cleancode.test;

import at.aau.cleancode.crawler.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebsiteTest {

    private static String websiteTitle = "Wikipedia";
    Link mockSiteUrl;
    Link workingLink;
    Link brokenLink;
    Crawler mockJsoupCrawler;
    Link mockWebsiteLink;
    Headline mockHeader;
    Website website;

    @BeforeEach
    void setUp() {
        mockSiteUrl = mock(Link.class);
        workingLink = mock(Link.class);
        mockJsoupCrawler = mock(JsoupCrawler.class);
        mockWebsiteLink = mock(Link.class);
        mockHeader = mock(Headline.class);

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

        verify(mockJsoupCrawler).getTitle();
    }

    @Test
    void crawlWebsite() throws IOException {
        //when
        initWebsite();

        //then
        website.crawlWebsite();

        //verify
        verify(mockJsoupCrawler).getTitle();
        verify(mockJsoupCrawler).getHeadlines();
        verify(mockJsoupCrawler).getLinks();
    }

    void setUpBrokenWebsite(){
        brokenLink = mock(Link.class);
        website = new Website(brokenLink, 1);
        when(brokenLink.isBroken()).thenReturn(true);
        when(brokenLink.getHref()).thenReturn("ioexception.org");
    }
    @Test
    void crawlBrokenWebsite() {
        setUpBrokenWebsite();

        website.crawlWebsite();

        assertTrue(website.getLinks().isEmpty());
        assertTrue(website.getHeadlines().isEmpty());

        verify(brokenLink).isBroken();
        verify(brokenLink).getHref();
    }

    @Test
    void getHeadlines() throws IOException {
        initWebsite();

        website.crawlWebsite();

        assertEquals(1, website.getHeadlines().size());

        verify(mockJsoupCrawler).getHeadlines();
    }

    @Test
    void getLinks() throws IOException {
        initWebsite();

        website.crawlWebsite();

        assertEquals(1, website.getLinks().size());

        verify(mockJsoupCrawler).getLinks();
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
        when(mockHeader.getHeading()).thenReturn("Header Text");

        List<Link> linkElements = new ArrayList<>();
        linkElements.add(workingLink);
        List<Headline> headerElements = new ArrayList<>();
        headerElements.add(mockHeader);

        website = new Website(mockSiteUrl, 1);
        when(mockSiteUrl.isBroken()).thenReturn(false);
        when(mockSiteUrl.getHref()).thenReturn("A href");
        website.setCrawler(mockJsoupCrawler);

        when(mockJsoupCrawler.getTitle()).thenReturn(websiteTitle);
        when(mockJsoupCrawler.getHeadlines()).thenReturn(headerElements);
        when(mockJsoupCrawler.getLinks()).thenReturn(linkElements);
    }
}