package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.gui.RailroadsView;
import pl.edu.pja.s28687.gui.TrainSetView;
import pl.edu.pja.s28687.load.IDeliverable;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class LocoBase {

    private static final Logger logger = Logger.getLogger(LocoBase.class.getName());

    static {
        logger.setLevel(SEVERE);
    }

    private final Map<Integer, Locomotive> locomotives;
    private final Map<Integer, IRailroadCar> railroadCars;
    private final Set<TrainStation> trainStations;
    private final Set<RailroadLink> railroadConnections;
    private final Map<Integer, TrainSet> trainSets;
    private final Map<Integer, ILoadCarrier<IDeliverable>> loadCarriers;
    private final Map<Integer, IDeliverable> loads;
    private int trainSetCount = 0;
    private int locCount = 0;
    private int carCount = 0;
    private int loadCount = 0;
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

    public static void setLogLevel(Level level) {
        logger.setLevel(level);
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

    public int getIdForTrainSet() {
        return ++trainSetCount;
    }

    public boolean registerTrainSet(TrainSet trainSet) {
        int id = trainSet.getId();
        if (trainSets.containsKey(id)) {
            logger.log(SEVERE, "Train set with id " + id + " already exists"
                    + " Train set not registered");
            return false;
        }
        trainSets.put(id, trainSet);
        logger.log(java.util.logging.Level.INFO,
                "Train set has been registered. Total number of train sets registered : " + trainSetCount);
        return true;
    }

    public boolean registerCar(IRailroadCar railroadCar) {
        int id = railroadCar.getId();

        if (railroadCars.containsKey(id)) {
            logger.log(SEVERE, "Railroad car with id " + id + " already exists"
                    + " Car not registered");
            return false;
        }
        railroadCars.put(id, railroadCar);
        if (railroadCar instanceof ILoadCarrier<? extends IDeliverable>) {
            loadCarriers.put(id, ((ILoadCarrier<IDeliverable>) railroadCar));
            logger.log(java.util.logging.Level.INFO, "Load carrier registered ! " + railroadCar.getClass().getName());
        }
        logger.log(java.util.logging.Level.INFO,
                "Railroad Car has been registered. Total number of cars registered : " + carCount);
        return true;
    }

    public int getIdForCar() {
        return ++carCount;
    }

    public boolean unRegisterCar(int id) {
        if (!railroadCars.containsKey(id)) {
            logger.log(SEVERE, "LocBase : Railroad Car with id " + id + " does not exist !"
                    + " Car not unregistered");
            return false;
        }
        railroadCars.remove(id);
        loadCarriers.remove(id);
        logger.log(java.util.logging.Level.INFO,
                "Railroad Car with ID : " + id + " has been unregistered. ");
        return true;
    }

    public List<IRailroadCar> getRailroadCarsList() {
        return railroadCars.values().stream().toList();
    }

    public int getIdForLocomotive() {
        return ++locCount;
    }

    public boolean registerLocomotive(Locomotive locomotive) {
        int id = locomotive.getId();
        if (locomotives.containsKey(id)) {
            logger.log
                    (SEVERE, "Locomotive with id " + id + " already exists" +
                            " Locomotive not registered");
            return false;
        }
        locomotives.put(locomotive.getId(), locomotive);
        logger.log(java.util.logging.Level.INFO,
                "Locomotive " + locomotive.getName() + " with ID: "
                        + locomotive.getId()
                        + " has been registered. Total number of locomotives registered : "
                        + locCount);
        trainSetView.ifPresent(locomotive::setView);
        return true;
    }

    public boolean unregisterLoc(int id) {
        if (!locomotives.containsKey(id)) {
            logger.log(SEVERE, "LocBase : Locomotive with id " + id + " does not exist !" +
                    " Locomotive not unregistered");
            return false;
        }
        locomotives.remove(id);
        logger.log(java.util.logging.Level.INFO,
                "Locomotive with ID : " + id + " has been unregistered. ");
        return true;
    }

    public Optional<Locomotive> findLoc(int id) {
        return Optional.ofNullable(locomotives.get(id));
    }

    public Optional<IRailroadCar> findCar(int id) {
        return Optional.ofNullable(railroadCars.get(id));
    }

    public Optional<ILoadCarrier<IDeliverable>> findLoadCarrier(int id) {
        return Optional.ofNullable(loadCarriers.get(id));
    }

    public boolean registerTrainStation(TrainStation trainStation) {
        if (trainStations.contains(trainStation)) {
            logger.log(SEVERE, "Train station already exists !" +
                    " Train station not registered");
            return false;
        }
        trainStations.add(trainStation);
        logger.log(java.util.logging.Level.INFO,
                "Train station registered ! Total number of train stations registered : "
                        + trainStations.size());
        railroadsView.ifPresent(v -> v.addTrainStation(trainStation));
        return true;
    }

    public Set<TrainStation> getTrainStations() {
        return trainStations;
    }

    public Set<RailroadLink> getRailroadConnections() {
        return railroadConnections;
    }

    public boolean registerRailroadConnection(RailroadLink connection) {
        if (railroadConnections.contains(connection)) {
            logger.log(SEVERE, "Railroad connection already exists !");
            return false;
        }
        railroadConnections.add(connection);
        logger.log(java.util.logging.Level.INFO,
                "Railroad connection registered ! Total number of connections registered : "
                        + railroadConnections.size());
        railroadsView.ifPresent(v -> v.addRailroadLink(connection));
        return true;
    }

    public Optional<TrainStation> findTrainStation(String name) {
        return trainStations.stream().filter(n -> n.getName().equals(name)).findFirst();
    }

    public int getIdForLoad() {
        return ++loadCount;
    }

    public boolean registerLoad(IDeliverable load) {
        int id = load.getId();
        if (loads.containsKey(id)) {
            logger.log
                    (SEVERE, "Load with id " + id + " already exists" +
                            " Load not registered");
            return false;
        }
        loads.put(load.getId(), load);
        String s = new StringBuilder()
                .append("LocBase : Load has been registered.")
                .append("Flags : ").append(load.flags())
                .append(" Total number of loads registered : ")
                .append(loads.size()).toString();
        logger.log(java.util.logging.Level.INFO, s);
        return true;
    }

    public List<ILoadCarrier<IDeliverable>> getLoadCarriers() {
        return loadCarriers.values().stream().toList();
    }

    public List<? extends IDeliverable> getLoadList() {
        return loads.values().stream().toList();
    }

    public List<ILoadCarrier<IDeliverable>> findSuitableCars(IDeliverable load) {
        List<ILoadCarrier<IDeliverable>> suitableCars = new ArrayList<>();
        for (ILoadCarrier<IDeliverable> car : loadCarriers.values()) {
            if (!car.isAttached() && car.validateLoad(load)) {
                suitableCars.add(car);
            }
        }
        return suitableCars;
    }

    public List<IDeliverable> findSuitableLoads(ILoadCarrier<? extends IDeliverable> car) {
        return loads
                .values()
                .stream()
                .filter(load -> !load.isLoaded())
                .filter(car::validateLoad)
                .toList();
    }

    public RailroadLink findLink(TrainStation station1, TrainStation station2) {
        return railroadConnections
                .stream()
                .filter(connection -> connection.getStations().equals(Set.of(station1, station2)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No connection between " + station1 + " and " + station2));
    }

    public Optional<RailroadLink> findLink(Set<TrainStation> stations) {
        return railroadConnections
                .stream()
                .filter(connection -> connection.getStations().equals(stations))
                .findFirst();
    }

    public List<IRailroadCar> findSuitableCars(Locomotive loc) {
        return railroadCars
                .values()
                .stream()
                .filter(car -> !car.isAttached())
                .filter(loc::validateCar)
                .toList();
    }

    public void setRailroadsView(RailroadsView view) {
        this.railroadsView = Optional.of(view);
    }

    public void setTrainSetView(TrainSetView view) {
        this.trainSetView = Optional.of(view);
    }
}

