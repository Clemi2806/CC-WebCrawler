package at.aau.cleancode.crawler;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HeadlineTest {

    @ParameterizedTest
    @MethodSource("headingsSource")
    void testGetters(String heading, int depth) {
        Headline headline = new Headline(heading, depth);

        assertEquals(heading, headline.getHeading());
        assertEquals(depth, headline.getDepth());
    }

    private static Stream<Arguments> headingsSource(){
        return Stream.of(
                Arguments.of("This is a heading", 1),
                Arguments.of("Welcome page", 2),
                Arguments.of("Wikipedia", 77));
    }
}