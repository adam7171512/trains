package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.ResourceContainer;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RailroadLink;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class RailroadsView extends JPanel implements KeyListener {
    private Set<TrainStation> trainStations;
    private Set<RailroadLink> railroadLinks;
    private LocoBase locoBase;
    ResourceContainer resourceContainer = new ResourceContainer();

    public RailroadsView(LocoBase locoBase){
        this.locoBase = locoBase;
        initialize();
    }

    private void initialize(){
        this.trainStations = new CopyOnWriteArraySet<>();
        this.railroadLinks = new CopyOnWriteArraySet<>();
        this.setOpaque(false);
        this.setVisible(true);
        this.setBounds(0,
                0,
                1000,
                1000);
    }

    public void notify(RailroadLink railroadLink) {
        railroadLinks.add(railroadLink);
        repaint();
    }
    public void notify(TrainStation trainStation) {
        trainStations.add(trainStation);
        repaint();
    }

    public void addRailroadLink(RailroadLink railroadLink){
        railroadLinks.add(railroadLink);
        repaint();
    }

    public void addTrainStation(TrainStation trainStation){
        trainStations.add(trainStation);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        for (TrainStation trainStation : trainStations){
            Coordinates coordinates = trainStation.getCoordinates();
            g.setColor(Color.BLUE);
            g.fillRect((int) coordinates.getX(), (int) coordinates.getY(), 3, 3);
        }
        for (RailroadLink railroadLink : railroadLinks){
            Coordinates source = railroadLink.getStation1().getCoordinates();
            Coordinates destination = railroadLink.getStation2().getCoordinates();
            g.setColor(Color.BLACK);
            float[] dash1 = { 4 };
            BasicStroke dashed =
                    new BasicStroke(1,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            1, dash1, 0);
            ((Graphics2D) g).setStroke(dashed);
            g.drawLine((int) source.getX(), (int) source.getY(), (int) destination.getX(), (int) destination.getY());
        }
    }

    private void leftMouseButton(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Coordinates coordinates = new Coordinates(x, y);
        TrainStation trainStation =
                new TrainStationFactory(locoBase).createTrainStation("station", coordinates);
    }

    private void rightMouseButton(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Coordinates coordinates = new Coordinates(x, y);
        List<TrainStation> closestTrainStations = locoBase.getTrainStations().stream().sorted(
                (ts1, ts2) -> {
            double distance1 = Coordinates.distance(ts1.getCoordinates(), coordinates);
            double distance2 = Coordinates.distance(ts2.getCoordinates(), coordinates);
            return Double.compare(distance1, distance2);
        }).toList();
        if (closestTrainStations.size() >= 2){
            TrainStation station1 = closestTrainStations.get(0);
            TrainStation station2 = closestTrainStations.get(1);
            new RailroadsFactory(locoBase).createRailroadLink(station1, station2);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 's'){
            resourceContainer.getTrainStationFactory().createRandomTrainStations(1);
        }
        else if (e.getKeyChar() == 'r'){
            resourceContainer.getRailroadsFactory().createOrderedConnectionsBetweenStations(3);
        }
        else if (e.getKeyChar() == 'l'){
            resourceContainer.getLoadFactory().createRandomLoads(10);
        }
        else if (e.getKeyChar() == 'c'){
            resourceContainer.getCarsFactory().createRandomCars(1);
        }
        else if (e.getKeyChar() == 't'){
            resourceContainer.getTrainSetFactory().createRandomTrainSets(1);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
