package at.aau.cleancode.crawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportTest {

    Website mockStartingWebsite;
    Report mockReport;
    Link mockLink;

    Report report;

    @BeforeEach
    void setUp(){
        mockStartingWebsite = mock(Website.class);
        mockReport = mock(Report.class);
        mockLink = mock(Link.class);

        report = null;
    }

    @Test
    void createReport() {
        initReport();

        report.createReport();

        verify(mockStartingWebsite).getCrawlDepth();
        verify(mockStartingWebsite).getLinks();
    }

    @Test
    void getAllWebsites() {
        initReport();

        report.createReport();

        assertEquals(1, report.getAllWebsites().size());
    }

    @Test
    void getStartingSite() {
        initReport();
        String url = "https://wikipedia.org";
        when(mockStartingWebsite.getUrl()).thenReturn(url);

        assertEquals(url, report.getStartingSite());

        verify(mockStartingWebsite).getUrl();
    }

    @Test
    void getCrawlingDepth() {
        initReport();

        assertEquals(2, report.getCrawlingDepth());

        verify(mockStartingWebsite).getCrawlDepth();
    }

    @Test
    void getTargetLanguage() {
        initReport();

        assertEquals("DE", report.getTargetLanguage());
    }

    void initReport(){
        when(mockStartingWebsite.getCrawlDepth()).thenReturn(2);
        List<Link> links = new ArrayList<>();
        links.add(mockLink);
        when(mockStartingWebsite.getLinks()).thenReturn(links);
        when(mockLink.isBroken()).thenReturn(true);

        report = new Report(mockStartingWebsite, "DE");
    }


}