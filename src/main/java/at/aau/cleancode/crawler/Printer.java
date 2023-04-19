package at.aau.cleancode.crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
    private static BufferedWriter writer;

    public static void initializePrinter(String path) throws IOException {
        writer = new BufferedWriter(new FileWriter(path));
    }

    public static void closeWriter() throws Exception {
        if(writer == null){
            throw new Exception("Path not set");
        }
        writer.flush();
        writer.close();
    }

    public void printReport(Report report) throws Exception {
        if(writer == null){
            throw new Exception("Path not set");
        }
        writer.write(report.toString());
        writer.flush();
    }
}
