package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RailroadLink;
import pl.edu.pja.s28687.TrainStation;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class RailroadsFactory {
    public static Random random = new Random();
    private LocoBase locoBase;

    public RailroadsFactory(LocoBase locoBase) {
        this.locoBase = locoBase;
    }

    public RailroadLink createRailroadLink(TrainStation station1, TrainStation station2) {
        if (station1.getNeighbors().contains(station2))
            throw new IllegalArgumentException("Railroad link already exists between these stations");
        RailroadLink railroadLink = new RailroadLink(station1, station2);
        locoBase.registerRailroadConnection(railroadLink);
        station1.addNeighbor(station2);
        station2.addNeighbor(station1);
        return railroadLink;
    }

    public RailroadLink createRailroadLink(String station1Name, String station2Name) throws IllegalArgumentException {
        Optional<TrainStation> station1 = locoBase.findTrainStation(station1Name);
        Optional<TrainStation> station2 = locoBase.findTrainStation(station2Name);
        if (station1.isEmpty() || station2.isEmpty() || station1.get().equals(station2.get())) {
            throw new IllegalArgumentException("One of the stations does not exist");
        }
        return createRailroadLink(station1.get(), station2.get());
    }

    public RailroadLink createRandomRailroadLink() {
        Random random = new Random();
        List<TrainStation> stations = locoBase.getTrainStations().stream().toList();
        TrainStation station1 = stations.get(random.nextInt(stations.size()));
        TrainStation station2 = stations.get(random.nextInt(stations.size()));
        return createRailroadLink(station1, station2);
    }

    public List<RailroadLink> createRandomRailroadLinks(int amount) {
        List<RailroadLink> railroadLinks = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            railroadLinks.add(createRandomRailroadLink());
        }
        return railroadLinks;
    }

    public void createOrderedConnectionsBetweenStations(int amountPerStation, boolean slowAlgorithm) {
        List<RailroadLink> railroadLinks = new ArrayList<>();
        List<TrainStation> stations = locoBase
                .getTrainStations()
                .stream()
                .sorted(Comparator.comparingDouble(ts ->
                                Math.sqrt(
                                        Math.pow(ts.getCoordinates().getX(), 2)
                                                + Math.pow(ts.getCoordinates().getY(), 2)
                                )
                        )
                )
                .toList();
        if (slowAlgorithm)
            createOrderedConnectionsSlowAlgo(amountPerStation, stations);
        else
            createOrderedConnections(amountPerStation, stations);
    }

    public void createOrderedConnectionsBetweenStations(int amountPerStation) {
        createOrderedConnectionsBetweenStations(amountPerStation, false);
    }


    private void createOrderedConnections(int numberPerStation, List<TrainStation> stations) {
        for (int i = 0; i < stations.size(); i++) {

            int finalI1 = i;
            int rangeBottom = max(i - 100, 0);
            int rangeTop = min(i + 100, stations.size() - 1);
            List<TrainStation> potentialNeighbours = stations
                    .subList(rangeBottom, rangeTop)
                    .stream()
                    .filter(ts -> !ts.equals(stations.get(finalI1)))
                    .filter(ts -> !ts.getNeighbors().contains(stations.get(finalI1)))
                    .toList();
            List<TrainStation> sortedPotantialNeighbours = potentialNeighbours
                    .stream()
                    .sorted(Comparator.comparingDouble(
                            ((TrainStation ts)
                                    ->
                                    locoBase.calcDistance
                                                    (ts, stations.get(finalI1))
                                            .doubleValue())))
                    .toList();

            int h = 0;
            while (stations.get(finalI1).getNeighbors().size() < numberPerStation) {
//                if (sortedPotantialNeighbours.size() < h){
//                    break;
//                }
                if (stations.get(i).getNeighbors().contains(sortedPotantialNeighbours.get(h)))
                    h++;
                else
                    createRailroadLink(stations.get(i), sortedPotantialNeighbours.get(h++));
            }
        }
    }

    private void createOrderedConnectionsSlowAlgo(int numberPerStation, List<TrainStation> stations) {
        for (int i = 0; i < stations.size(); i++) {
            int h = 0;
            while (stations.get(i).getTrainDirectConnectionList().size() < numberPerStation) {
                int k = i;
                int finalI = i;
                List<TrainStation> sortedTs = locoBase
                        .getTrainStations()
                        .stream()
                        .filter(station -> !station.equals(stations.get(finalI)))
                        .filter(station -> !station.getNeighbors().contains(stations.get(finalI)))
                        .sorted(Comparator.comparingDouble
                                ((TrainStation ts)
                                        ->
                                        locoBase.calcDistance
                                                        (ts, stations.get(k))
                                                .doubleValue()))
                        .toList();
                createRailroadLink(stations.get(i), sortedTs.get(h++));
            }

        }
    }
}


