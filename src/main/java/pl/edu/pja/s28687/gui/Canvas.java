package pl.edu.pja.s28687.gui;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RailroadLink;
import pl.edu.pja.s28687.TrainStation;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Canvas extends Thread {
    private JFrame jFrame;
    private LocoMap locoMap;
    private LocoBase locoBase;
    public Canvas(LocoBase locoBase){
        this.locoBase = locoBase;
        initialize();
    }

    private void initialize(){
        jFrame = new JFrame();
        jFrame.setSize(2400, 1200);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setResizable(true);
        locoMap = new LocoMap(locoBase);
        jFrame.add(locoMap);
        jFrame.getContentPane().add(locoMap);
        jFrame.getContentPane().setComponentZOrder(locoMap, 0);
//        Thread t = new Thread(map);
//        t.start();
//        this.setLayout(null);

    }

    public void paint(Graphics g) {
        jFrame.paint(g);
    }
    public void repaint(){
        jFrame.repaint();
    }


    public void add(JComponent comp){
        jFrame.add(comp);
    }

    @Override
    public void run(){
        try {
            visualise();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }


    public  void visualise() throws InterruptedException {
        List<RailroadLink> links = locoBase.getRailroadConnections().stream().toList();
        for(RailroadLink link: links){
            locoMap.addRails(link);
        }


        List<TrainSet> trainSets = locoBase.getTrainSets().stream().filter(TrainSet::hasStarted).toList();
        for (TrainSet trainSet : trainSets){
            TrainSetRepresentation trainSetRepresentation = new TrainSetRepresentation(trainSet);
            trainSet.getLocomotive().setVisualRepresentation(trainSetRepresentation);
            locoMap.addTrain(trainSetRepresentation);
            locoMap.addTrainLabel(trainSet.getLocomotive());

        }

        for (TrainStation ts : locoBase.getTrainStations()){
            locoMap.addTrainStation(ts);
        }

        }

    public LocoMap getLocoMap() {
        return locoMap;
    }
}

