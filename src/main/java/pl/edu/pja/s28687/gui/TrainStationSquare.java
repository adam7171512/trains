package pl.edu.pja.s28687.Gui;

import pl.edu.pja.s28687.Gui.Canvas;
import pl.edu.pja.s28687.TrainStation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class TrainStationSquare extends JPanel {
    private Graphics2D point;
    private int x;
    private int y;

    public TrainStationSquare(TrainStation ts){
        initialize(ts);
    }

    private void initialize(TrainStation ts){
        this.setOpaque(true);
        this.setVisible(true);
        this.x = (int) ts.getCoordinates().getX();
        this.y = (int) ts.getCoordinates().getY();
        this.setBounds(x,
                y,
                5,
                5);

    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        setBackground(Color.RED);
        setSize(getPreferredSize());
    }
}
