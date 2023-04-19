package pl.edu.pja.s28687.info;

import pl.edu.pja.s28687.logistics.LocoBase;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AggregateLogger implements Runnable{

    private volatile boolean isLogging = false;
    private static final String LOG_FILE_PATH = "Appstate.txt";
    private PrintWriter logger;
    private final LocoBase locoBase;
    private Thread loggingThread;
    public AggregateLogger(LocoBase locoBase) {
        this.locoBase = locoBase;
    }

    public void startLogging(){
        if (!isLogging) {
            loggingThread = new Thread(this);
            loggingThread.start();
            isLogging = true;
            System.out.println("Logging started");
        }
    }

    public void stopLogging(){
        if (isLogging) {
            loggingThread.interrupt();
            isLogging = false;
            System.out.println("Logging stopped");
        }
    }

    public boolean isLogging() {
        return isLogging;
    }

    @Override
    public void run(){
        while (true) {
            String report = TrainSetInfo.getAggregatedTrainSetsInfo(locoBase);
            if (report.isEmpty()){
                continue;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return;
            }
            try {
                logger = new PrintWriter(new FileWriter(LOG_FILE_PATH, true));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logger.println("Trainset report at: ");
            logger.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            logger.println(report);
            logger.close();
        }
    }
}
