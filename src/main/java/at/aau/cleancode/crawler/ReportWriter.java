package at.aau.cleancode.crawler;

import at.aau.cleancode.translation.DeeplTranslator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportWriter {
    private BufferedWriter writer;
    private Report report;
    private DeeplTranslator translator;

    private static final String NEWLINE = "\n";
    private static final String RULE = "___\n";
    private static final String BREAK = "<br>";

    public ReportWriter(Report report, DeeplTranslator translator, BufferedWriter writer) throws IOException {
        this.report = report;
        this.translator = translator;
        long currentTime = System.currentTimeMillis();
        if(writer == null){
            this.writer = new BufferedWriter(new FileWriter("report-" + currentTime + ".md"));
        } else {
            this.writer = writer;
        }
    }

    public void writeReport() throws IOException {
        writer.append(getMainInformation());
        List<String> parsedWebsites = new ArrayList<>();
        for(Website website : report.getAllWebsites()){
            parsedWebsites.add(getWebsiteInformation(website));
        }
        writer.append(getSourceLanguages());
        for(String websiteString : parsedWebsites){
            writer.append(websiteString);
        }
        writer.flush();
        writer.close();
    }

    private String getMainInformation() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("input: ").append(wrapLink(report.getStartingSite())).append(NEWLINE)
                .append(BREAK).append("depth: ").append(report.getCrawlingDepth()).append(NEWLINE)
                .append(BREAK).append("target language: ").append(translator.getTargetLanguage()).append(NEWLINE);
        return stringBuilder.toString();
    }

    private String getSourceLanguages(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BREAK).append("source languages: ");
        for(String lang : translator.getTranslatedLanguages()){
            stringBuilder.append(lang).append(" ");
        }

        return stringBuilder.toString();
    }

    private String getWebsiteInformation(Website website) {
        int currentDepth = report.getCrawlingDepth() - website.getCrawlDepth();
        String indentation = currentDepth != 0 ? "--".repeat(currentDepth) + "> " : "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(NEWLINE)
                .append(RULE)
                .append(BREAK).append(indentation).append("link: ").append(wrapLink(website.getUrl())).append(NEWLINE)
                .append(BREAK).append(indentation).append("title: ").append(website.getTitle()).append(NEWLINE);

        String headings = getHeadings(website, indentation);
        String links = getLinks(website, indentation);

        stringBuilder
                .append(headings).append(NEWLINE)
                .append(links).append(NEWLINE);
        return stringBuilder.toString();
    }

    private String getHeadings(Website website, String indentation){
        StringBuilder stringBuilder = new StringBuilder();
        for(Headline headline :website.getHeadlines()){
            stringBuilder.append("#".repeat(Math.max(0, headline.getDepth())));
            String headingText = headline.getHeading();
            boolean isTranslated = true;
            try {
                headingText = translator.translate(headingText);
            } catch (Exception e) {
                isTranslated = false;
            }
            stringBuilder.append(" ").append(indentation);
            if (isTranslated) {
                stringBuilder.append(headingText).append(NEWLINE);
            } else {
                stringBuilder.append(headingText).append(" (not translated)").append(NEWLINE);
            }

        }

        return stringBuilder.toString();
    }



    private String getLinks(Website website, String indentation){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NEWLINE).append("---").append(NEWLINE);

        for(Link link : website.getLinks()){
            stringBuilder.append(BREAK).append(indentation).append(wrapLink(link.getHref()));
            if(link.isBroken()){
                stringBuilder.append(" broken link");
            }
            stringBuilder.append(NEWLINE);
        }

        return stringBuilder.toString();
    }
    private String wrapLink(String link){
        return "<" + link + ">";
    }

}
