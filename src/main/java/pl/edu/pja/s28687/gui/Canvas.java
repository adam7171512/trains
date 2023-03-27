package pl.edu.pja.s28687.Gui;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Logistics.RailroadLink;
import pl.edu.pja.s28687.TrainStation;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Canvas extends Thread {
    private JFrame jFrame;
    private Map map;
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
        map = new Map(locoBase);
        jFrame.add(map);
        jFrame.getContentPane().add(map);
        jFrame.getContentPane().setComponentZOrder(map, 0);
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
        LocoBase locoBase = LocoBase.getInstance();
        List<RailroadLink> links = locoBase.getRailroadConnections().stream().toList();
        for(RailroadLink link: links){
            map.addRails(link);
        }


        List<Locomotive> locomotiveList = locoBase.getLocomotiveList();
        for (Locomotive loc : locomotiveList){
            TrainSetRepresentation trainSetRepresentation = new TrainSetRepresentation(loc);
            loc.setVisualRepresentation(trainSetRepresentation);
            map.addTrain(trainSetRepresentation);
            map.addTrainLabel(loc);

        }

        for (TrainStation ts : locoBase.getTrainStations()){
            map.addTrainStation(ts);
        }

        }
    }

