package at.aau.cleancode.crawler;

public class Headline {
    private String heading;
    private int depth;

    public Headline(String heading, int depth) {
        this.heading = heading;
        this.depth = depth;
    }

    public String getHeading() {
        return heading;
    }

    public int getDepth() {
        return depth;
    }
}
