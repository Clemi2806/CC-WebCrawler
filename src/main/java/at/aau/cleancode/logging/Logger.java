package at.aau.cleancode.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {
    private static Logger logger;

    public static synchronized Logger getInstance(){
        if(logger == null) logger = new Logger();
        return logger;
    }

    private List<String> logs;

    private Logger(){
        super();
        this.logs = new ArrayList<>();
    }

    public synchronized void error(String message){
        logs.add("<span style=\"color:red\">E: " + message + "</span>");
    }

    public synchronized void info(String info){
        logs.add("<span style=\"color:blue\">I: " + info + "</span>");
    }

    public synchronized void log(String log) {
        logs.add(log);
    }

    public synchronized List<String> getLogs() {
        return Collections.unmodifiableList(logs);
    }
}
