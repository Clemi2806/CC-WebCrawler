package at.aau.cleancode.logging;

import java.util.List;

public class Logger {
    private static Logger logger;

    public static Logger getInstance(){
        if(logger == null) logger = new Logger();
        return logger;
    }

    private List<String> logs;

    private Logger(){
        super();
    }

    public void error(String message){
        logs.add("<span style=\"color:red\">E: " + message + "</span>");
    }

    public void info(String info){
        logs.add("<span style=\"color:blue\">I: " + info + "</span>");
    }

    public void log(String log) {
        logs.add(log);
    }

    public List<String> getLogs() {
        return logs;
    }
}
