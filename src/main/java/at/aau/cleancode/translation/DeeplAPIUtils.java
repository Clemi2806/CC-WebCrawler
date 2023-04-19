package at.aau.cleancode.translation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DeeplAPIUtils {
    public static String loadApiKey() {
        Properties crawlerProperties = new Properties();
        try {
            crawlerProperties.load(new FileInputStream("crawler.properties"));
        } catch (IOException e) {
            System.err.println("Unable to read crawler.properties file");
            return null;
        }

        return crawlerProperties.getProperty("apikey");
    }
}
