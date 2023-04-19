package at.aau.cleancode.crawler;

public class Heading {
    private String heading;
    private int depth;

    public Heading(String heading, int depth) {
        this.heading = heading;
        this.depth = depth;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
