package pl.edu.pja.s28687.Gui;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.Coordinates;

import java.awt.Point;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrainSetRepresentation extends JComponent {
    private Locomotive loc;
    private List<Point> sourceAndDest;
    private Coordinates coordinates;
    private List<Line2D> road;
    private List<Point> trail;
//    private TrainSetLabel locLabel;

    private Color color;

    public TrainSetRepresentation(Locomotive loc){
        initialize(loc);
    }

    private void initialize(Locomotive loc){
        this.loc = loc;
        trail = new ArrayList<>();
        this.setOpaque(false);
        this.setVisible(true);
//        makeLabel();
        sourceAndDest = new ArrayList<>();
        this.setBounds(0,
                0,
                3400,
                1200);
        Random random = new Random();
        this.color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        this.coordinates = new Coordinates(0, 0);

        sourceAndDest.add(new Point(
                (int) loc.getSourceStation()
                        .getCoordinates()
                        .getX(),
                (int) loc.getSourceStation()
                        .getCoordinates()
                        .getY()));
        sourceAndDest.add(new Point(
                (int) loc.getDestStation()
                        .getCoordinates()
                        .getX(),
                (int) loc.getDestStation()
                        .getCoordinates()
                        .getY()));
//        locLabel = new TrainSetLabel(loc);
//        add(locLabel);

    }

//    private String labelText(){
//        String s = new StringBuilder()
////                .append("Name: ")
////                .append(this.loc.getLocName())
//                .append(" ID: ")
//                .append(this.loc.getId())
////                .append("\nSource : ")
////                .append(this.loc.getSourceStation())
//                .append(" - ")
////                .append(this.loc.getDestStation())
//                .append("\nSpeed : ")
//                .append(this.loc.getCurrentSpeed())
//                .append(" km/h")
////                .append("\n Trip :")
////                .append(loc.getRoad().size()).append(" stops !")
////                .append(loc.getRoad().stream().map(RailroadLink::getDistance).reduce(BigDecimal::add).orElse(BigDecimal.ZERO))
////                .append(" KM distance")
////                .append(loc.getRoad())
////                .append("\n Current segment : ")
////                .append(loc.getCurrentSegment())
////                .append("\n Current station : ")
////                .append(loc.getCurrentTrainStation())
//                .append("\n Trip progress % : ")
//                .append(loc.getCurrentTripProgress())
//                .toString();
//        return s;
//    }

    public void updatePosition(Coordinates newCoordinates){
//        String newText = labelText();
//        locLabel.setPreferredSize(getPreferredSize());
//        locLabel.setText("LO2L");
        if (! this.coordinates.equals(newCoordinates)){
            trail.add(new Point((int) newCoordinates.getX(), (int) newCoordinates.getY()));
        }
        this.coordinates = newCoordinates;

//        remove(locLabel);
//        makeLabel();
//        remove(locLabel);
//        locLabel = new TrainSetLabel(loc);
//        add(locLabel);
//        locLabel.setBounds((int) newCoordinates.getX(), (int) newCoordinates.getY(), 170, 30);
//        locLabel.repaint();
//        locLabel.updateLabel();
//        locLabel.setBounds(840, 200, 500, 500);
//        repaint();

    }

//    public void makeLabel(){
//        locLabel = new JTextArea(labelText());
////        locLabel = new JTextArea("LOL22222");
//        locLabel.setVisible(true);
//        locLabel.setOpaque(false);
//        locLabel.setBackground(new Color(250, 250, 250, 120));
//        locLabel.setFont(new Font(Font.SERIF, Font.BOLD, 9));
//        add(locLabel);
//    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        g2.setColor(color);
        for (int i = 0; i < trail.size(); i++){
            g2.fillOval(trail.get(i).x, trail.get(i).y, 3, 3);
        }
        g2.setColor(color);
        for (int i = 0; i < sourceAndDest.size(); i++){
            Point p = sourceAndDest.get(i);
            g2.fillRect(p.x, p.y, 11, 11);
        }

        g2.fillOval((int) (coordinates.getX() - 5), (int) (coordinates.getY() - 5), 10, 10);
    }

    public Point2D coordinatesToPoint(Coordinates coordinates){
        return new Point2D.Double(coordinates.getX(), coordinates.getY());
    }

}
