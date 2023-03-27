package pl.edu.pja.s28687;

import pl.edu.pja.s28687.Logistics.*;
import pl.edu.pja.s28687.Misc.TrainStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class Conductor extends Thread {
    private RouteFindingAlgos logisticSkill;
    private Locomotive locomotive;
    private List<RailroadLink> route;
    private final LocoBase locoBase;

    public Conductor(Locomotive locomotive, LocoBase locoBase){
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

    public void setRoute(List<RailroadLink> route){
        this.route = route;
    }

    public List<RailroadLink> findRoute() throws InterruptedException {
        if (this.route != null && locomotive.getLastTrainStation() != locomotive.getDestStation()){
            locomotive.setRoad(this.route);
            return this.route;
        }
        TrainStation sourceStation = locomotive.getSourceStation();
        TrainStation destStation = locomotive.getDestStation();
        Optional<List<RailroadLink>> route = switch (logisticSkill) {
            case BAD -> BadRouteFinder.findRoute(sourceStation, destStation);
            case WORST -> TheWorstFinder.findRoute(sourceStation, destStation, locoBase);
            case NORMAL -> RouteFinder.findRoute(sourceStation, destStation);
            default -> NaiveRouteFinder.findRoute(sourceStation, destStation, locoBase);
        };
        while (route.isEmpty()) {
            locomotive.setStatus(TrainStatus.UNABLE_TO_FIND_ROUTE);
            String s = "Train " + this.locomotive.getLocName() +
                    " Can't start the journey because railroad connection between" +
                    sourceStation.getName() + " and " +
                    destStation.getName() + " does not exist" +
                    " I will wait..";
            System.out.println(s);
            Thread.sleep(5000);
            route = NaiveRouteFinder.findRoute(sourceStation, locomotive.getDestStation(), locoBase);
        }
        return route.get();
    }

    public void setLogisticSkill(RouteFindingAlgos skillValue){
        logisticSkill = skillValue;
    }

    public void directSegment(RailroadLink segment) throws InterruptedException {
        TrainStation destination =
                locomotive.getLastTrainStation() == segment.getStation2()?
                        segment.getStation1() : segment.getStation2(); // change this crap later on
        BigDecimal segmentDistance = segment.getDistance();
        segment.enterRailway();
        locomotive.setCurrentSegment(segment);
        locomotive.setCurrentSegmentDestination(destination);
        announceDeparture(locomotive.getLastTrainStation(), destination);
        locomotive.setCurrentSegmentDistance(segmentDistance);
        MachinistJob machinistJob = new MachinistJob(locomotive, segmentDistance);
        machinistJob.start();
        machinistJob.join();
        segment.leaveRailway();
        locomotive.setLastTrainStation(destination);
        announceArrival(destination);
    }

    public void directRoute(List<RailroadLink> route){
        locomotive.setRoad(route);
        locomotive.setCurrentTripDistanceCovered(BigDecimal.valueOf(0));
        for (RailroadLink tdc : route){
            try {
                directSegment(tdc);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(20);
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
        locomotive.setSourceStation(locomotive.getDestStation());
        locomotive.setDestStation(temp);
    }

    public void announceDeparture(TrainStation source, TrainStation destination){
//        System.out.println(locomotive.getLocName() + " leaves  " + source + " , next station " + destination);
    }

    public void announceArrival(TrainStation destination){
//        System.out.println(locomotive.getLocName() + " arrives at " + destination);
    }

    public void switchDestination(){
        int i = new Random().nextInt(locoBase.getTrainStations().size() - 1);
        TrainStation newDestination = locoBase.getTrainStations().stream().toList().get(i);
        locomotive.setDestStation(newDestination);

    }
}
