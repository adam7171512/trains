package pl.edu.pja.s28687.info;

import pl.edu.pja.s28687.logistics.LocoBase;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AggregateLogger extends Thread{

    public PrintWriter logger;
    private LocoBase locoBase;
    public AggregateLogger(LocoBase locoBase) {
        this.locoBase = locoBase;
    }

    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                logger = new PrintWriter(new FileWriter("Appstate.txt", true));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logger.println("Trainset report at: ");
            logger.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            logger.println(TrainSetInfo.getAggregatedTrainSetsInfo(locoBase));
            logger.close();
        }
    }
}
