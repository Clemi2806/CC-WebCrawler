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
        writer.flush();
        writer.close();
    }

    private String getMainInformation() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("input: ").append(wrapLink(report.getStartingSite())).append(NEWLINE)
                .append(BREAK).append("depth: ").append(report.getCrawlingDepth()).append(NEWLINE)
                .append(BREAK).append("target language: ").append(report.getTargetLanguage()).append(NEWLINE);
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(NEWLINE)
                .append(RULE)
                .append(BREAK).append("link: ").append(wrapLink(website.getUrl()))
                .append(BREAK).append("title: ").append(website.getTitle());

        String headings = getHeadings(website);
        String links = getLinks(website);

        return stringBuilder.toString();
    }

    private String getHeadings(Website website){
        StringBuilder stringBuilder = new StringBuilder();
        for(Heading heading :website.getHeadings()){
            stringBuilder.append("#".repeat(Math.max(0, heading.getDepth())));
            String headingText = heading.getHeading();
            boolean isTranslated = false;
//            try {
//                headingText = translator.translate(headingText);
//            } catch (Exception e) {
//                isTranslated = false;
//            }
            if (isTranslated) {
                stringBuilder.append(" ").append(headingText).append(NEWLINE);
            } else {
                stringBuilder.append(" ").append(headingText).append(" (not translated)").append(NEWLINE);
            }

        }

        return stringBuilder.toString();
    }


    private String getLinks(Website website){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NEWLINE).append("---").append(NEWLINE);

        for(Link link : website.getLinks()){
            stringBuilder.append(BREAK).append(wrapLink(link.getHref()));
            if(link.isBroken()){
                stringBuilder.append(" broken link");
            }
        }

        return stringBuilder.toString();
    }
    private String wrapLink(String link){
        return "<a>" + link + "</a>";
    }

}
