package at.aau.cleancode.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReportBuilder {

    private List<String> targetUrls;
    private int crawlDepth;

    public ReportBuilder(List<String> targetUrls, int crawlDepth) {
        this.targetUrls = targetUrls;
        this.crawlDepth = crawlDepth;
    }

    public List<Report> buildReports() throws InterruptedException {
        List<Report> reports = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(targetUrls.size());
        for(String targetUrl : targetUrls){
            Report report = new Report(targetUrl, crawlDepth);
            reports.add(report);
            ReportGenerator reportGenerator = new ReportGenerator(report, countDownLatch);
            Thread t = new Thread(reportGenerator);
            t.start();
        }
        countDownLatch.await();
        return reports;
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
