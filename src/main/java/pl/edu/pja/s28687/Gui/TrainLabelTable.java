package pl.edu.pja.s28687.Gui;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.Coordinates;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Logistics.RailroadLink;
import pl.edu.pja.s28687.TrainStation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrainLabelTable extends JPanel {
    private List<TrainSetLabel> trainLabels;

    public TrainLabelTable(){
        initialize();
    }

    private void initialize(){
        trainLabels = new ArrayList<>();
        setVisible(true);
        FlowLayout  flowLayout= new FlowLayout(FlowLayout.LEFT);
        flowLayout.setVgap(20);
        flowLayout.setHgap(200);
        setLayout(flowLayout);
        setOpaque(false);
//        setAlignmentX(LEFT_ALIGNMENT);
        setBounds(1200, 20, 600, 1400);

    }

    @Override
    public void paintComponent(Graphics g){

    }

    public void addTrainLabel(Locomotive locomotive){
        TrainSetLabel label= new TrainSetLabel(locomotive);
        add(label);
    }
}

