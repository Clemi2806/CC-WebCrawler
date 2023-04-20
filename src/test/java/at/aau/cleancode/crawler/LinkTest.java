package at.aau.cleancode.crawler;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

    @ParameterizedTest
    @MethodSource("linkSource")
    void testGetters(String href, boolean expectedBroken) {
        Link link = new Link(href);

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