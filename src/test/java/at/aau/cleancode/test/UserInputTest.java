package at.aau.cleancode.test;

import at.aau.cleancode.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserInputTest {

    static Scanner userInputScanner;

    @BeforeEach
    public void createMock() {
        userInputScanner = mock(Scanner.class);
        Main.userInputScanner = userInputScanner;
    }

    @Test
    public void testTargetLanguage() {
        createMock(); // Is not necessary, but mvn test won't pass because userInputScanner is null
        when(userInputScanner.nextLine()).then(new Answer<String>() {
            int count = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return count++ == 0 ? "dsa" : "DE";
            }
        });
        String lang = Main.readTargetLanguage();
        verify(userInputScanner, times(2)).nextLine();
        assertEquals(lang, "DE");
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://www.example.com https://www.test.net http://www.abc.at", "https://www.test.net http://www.abc.at", "http://www.abc.at"})
    public void testUrl(String url) {
        when(userInputScanner.nextLine()).thenReturn(url);
        int numberOfWebsites = url.split(" ").length;
        List<String> targetUrl = Main.readTargetUrl();
        assertEquals(numberOfWebsites, targetUrl.size());
        verify(userInputScanner).nextLine();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
    public void testValidCrawlDepth(int depth) {
        when(userInputScanner.nextLine()).thenReturn("" + depth);
        int crawlDepth = Main.readCrawlDepth();
        assertEquals(depth, crawlDepth);
        verify(userInputScanner).nextLine();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-12", "-2", "-3", "-100", "abc", "def"})
    public void testInvalidCrawlDepth(String depth) {
        when(userInputScanner.nextLine()).thenReturn(depth);
        int crawlDepth = Main.readCrawlDepth();
        assertEquals(2, crawlDepth);
        verify(userInputScanner).nextLine();
    }


}
