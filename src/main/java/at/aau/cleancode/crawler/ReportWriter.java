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

    public ReportWriter(Report report, DeeplTranslator translator) throws IOException {
        this.report = report;
        this.translator = translator;
        long currentTime = System.currentTimeMillis();
        this.writer = new BufferedWriter(new FileWriter("report-" + currentTime + ".md"));
    }

    public void writeReport() throws IOException {
        writer.append(getMainInformation());
        List<String> parsedWebsites = new ArrayList<>();
        for(Website website : report.websites){
            parsedWebsites.add(getWebsiteInformation(website));
        }
        writer.append(getSourceLanguages());
        for(String websiteString : parsedWebsites){
            writer.append(websiteString);
        }
    }

    private String getMainInformation() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("input: ").append(wrapLink(report.getStartingSite()))
                .append(breakChars()).append("depth: ").append(report.getCrawlingDepth())
                .append(breakChars()).append("target language: ").append(report.getTargetLanguage());
        return stringBuilder.toString();
    }

    private String getSourceLanguages(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(breakChars()).append("source languages: ");
        for(String lang : translator.getTranslatedLanguages()){
            stringBuilder.append(lang).append(" ");
        }

        return stringBuilder.toString();
    }

    private String getWebsiteInformation(Website website) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n")
                .append(horizontalRule())
                .append(breakChars()).append("link: ").append(wrapLink(website.getUrl()))
                .append(breakChars()).append("title: ").append(website.getTitle());

        String headings = getHeadings(website);
        String links = getLinks(website);

        return stringBuilder.toString();
    }

    private String getHeadings(Website website){
        StringBuilder stringBuilder = new StringBuilder();
        for(Heading heading :website.getHeadings()){
            for (int i = 0; i < heading.getDepth(); i++) {
                stringBuilder.append("#");
            }
            String headingText = heading.getHeading();
            boolean isTranslated = true;
            try {
                headingText = translator.translate(headingText);
            } catch (Exception e) {
                isTranslated = false;
            }
            if (isTranslated) {
                stringBuilder.append(" ").append(headingText).append("\n");
            } else {
                stringBuilder.append(" ").append(headingText).append(" (not translated)").append("\n");
            }

        }

        return stringBuilder.toString();
    }


    private String getLinks(Website website){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n").append("---").append("\n");

        for(Link link : website.getLinks()){
            stringBuilder.append(breakChars()).append(wrapLink(link.getHref()));
            if(link.isBroken()){
                stringBuilder.append(" broken link");
            }
        }

        return stringBuilder.toString();
    }

    private String horizontalRule(){
        return "___\n";
    }

    private String breakChars(){
        return "<br>";
    }

    private String wrapLink(String link){
        return "<a>" + link + "</a>";
    }

}
