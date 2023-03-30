package pl.edu.pja.s28687.logistics;


import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.*;

public class oldBase {
    private static int locIdCount = 1;
    private static int carIdCount = 1;
    private static final List<Locomotive> locomotiveList = new ArrayList<>();
    private static final List<RailroadCar> railroadCarsList = new ArrayList<>();
    private static final Set<TrainStation> trainStationList = new HashSet<>();
    private static final Set<RailroadLink> railroadConnectionList = new HashSet<>();
    private static final  List<LoadableRailroadCar<?>> loadCarriers = new ArrayList<>();
    private static final List<Load<? extends IDeliverable>> loadList = new ArrayList<>();



    public static int assignLocId(Locomotive locomotive) {
        registerLoc(locomotive);
        return locIdCount++;
    }


    public static int assignCarId() {
        return carIdCount++;
    }

    public static List<Locomotive> getLocomotiveList() {
        return locomotiveList;
    }

    public static void registerCar(RailroadCar railroadCar){
        railroadCarsList.add(railroadCar);
        if (railroadCar instanceof LoadableRailroadCar<?>) {
            loadCarriers.add( (LoadableRailroadCar<?>) railroadCar);
            System.out.println("Load carrie registered !");
        }
        System.out.println("LocBase : Railroad Car has been registered. Total number of cars registered : " + railroadCarsList.size());

    }

    public static void unRegisterCar(RailroadCar railroadCar){
        railroadCarsList.remove(railroadCar);
    }

    public static List<RailroadCar> getRailroadCarsList() {
        return railroadCarsList;
    }

    public static void registerLoc(Locomotive locomotive){
        locomotiveList.add(locomotive);
        System.out.println("LocBase : Locomotive has been registered. Total number of locomotives registered : " + locomotiveList.size());

    }

    public static void unregisterLoc(Locomotive locomotive){
        locomotiveList.remove(locomotive);
    }
    public static Optional<Locomotive> findLoc(int locId){
        return locomotiveList.stream().filter(loc -> loc.getId() == locId).findFirst();
    }

    public static Optional<LoadableRailroadCar<?>> findCar(int carID){
        return loadCarriers.
                stream().
                filter(car -> car .getId() == carID).
                findFirst();
    }


    public static void addTrainStation(TrainStation trainStation){
        trainStationList.add(trainStation);
        System.out.println("LocBase : Train Station has been registered. Total number of train stations registered : " + trainStationList.size());

    }

    public static Set<TrainStation> getTrainStationList(){
        return trainStationList;
    }
    public static Set<RailroadLink> getRailroadConnectionList(){
        return railroadConnectionList;
    }
    public static void registerRailroadConnection(RailroadLink connection){
        railroadConnectionList.add(connection);
        System.out.println(
                "LocBase : Railroad connection : \n" + connection.getStation1() + "-" + connection.getStation2()+
                        "\nhas been registered" + "\nlength : " + connection.getDistance() + " km");
        System.out.println("Total number of connections registered : " + railroadConnectionList.size());
    }

    public static Optional<TrainStation> findTrainStation(String name){
        return trainStationList.stream().filter(n -> n.getName().equals(name)).findFirst();
    }

    public static void registerLoad(Load<? extends IDeliverable> load){
        loadList.add(load);
        load.setId(loadList.size());
        System.out.println("LocBase : Load has been registered. Total number of loads registered : " + loadList.size());
    }

    public static Load<? extends IDeliverable> getLoad(int id){
        return loadList.stream().filter(load -> load.getId() == id).findFirst().orElse(null);
    }

    public static List<LoadableRailroadCar<? extends IDeliverable>> getLoadCarriers(){
        return loadCarriers;
    }

    public static List<Load<? extends IDeliverable>> getLoadList() {
        return loadList;
    }

    public static List<LoadableRailroadCar<?>> findSuitableCars(Load<?> load){
        return loadCarriers.
                stream().
                filter(car -> !car.isAttached()).
                filter(car ->
                        Collections.disjoint(load.flags(),
                                car.allowedLoadFlags())).
                toList();
    }



    public static List<Load<? extends IDeliverable>>findSuitableLoads(LoadableRailroadCar<? extends IDeliverable> car) {

        return loadList.
                    stream().
                    filter(load -> !load.isLoaded()).
                    filter(load ->
                            Collections.disjoint(load.flags(),
                                    car.allowedLoadFlags())).
                    filter(load -> car.validateLoad(((Load<IDeliverable>) load)).isEmpty()).
                    toList();
        }


    public static List<RailroadCar> findSuitableCars(Locomotive loc){

//        if (loc.getMaxCars() <= loc.carsOccupied()) return new ArrayList<>();
//
//        BigDecimal freightAvailable = loc.getMaxFreight().subtract(loc.getCurrentFreight());
//
//        List<RailroadCar> suitableCars = railroadCarsList.
//                stream().
//                filter(car -> !car.isAttached()).
//                filter(car -> car.getCurrentWeight().
//                        compareTo(freightAvailable) <= 0).toList();
//
//        if (loc.poweredCarsOccupied() >= loc.getMaxPoweredCars()) {
//            suitableCars = suitableCars.
//                    stream().
//                    filter(car -> {
//                if (car instanceof IPowered) return false;
//                else return true;}).
//                    toList();
//        }
        return railroadCarsList.stream()
                .filter(car -> ! car.isAttached())
                .filter(loc::validateCar)
                .toList();

    }

    public static BigDecimal calcDistance(TrainStation station1, TrainStation station2){
        Coordinates sourceCoords = station1.getCoordinates();
        Coordinates destCoords = station2.getCoordinates();
        double xDist = Math.abs(sourceCoords.getX() - destCoords.getX());
        double yDist = Math.abs(sourceCoords.getY() - destCoords.getY());
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
        return BigDecimal.valueOf(dist);
    }


}

