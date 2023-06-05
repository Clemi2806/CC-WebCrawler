package at.aau.cleancode.crawler;

import at.aau.cleancode.logging.Logger;
import at.aau.cleancode.translation.Translator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReportBuilder {

    private List<String> targetUrls;
    private int crawlDepth;
    private final List<Report> reports;

    public ReportBuilder(List<String> targetUrls, int crawlDepth) {
        this.targetUrls = targetUrls;
        this.crawlDepth = crawlDepth;
        reports = new ArrayList<>();
    }

    public void buildReports() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(targetUrls.size());
        for(String targetUrl : targetUrls){
            Report report = new Report(targetUrl, crawlDepth);
            synchronized (this.reports) {
                this.reports.add(report);
            }
            ReportGenerator reportGenerator = new ReportGenerator(report, countDownLatch);
            Thread t = new Thread(reportGenerator);
            t.start();
        }
        countDownLatch.await();
    }

    public void printReports(Translator translator, BufferedWriter writer) throws IOException {
        if(writer == null) {
            long currentTime = System.currentTimeMillis();
            writer = new BufferedWriter(new FileWriter("report-" + currentTime + ".md"));
        }
        ReportWriter reportWriter;
        for(Report report : this.reports) {
            try {
                reportWriter = new ReportWriter(report, translator, writer);
            } catch (IOException e) {
                System.out.println("Unable to create report file!");
                return;
            }
            try {
                reportWriter.writeReport();
                writer.append("---\n");
                writer.append("---\n");
            } catch (IOException e) {
                System.out.println("Unable to write report file!");
                return;
            }
        }
        writer.append(getLogs());
        writer.flush();
        writer.close();
    }

    private String getLogs(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("# Logs").append("\n");
        List<String> logs = Logger.getInstance().getLogs();
        if(logs.isEmpty()){
            stringBuilder.append("<br>").append("No logs reported!").append("\n");
        } else {
            for(String s : logs) {
                stringBuilder.append("<br>").append(s).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private class ReportGenerator implements Runnable{

        private Report report;
        private CountDownLatch countDownLatch;

        public ReportGenerator(Report report, CountDownLatch countDownLatch) {
            this.report = report;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.printf("Staring crawling website: %s ...\n", report.getStartingSite());
            report.createReport();
            countDownLatch.countDown();
        }
    }
}
