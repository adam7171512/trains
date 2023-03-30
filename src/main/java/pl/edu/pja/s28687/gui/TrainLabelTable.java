package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.Locomotive;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

