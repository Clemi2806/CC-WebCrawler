package at.aau.cleancode.test;

import at.aau.cleancode.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserInputTest {

    static Scanner userInputScanner;

    @BeforeAll
    public static void initMockSystemIn(){
        userInputScanner = mock(Scanner.class);
        Main.userInputScanner = userInputScanner;
    }

    @BeforeEach
    public void resetMock(){
        reset(userInputScanner);
    }

    @ParameterizedTest
    @ValueSource(strings = {"www.example.com", "www.test.net", "www.abc.at"})
    public void testUrl(String url){
        when(userInputScanner.nextLine()).thenReturn(url);
        String targetUrl = Main.readTargetUrl();
        assertEquals(url, targetUrl);
        verify(userInputScanner).nextLine();
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7})
    public void testValidCrawlDepth(int depth){
        when(userInputScanner.nextLine()).thenReturn(""+depth);
        int crawlDepth = Main.readCrawlDepth();
        assertEquals(depth, crawlDepth);
        verify(userInputScanner).nextLine();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-12", "-2", "-3", "-100", "abc", "def"})
    public void testInvalidCrawlDepth(String depth){
        when(userInputScanner.nextLine()).thenReturn(depth);
        int crawlDepth = Main.readCrawlDepth();
        assertEquals(2, crawlDepth);
        verify(userInputScanner).nextLine();
    }

}
