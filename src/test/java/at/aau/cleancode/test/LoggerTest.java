package at.aau.cleancode.test;

import at.aau.cleancode.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoggerTest {

    @BeforeEach
    public void resetLogger() throws NoSuchFieldException, IllegalAccessException {
        Field instance = Logger.class.getDeclaredField("logger");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void errorLoggingTest(){
        Logger logger = Logger.getInstance();
        logger.error("ERROR");
        assertEquals("<span style=\"color:red\">E: ERROR</span>", logger.getLogs().get(0));
    }

    @Test
    public void infoLoggingTest(){
        Logger logger = Logger.getInstance();
        logger.info("INFO");
        assertEquals("<span style=\"color:blue\">I: INFO</span>", logger.getLogs().get(0));
    }

    @Test
    public void loggingTest(){
        Logger logger = Logger.getInstance();
        logger.log("LOG");
        assertEquals("LOG", logger.getLogs().get(0));
    }

    @Test
    public void mixedLoggingTest(){
        Logger logger = Logger.getInstance();
        logger.error("ERROR");
        logger.info("INFO");
        logger.log("LOG");
        assertEquals("<span style=\"color:red\">E: ERROR</span>", logger.getLogs().get(0));
        assertEquals("<span style=\"color:blue\">I: INFO</span>", logger.getLogs().get(1));
        assertEquals("LOG", logger.getLogs().get(2));
    }
}
