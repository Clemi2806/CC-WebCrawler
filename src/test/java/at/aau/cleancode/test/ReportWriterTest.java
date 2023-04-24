package at.aau.cleancode.test;

import at.aau.cleancode.crawler.*;
import at.aau.cleancode.translation.DeeplTranslator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportWriterTest {

    static DeeplTranslator translator;
    static BufferedWriter writer;
    static Report report;
    static Website website;
    static Headline h1;
    static Headline h2;
    static Link link;
    static Link brokenLink;

    @BeforeAll
    public static void init() throws Exception {
        translator = mock(DeeplTranslator.class);
        writer = mock(BufferedWriter.class);
        setUpWriteReportTest();
    }

    private static void setUpWriteReportTest() throws Exception {
        report = mock(Report.class);
        website = mock(Website.class);
        when(website.getCrawlDepth()).thenReturn(1);
        when(website.getUrl()).thenReturn("https://www.test.at");
        when(website.getTitle()).thenReturn("Title");
        h1 = new Headline("H1", 1);
        h2 = new Headline("H2", 2);
        when(website.getHeadlines()).thenReturn(List.of(h1, h2));
        link = mock(Link.class);
        brokenLink = mock(Link.class);
        when(link.isBroken()).thenReturn(false);
        when(brokenLink.isBroken()).thenReturn(true);
        when(link.getHref()).thenReturn("link 1");
        when(brokenLink.getHref()).thenReturn("link2");
        when(website.getLinks()).thenReturn(List.of(link, brokenLink));
        when(report.getAllWebsites()).thenReturn(List.of(website));
        when(report.getStartingSite()).thenReturn("https://www.test.at");
        when(report.getCrawlingDepth()).thenReturn(1);
        when(translator.getTranslatedLanguages()).thenReturn(Set.of("German", "Portuguese"));
        when(translator.translate(h1.getHeading())).thenReturn("h1");
        when(translator.translate(h2.getHeading())).thenThrow(new Exception());
    }
    @Test
    void writeReport() throws Exception {
        BufferedWriter writer = mock(BufferedWriter.class);
        ReportWriter reportWriter = new ReportWriter(report, translator, writer);
        reportWriter.writeReport();

        verify(report).getAllWebsites();
        verify(report).getStartingSite();
        verify(report, times(2)).getCrawlingDepth();

        verify(website).getCrawlDepth();
        verify(website).getUrl();
        verify(website).getTitle();
        verify(website).getHeadlines();
        verify(website).getLinks();

        verify(translator).translate(h1.getHeading());
        verify(translator).translate(h2.getHeading());

        verify(link).getHref();
        verify(link).isBroken();
        verify(brokenLink).getHref();
        verify(brokenLink).isBroken();
    }

}