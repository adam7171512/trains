package pl.edu.pja.s28687.logistics;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LocoLogger {
    private final Logger logger;

    public LocoLogger(String loggerName, String logFilePath) {
        logger = Logger.getLogger(loggerName);
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(logFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setLevel(Level.SEVERE);
    }

    public void log(Level level, String message) {
        logger.log(level, message);
    }
}
