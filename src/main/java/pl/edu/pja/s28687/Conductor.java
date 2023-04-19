package pl.edu.pja.s28687;

import pl.edu.pja.s28687.logistics.*;
import pl.edu.pja.s28687.misc.TrainStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conductor extends Thread {
    private static final Logger LOGGER = Logger.getLogger(Conductor.class.getName());
    static {
        LOGGER.setLevel(Level.SEVERE);
    }
    private IRouteFinder routeFinder;
    private RouteFindingAlgos logisticSkill = RouteFindingAlgos.A_STAR;
    private Locomotive locomotive;
    private List<RouteSegment> route;
    private final LocoBase locoBase;
    //temporary for testing

    public Conductor(Locomotive locomotive, LocoBase locoBase){
        this.routeFinder = new AStarRouteFinder(locoBase);
        this.locoBase = locoBase;
        this.locomotive = locomotive;
    }

    @Override
    public void run(){
        while (true){
            try {
                directRoute(findRoute());
            } catch (InterruptedException e) {

            throw new RuntimeException(e);
          }
            reverseTrip();
        }
    }

    public List<RouteSegment> findRoute() throws InterruptedException {
        if (this.route != null && locomotive.getLastTrainStation() != locomotive.getDestinationStation()){
            locomotive.setRoute(this.route);
            return this.route;
        }
        TrainStation sourceStation = locomotive.getSourceStation();
        TrainStation destStation = locomotive.getDestinationStation();
        List<RouteSegment> route = routeFinder.findRoute(sourceStation, destStation);
        while (route.isEmpty()) {
            locomotive.setStatus(TrainStatus.UNABLE_TO_FIND_ROUTE);
            String s = "Train " + this.locomotive.getName() +
                    " Can't start the journey because railroad connection between" +
                    sourceStation.getName() + " and " +
                    destStation.getName() + " does not exist" +
                    " I will wait..";
            LOGGER.log(Level.SEVERE, s);
            Thread.sleep(5000);
            route = routeFinder.findRoute(sourceStation, destStation);
        }
        return route;
    }

    public void setRouteFindingAlgorithm(IRouteFinder routeFinder){
        this.routeFinder = routeFinder;
    }

    public void directSegment(RouteSegment segment) throws InterruptedException {
        TrainStation destination = segment.getDestination();
        BigDecimal segmentDistance = segment.getDistance();
        segment.enterRailway(locomotive);
        locomotive.setCurrentSegment(segment);
        announceDeparture(locomotive.getLastTrainStation(), destination);
        MachinistJob machinistJob = new MachinistJob(locomotive, segmentDistance);
        machinistJob.start();
        machinistJob.join();
        segment.leaveRailway(locomotive);
        announceArrival(destination);
    }

    public void directRoute(List<RouteSegment> route){
        locomotive.setRoute(route);
        locomotive.setCurrentTripDistanceCovered(BigDecimal.valueOf(0));
        for (RouteSegment tdc : route){
            try {
                directSegment(tdc);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Thread.sleep(3010);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void reverseTrip(){
        TrainStation temp = locomotive.getSourceStation();
        locomotive.setSourceStation(locomotive.getDestinationStation());
        locomotive.setDestinationStation(temp);
    }

    public void announceDeparture(TrainStation source, TrainStation destination){
        LOGGER.log(Level.INFO, "Train " + locomotive.getName()
                        + " travelling to " + locomotive.getDestinationStation()
                        + " leaves  " + source + " , next station " + destination);
    }

    public void announceArrival(TrainStation destination){
        String message;
        if (locomotive.getDestinationStation().equals(destination)) {
            message = "Train " + locomotive.getName()
                    + " has arrived at its final destination  " + destination;
        }
        else {
            message = "Train " + locomotive.getName()
                    + " travelling to " + locomotive.getDestinationStation()
                    + " arrives at " + destination;
        }
        LOGGER.log(Level.INFO, message);
    }

    public void switchDestination(){
        int i = new Random().nextInt(locoBase.getTrainStations().size() - 1);
        TrainStation newDestination = locoBase.getTrainStations().stream().toList().get(i);
        locomotive.setDestinationStation(newDestination);

    }

    @Override
    public String toString() {
        return "Conductor " +
                "routeFinder=" + routeFinder;
    }

    public static void setLogLevel(Level level){
        LOGGER.setLevel(level);
    }
}
