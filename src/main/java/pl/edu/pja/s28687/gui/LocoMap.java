package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RailroadLink;
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

public class LocoMap extends JPanel implements MouseListener, Runnable {
    private LocoMap locoMap;
    private TrainLabelTable trainLabelTable;
    private List<TrainSetLabel> trainLabels;
    private List<TrainSetRepresentation> trains;
    private List<Line2D> rails;
    private List<Point2D> stations;
    private LocoBase locoBase;
    private List<TrainStationLabel> trainStationLabels;
    private Point2D stationMarked;

    public LocoMap(LocoBase locoBase){
        this.locoMap = this;
        this.locoBase = locoBase;
        initialize();
    }

    private void initialize(){
        trainLabelTable = new TrainLabelTable();
        add(trainLabelTable);
        trains = new ArrayList<>();
        rails = new ArrayList<>();
        stations = new ArrayList<>();
        trainLabels = new ArrayList<>();
        trainStationLabels = new ArrayList<>();
        setOpaque(false);
        setVisible(true);
        setSize(3400, 1200);
        addMouseListener(this);
        setLayout(null);

    }

    public List<TrainSetRepresentation> getTrains() {
        return trains;
    }

    public void addTrainStation(TrainStation ts){
        TrainStationLabel label = new TrainStationLabel(ts);
        add(label);
        trainStationLabels.add(label);
        stations.add(new Point2D.Double(label.getX(), label.getY()));
    }

    public void addTrainLabel(Locomotive locomotive){
        trainLabelTable.addTrainLabel(locomotive);

    }

    public void addTrain(TrainSetRepresentation train){
        trains.add(train);
        this.add(train);
    }

    public void addRails(RailroadLink link) {
        int x1= (int) link.getStation1().getCoordinates().getX();
        int x2= (int) link.getStation2().getCoordinates().getX();
        int y1= (int) link.getStation1().getCoordinates().getY();
        int y2= (int) link.getStation2().getCoordinates().getY();
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        rails.add(line);
    }

    public void addManyRails(List<RailroadLink> links) {

        for (RailroadLink link : links){
            int x1 = (int) link.getStation1().getCoordinates().getX();
            int x2 = (int) link.getStation2().getCoordinates().getX();
            int y1 = (int) link.getStation1().getCoordinates().getY();
            int y2 = (int) link.getStation2().getCoordinates().getY();
            Line2D line = new Line2D.Double(x1, y1, x2, y2);
            rails.add(line);
    }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));
        for (int i = 0; i < rails.size(); i++) {
            g2.draw(rails.get(i));
        }
        g2.setColor(Color.BLUE);
        for (Point2D point : stations) {
            Ellipse2D circle = new Ellipse2D.Double(point.getX() - 2, point.getY() - 2, 4, 4);
            g2.fill(circle);
            g2.draw(circle);
        }

        if (stationMarked != null) {
            g2.setColor(Color.DARK_GRAY);
            Ellipse2D circle = new Ellipse2D.Double(stationMarked.getX() - 2, stationMarked.getY() - 2, 20, 20);
            g2.fill(circle);
            g2.draw(circle);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)){
            leftMouseButton(e);
        }
        else if (SwingUtilities.isRightMouseButton(e)){
            rightMouseButton(e);
        }
        repaint();
    }

    public void leftMouseButton(MouseEvent e){
        TrainStation ts = new TrainStation("Custom station", new Coordinates(e.getX(), e.getY()));
        List<TrainStation> destinations = locoBase.getTrainStations()
                .stream()
                .sorted(Comparator.comparingDouble(
                        ((TrainStation tsDest)
                                ->
                                locoBase.calcDistance
                                                (ts, tsDest)
                                        .doubleValue())))
                .toList();

        addTrainStation(ts);
        locoBase.addTrainStation(ts);

        if(!destinations.isEmpty()) {
            RailroadLink railroadLink = new RailroadLink(ts, destinations.get(0));
            addRails(railroadLink);
            locoBase.registerRailroadConnection(railroadLink);
        }
    }

    public void rightMouseButton(MouseEvent e){
        if (locoBase.getTrainStations().size() > 2){
            Coordinates coordinates = new Coordinates(e.getX(), e.getY());
            List<TrainStation> stationsToJoin = locoBase.getTrainStations().stream().sorted(Comparator.comparingDouble(
                    ((TrainStation ts)
                            ->
                            Coordinates.distance(
                                            ts.getCoordinates(), coordinates)))).limit(2)
                    .toList();

            RailroadLink railroadLink = new RailroadLink(stationsToJoin.get(0), stationsToJoin.get(1));
            addRails(railroadLink);
            locoBase.registerRailroadConnection(railroadLink);
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void run() {
        while (true){
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void lightUpStation(TrainStation station) {
        stationMarked = new Point2D.Double(station.getCoordinates().getX(), station.getCoordinates().getY());
    }
}

