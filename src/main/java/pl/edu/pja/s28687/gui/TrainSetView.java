package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.train.Locomotive;
import pl.edu.pja.s28687.logistics.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class TrainSetView extends JPanel {
    private class LocoNode{
        private Locomotive locomotive;
        private Color color;
        private List<Line2D> route;
        LocoNode(Locomotive locomotive){
            this.locomotive = locomotive;
            Random random = new Random();
            this.color = new Color(random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255));
            this.route = new ArrayList<>();
        }
        Coordinates getCoordinates(){
            return locomotive.getCoordinates();
        }
    }
    Set<Integer> locsIds;
    Set<LocoNode> locoNodes;
    private Color color;

    public TrainSetView(){
        initialize();
    }

    private void initialize(){
        this.locsIds = new HashSet<>();
        this.locoNodes = new CopyOnWriteArraySet<>();
        this.setOpaque(false);
        this.setVisible(true);
        this.setBounds(0,
                0,
                1000,
                1000);
    }

    public void notify(Locomotive locomotive) {
        if (!locsIds.contains(locomotive.getId())) {
            locsIds.add(locomotive.getId());
            locoNodes.add(new LocoNode(locomotive));
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        for (LocoNode locoNode : locoNodes){
            Color color = locoNode.color;
            Coordinates coordinates = locoNode.getCoordinates();
            g.setColor(color);
            g.fillOval((int) (coordinates.getX() - 5), (int) (coordinates.getY() - 5), 10, 10);
            Coordinates source = locoNode.locomotive.getSourceStation().getCoordinates();
            Coordinates destination = locoNode.locomotive.getDestinationStation().getCoordinates();
            g.fillRect((int) source.getX(), (int) source.getY(), 10, 10);
            g.fillRect((int) destination.getX(), (int) destination.getY(), 10, 10);
        }
    }

    public Point2D coordinatesToPoint(Coordinates coordinates){
        return new Point2D.Double(coordinates.getX(), coordinates.getY());
    }

}
