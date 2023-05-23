package at.aau.cleancode.crawler;

import java.io.IOException;
import java.util.List;

public interface Crawler {

    List<Headline> getHeadlines() throws IOException;

    List<Link> getLinks() throws IOException;

    String getHeadline() throws IOException;
}
