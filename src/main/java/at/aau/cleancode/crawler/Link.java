package at.aau.cleancode.crawler;

import java.net.HttpURLConnection;
import java.net.URL;

public class Link {
    private final String href;
    private final boolean isBroken;

    public Link(String href) {
        this.href = href;
        this.isBroken = isBrokenUrl(href);
    }

    public boolean isBroken() {
        return isBroken;
    }

    public String getHref() {
        return href;
    }

    private boolean isBrokenUrl(String href) {
        try {
            URL url = new URL(href);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            return responseCode != 200;
        } catch (Exception e) {
            return true;
        }
    }
}
