package at.aau.cleancode.crawler;

import java.net.HttpURLConnection;
import java.net.URL;

public class Link {
    private final String href;
    private HttpURLConnection connection;

    public Link(String href) {
        this.href = href;
        connection = null;
    }

    public boolean isBroken() {
        return isBrokenUrl(this.href);
    }

    public String getHref() {
        return href;
    }

    public void setConnection(HttpURLConnection connection){
        this.connection = connection;
    }

    private boolean isBrokenUrl(String href) {
        try {
            URL url = new URL(href);
            if(connection == null){
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            return (responseCode >= 400);
        } catch (Exception e) {
            return true;
        }
    }


}
