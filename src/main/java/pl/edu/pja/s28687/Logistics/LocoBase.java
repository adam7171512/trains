package pl.edu.pja.s28687.Logistics;


import pl.edu.pja.s28687.Cars.*;
import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.*;

public class LocoBase {
    private int locCount = 0;
    private  int carCount = 0;
    private int loadCount = 0;
    private final Map<Integer, Locomotive> locomotives;
    private final Map<Integer, RailroadCar> railroadCars;
    private final Set<TrainStation> trainStations;
    private final Set<RailroadLink> railroadConnections;
    private final  Map<Integer, LoadableRailroadCar<? extends IDeliverable>> loadCarriers;
    private final Map<Integer, Load<? extends IDeliverable>> loads;

    public LocoBase(){
        locomotives = new HashMap<>();
        railroadCars = new HashMap<>();
        trainStations = new HashSet<>();
        railroadConnections = new HashSet<>();
        loadCarriers = new HashMap<>();
        loads = new HashMap<>();
    }

    private static class SingletonHelper{
        private static final LocoBase INSTANCE = new LocoBase();
    }

    public static LocoBase getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public int assignLocId(Locomotive locomotive) {
        return registerLoc(locomotive);
    }

    public List<Locomotive> getLocomotiveList() {
        return locomotives.values().stream().toList();
    }

    public int registerCar(RailroadCar railroadCar){
        railroadCars.put(++carCount, railroadCar);

        if (railroadCar instanceof LoadableRailroadCar<?>) {
            loadCarriers.put(carCount, (LoadableRailroadCar<?>) railroadCar);
            System.out.println("Load carrier registered !");
        }
        System.out.println("LocBase : Railroad Car has been registered. Total number of cars registered : " + carCount);
        return carCount;
    }

    public void unRegisterCar(int id){
        railroadCars.remove(id);
        loadCarriers.remove(id);
    }

    public  List<RailroadCar> getRailroadCarsList() {
        return railroadCars.values().stream().toList();
    }

    public  int registerLoc(Locomotive locomotive){
        locomotives.put(++locCount, locomotive);
        System.out.println("LocBase : Locomotive has been registered. Total number of locomotives registered : " + locomotives.size());
        return locCount;
    }

    public void unregisterLoc(int id){
        locomotives.remove(id);
    }
    public  Optional<Locomotive> findLoc(int id){
        return Optional.ofNullable(locomotives.get(id));
    }

    public  Optional<LoadableRailroadCar<?>> findCar(int id){
        return Optional.ofNullable(loadCarriers.get(id));
    }

    public void addTrainStation(TrainStation trainStation){
        trainStations.add(trainStation);
        System.out.println("LocBase : Train Station has been registered. Total number of train stations registered : " + trainStations.size());

    }

    public Set<TrainStation> getTrainStations(){
        return trainStations;
    }
    public Set<RailroadLink> getRailroadConnections(){
        return railroadConnections;
    }
    public void registerRailroadConnection(RailroadLink connection){
        railroadConnections.add(connection);
        System.out.println(
                "LocBase : Railroad connection : \n" + connection.getStation1() + "-" + connection.getStation2()+
                        "\nhas been registered" + "\nlength : " + connection.getDistance() + " km");
        System.out.println("Total number of connections registered : " + railroadConnections.size());
    }

    public Optional<TrainStation> findTrainStation(String name){
        return trainStations.stream().filter(n -> n.getName().equals(name)).findFirst();
    }

    public void registerLoad(Load<? extends IDeliverable> load){
        loads.put(++loadCount, load);
        load.setId(loadCount);
        System.out.println("LocBase : Load has been registered. Total number of loads registered : " + loads.size());
    }

    public Load<? extends IDeliverable> getLoad(int id){
        return loads.get(id);
    }

    public List<LoadableRailroadCar<? extends IDeliverable>> getLoadCarriers(){
        return loadCarriers.values().stream().toList();
    }

    public List<Load<? extends IDeliverable>> getLoadList() {
        return loads.values().stream().toList();
    }

    public List<LoadableRailroadCar<?>> findSuitableCars(Load<?> load){
        return loadCarriers
                .values()
                .stream()
                .filter(car -> !car.isAttached())
                .filter(car ->
                        Collections.disjoint(load.flags(),
                                car.forbiddenLoadFlags()))
                .toList();
    }



    public List<Load<? extends IDeliverable>>findSuitableLoads(LoadableRailroadCar<?> car) {

        return loads
                .values()
                .stream()
                .filter(load -> !load.isLoaded())
                .filter(load ->
                        Collections.disjoint(load.flags(),
                                car.forbiddenLoadFlags()))
                .filter(load -> car.validateLoad(load).isEmpty())
                .toList();
    }


    public  List<RailroadCar> findSuitableCars(Locomotive loc){
        return railroadCars
                .values()
                .stream()
                .filter(car -> ! car.isAttached())
                .filter(loc::validateCar)
                .toList();
    }

    // move to another class
    public BigDecimal calcDistance(TrainStation station1, TrainStation station2){
        Coordinates sourceCoords = station1.getCoordinates();
        Coordinates destCoords = station2.getCoordinates();
        double xDist = Math.abs(sourceCoords.getX() - destCoords.getX());
        double yDist = Math.abs(sourceCoords.getY() - destCoords.getY());
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
        return BigDecimal.valueOf(dist);
    }


}

