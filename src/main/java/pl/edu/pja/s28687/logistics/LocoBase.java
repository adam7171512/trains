package pl.edu.pja.s28687.logistics;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.gui.RailroadsView;
import pl.edu.pja.s28687.gui.TrainSetView;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainStation;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocoBase {

    private int trainSetCount = 0;
    private int locCount = 0;
    private  int carCount = 0;
    private int loadCount = 0;
    private final Map<Integer, Locomotive> locomotives;
    private final Map<Integer, IRailroadCar> railroadCars;
    private final Set<TrainStation> trainStations;
    private final Set<RailroadLink> railroadConnections;
    private final Map<Integer, TrainSet> trainSets;
    private final Map<Integer, ILoadCarrier<IDeliverable>> loadCarriers;
    private final  Map<Integer, IDeliverable> loads;
    private static final Logger logger = Logger.getLogger(LocoBase.class.getName());
    static {
        logger.setLevel(java.util.logging.Level.SEVERE);
    }
    private Optional<TrainSetView> trainSetView = Optional.empty();
    private Optional<RailroadsView> railroadsView = Optional.empty();


    public LocoBase() {
        locomotives = new HashMap<>();
        railroadCars = new HashMap<>();
        trainStations = new CopyOnWriteArraySet<>();
        railroadConnections = new CopyOnWriteArraySet<>();
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

    public Optional<IDeliverable> findLoad(int selection) {
        return Optional.ofNullable(loads.get(selection));
    }

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

    public void registerCar(IRailroadCar railroadCar){
        int id = railroadCar.getId();

        if(railroadCars.containsKey(id)){
            throw new IllegalArgumentException(
                    "LocBase : Railroad Car with id "
                            + id
                            + " already exists !");
        }
        railroadCars.put(id, railroadCar);
        if (railroadCar instanceof ILoadCarrier<? extends IDeliverable>) {
            loadCarriers.put(id, ((ILoadCarrier<IDeliverable>) railroadCar));
            logger.log(java.util.logging.Level.INFO, "Load carrier registered ! " + railroadCar.getClass().getName());}
        logger.log(java.util.logging.Level.INFO,
                "Railroad Car has been registered. Total number of cars registered : " + carCount);
    }

    public int getIdForCar() {
        return ++carCount;
    }



    public void unRegisterCar(int id){
        if (!railroadCars.containsKey(id)) {
            throw new IllegalArgumentException("LocBase : Railroad Car with id " + id + " does not exist !");
        }
        railroadCars.remove(id);
        loadCarriers.remove(id);
    }

    public  List<IRailroadCar> getRailroadCarsList() {
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
        logger.log(java.util.logging.Level.INFO,
                "Locomotive " + locomotive.getName() + " with ID: "
                        + locomotive.getId()
                        + " has been registered. Total number of locomotives registered : "
                        + locCount);
        trainSetView.ifPresent(locomotive::setView);
    }

    public void unregisterLoc(int id){
        if (!locomotives.containsKey(id)) {
            throw new IllegalArgumentException("LocBase : Locomotive with id " + id + " does not exist !");
        }
        locomotives.remove(id);
    }

    public  Optional<Locomotive> findLoc(int id){
        return Optional.ofNullable(locomotives.get(id));
    }

    public  Optional<IRailroadCar> findCar(int id){
        return Optional.ofNullable(railroadCars.get(id));
    }

    public  Optional<ILoadCarrier<IDeliverable>> findLoadCarrier(int id){
        return Optional.ofNullable(loadCarriers.get(id));
    }

    public void registerTrainStation(TrainStation trainStation){
        if (trainStations.contains(trainStation)) {
            System.err.println("Train station already exists !");
            return;
        }
        trainStations.add(trainStation);
        logger.log(java.util.logging.Level.INFO,
                "Train station registered ! Total number of train stations registered : "
                        + trainStations.size());
        railroadsView.ifPresent(v -> v.addTrainStation(trainStation));
    }

    public Set<TrainStation> getTrainStations(){
        return trainStations;
    }
    public Set<RailroadLink> getRailroadConnections(){
        return railroadConnections;
    }
    public boolean registerRailroadConnection(RailroadLink connection){
        if (railroadConnections.contains(connection)) {
            System.err.println("Railroad connection already exists !");
            return false;
        }
        railroadConnections.add(connection);
        logger.log(java.util.logging.Level.INFO,
                "Railroad connection registered ! Total number of connections registered : "
                        + railroadConnections.size());
        railroadsView.ifPresent(v -> v.addRailroadLink(connection));
        return true;
    }

    public Optional<TrainStation> findTrainStation(String name){
        return trainStations.stream().filter(n -> n.getName().equals(name)).findFirst();
    }

    public int getIdForLoad(){
        return ++loadCount;
    }

    public void registerLoad(IDeliverable load) {
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
        logger.log(java.util.logging.Level.INFO, s);
    }

    public IDeliverable  getLoad(int id){
        return  loads.get(id);
    }

    public  List<ILoadCarrier<IDeliverable>> getLoadCarriers(){
        return loadCarriers.values().stream().map(car -> (ILoadCarrier<IDeliverable>) car).toList();
//        return loadCarriers.values().stream().toList();
    }

    public List<? extends IDeliverable> getLoadList() {
        return loads.values().stream().toList();
    }

    public List<ILoadCarrier<IDeliverable>> findSuitableCars(IDeliverable load){
        List<ILoadCarrier<IDeliverable>> suitableCars = new ArrayList<>();
        for (ILoadCarrier<IDeliverable> car : loadCarriers.values()) {
            if (!car.isAttached() && car.validateLoad(load)) {
                suitableCars.add(car);
            }
        }
        return suitableCars;

    }

//        return loadCarriers
//                .values()
//                .stream()
//                .filter(car -> !car.isAttached())
//                .filter(car ->
//                        Collections.disjoint(load.flags(),
//                                car.allowedLoadFlags()))
//                .toList();





    public List<IDeliverable>findSuitableLoads(ILoadCarrier<? extends IDeliverable> car) {

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


    public Optional<RailroadLink> findLink(Set<TrainStation> stations){
        return railroadConnections
                .stream()
                .filter(connection -> connection.getStations().equals(stations))
                .findFirst();
    }


    public  List<IRailroadCar> findSuitableCars(Locomotive loc){
        return railroadCars
                .values()
                .stream()
                .filter(car -> ! car.isAttached())
                .filter(loc::validateCar)
                .toList();
    }

    //todo : move to another class
    public static BigDecimal calcDistance(TrainStation station1, TrainStation station2){
        Coordinates sourceCoords = station1.getCoordinates();
        Coordinates destCoords = station2.getCoordinates();
        double xDist = Math.abs(sourceCoords.getX() - destCoords.getX());
        double yDist = Math.abs(sourceCoords.getY() - destCoords.getY());
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
        return BigDecimal.valueOf(dist);
    }

    public void setRailroadsView(RailroadsView view){
        this.railroadsView = Optional.of(view);
    }

    public void setTrainSetView(TrainSetView view){
        this.trainSetView = Optional.of(view);
    }

    public static void setLogLevel(Level level){
        logger.setLevel(level);
    }
}

