package at.aau.cleancode.crawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LinkTest {

    Link link;
    HttpURLConnection mockConnection;

    @BeforeEach
    void setUp(){
        mockConnection = mock(HttpURLConnection.class);
        link = null;
    }

    @ParameterizedTest
    @MethodSource("linkSource")
    void testGetters(String href, boolean expectedBroken) throws IOException {
        int responseCode = 200;
        if(expectedBroken)
            responseCode = 404;

        when(mockConnection.getResponseCode()).thenReturn(responseCode);
        link = new Link(href);


        assertEquals(href, link.getHref());
        assertEquals(expectedBroken, link.isBroken());
    }

    private static Stream<Arguments> linkSource(){
        return Stream.of(
                Arguments.of("github", true),
                Arguments.of("https://google.com", false),
                Arguments.of("https://www.wikipedia.org", false));
    }
}