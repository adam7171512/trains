package pl.edu.pja.s28687.gui;
import pl.edu.pja.s28687.logistics.LocoBase;

import javax.swing.*;
import java.awt.*;

public class Canvas2 {
    private static Canvas2 canvas;
    private JFrame frame;
    private final LocoBase locoBase;
    private RailroadsView railroadsView;
    private TrainSetView trainSetView;

    private Canvas2(LocoBase locoBase) {
        this.locoBase = locoBase;
        init();
        initBase();
    }
    private void initBase(){
        locoBase.getTrainStations().forEach(railroadsView::addTrainStation);
        locoBase.getRailroadConnections().forEach(railroadsView::addRailroadLink);
        locoBase.getLocomotiveList().forEach(l -> l.setView(trainSetView));
        locoBase.setRailroadsView(railroadsView);
        locoBase.setTrainSetView(trainSetView);
    }
    public void show(){
        frame.setVisible(true);
    }
    private void init(){
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(1000, 1000);
        trainSetView = new TrainSetView();
        railroadsView = new RailroadsView(locoBase);
        frame.add(trainSetView, BorderLayout.CENTER);
        frame.add(railroadsView, BorderLayout.CENTER);
        frame.addKeyListener(railroadsView);
    }

    public static Canvas2 getInstance(LocoBase locoBase){
        if (canvas == null){
            canvas = new Canvas2(locoBase);
        }
        return canvas;
    }

}
