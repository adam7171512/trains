package pl.edu.pja.s28687.logistics;


import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.*;

public class LocoBase {
    private int trainSetCount = 0;
    private int locCount = 0;
    private  int carCount = 0;
    private int loadCount = 0;
    private final Map<Integer, Locomotive> locomotives;
    private final Map<Integer, RailroadCar> railroadCars;
    private final Set<TrainStation> trainStations;
    private final Set<RailroadLink> railroadConnections;
    private final Map<Integer, TrainSet> trainSets;
    private final  Map<Integer, LoadableRailroadCar<? extends IDeliverable>> loadCarriers;
    private final Map<Integer, Load<? extends IDeliverable>> loads;

    public LocoBase(){
        locomotives = new HashMap<>();
        railroadCars = new HashMap<>();
        trainStations = new HashSet<>();
        railroadConnections = new HashSet<>();
        loadCarriers = new HashMap<>();
        trainSets = new HashMap<>();
        loads = new HashMap<>();
    }

    public List<TrainSet> getTrainSets() {
        return trainSets.values().stream().toList();
    }

    public Optional<TrainSet> findTrainSet(int selection) {
        return Optional.ofNullable(trainSets.get(selection));
    }


    private static class SingletonHelper{
        private static final LocoBase INSTANCE = new LocoBase();
    }

    public static LocoBase getInstance(){
        return SingletonHelper.INSTANCE;
    }

//    public int assignLocId(Locomotive locomotive) {
//        return registerLoc(locomotive);
//    }

    public List<Locomotive> getLocomotiveList() {
        return locomotives.values().stream().toList();
    }

    public int getIdForTrainSet(){
        return ++trainSetCount;
    }

    public void registerTrainSet(TrainSet trainSet){
        int id = trainSet.getId();
        if (trainSets.containsKey(id)) {
            throw new IllegalArgumentException("Train set with id " + id + " already exists");
        }
        trainSets.put(id, trainSet);
    }

    public void registerCar(RailroadCar railroadCar){
        int id = railroadCar.getId();

        if(railroadCars.containsKey(id)){
            throw new IllegalArgumentException(
                    "LocBase : Railroad Car with id "
                            + id
                            + " already exists !");
        }
        railroadCars.put(id, railroadCar);
        if (railroadCar instanceof LoadableRailroadCar<?>) {
            loadCarriers.put(id, (LoadableRailroadCar<?>) railroadCar);
            System.out.println("Load carrier registered !");
        }
        System.out.println("LocBase :" +
                " Railroad Car has been registered." +
                " Total number of cars registered : " + carCount);
    }

    public int getIdForCar() {
        return ++carCount;
    }



    public void unRegisterCar(int id){
        railroadCars.remove(id);
        loadCarriers.remove(id);
    }

    public  List<RailroadCar> getRailroadCarsList() {
        return railroadCars.values().stream().toList();
    }

//    public  int registerLoc(Locomotive locomotive){
//        locomotives.put(++locCount, locomotive);
//        System.out.println("LocBase : Locomotive has been registered. Total number of locomotives registered : " + locomotives.size());
//        return locCount;
//    }

    public int getIdForLocomotive(){
        return ++locCount;
    }

    public void registerLocomotive(Locomotive locomotive){
        int id = locomotive.getId();
        if (locomotives.containsKey(id)) {
            throw new IllegalArgumentException
                    ("Locomotive with id " + id + " already exists");
        }
        locomotives.put(locomotive.getId(), locomotive);
    }

    public void unregisterLoc(int id){
        locomotives.remove(id);
    }

    public void unregisterLoc(Locomotive locomotive){
        locomotives.remove(locomotive.getId());
    }

    public  Optional<Locomotive> findLoc(int id){
        return Optional.ofNullable(locomotives.get(id));
    }

    public  Optional<RailroadCar> findCar(int id){
        return Optional.ofNullable(railroadCars.get(id));
    }

    public  Optional<LoadableRailroadCar<? extends IDeliverable>> findLoadCarrier(int id){
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

    public int getIdForLoad(){
        return ++loadCount;
    }

    public void registerLoad(Load<? extends IDeliverable> load) {
        int id = load.getId();
        if (loads.containsKey(id)) {
            throw new IllegalArgumentException
                    ("Load with id " + id + " already exists");
        }
        loads.put(load.getId(), load);
        String s = new StringBuilder()
                .append("LocBase : Load has been registered.")
                .append("Flags : ").append(load.flags())
                .append(" Total number of loads registered : ")
                .append(loads.size()).toString();
        System.out.println(s);
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

    public List<LoadableRailroadCar<? extends IDeliverable>> findSuitableCars(Load<? extends IDeliverable> load){
        return loadCarriers
                .values()
                .stream()
                .filter(car -> !car.isAttached())
                .filter(car ->
                        Collections.disjoint(load.flags(),
                                car.allowedLoadFlags()))
                .toList();
    }



    public List<Load<? extends IDeliverable>>findSuitableLoads(ILoadCarrier<? extends IDeliverable> car) {

        return loads
                .values()
                .stream()
                .filter(load -> !load.isLoaded())
                .filter(car::validateLoad)
                .toList();
    }

    public RailroadLink getLink(TrainStation station1, TrainStation station2){
        return railroadConnections
                .stream()
                .filter(connection -> connection.getStation1().equals(station1) || connection.getStation2().equals(station1))
                .filter(connection -> connection.getStation2().equals(station2) || connection.getStation1().equals(station2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No connection between " + station1 + " and " + station2));
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

