package pl.edu.pja.s28687.Factories;

import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Logistics.RailroadLink;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class RailroadsFactory {
    public static Random random = new Random();
    public static void makeRandomRailroadsConnectionsOldAlgo(int connectionsPerStation, LocoBase locoBase){
        List<TrainStation> stations = locoBase.getTrainStations().stream().toList();
        for (int i = 0; i < stations.size(); i++) {
            int h = 0;
             while(stations.get(i).getTrainDirectConnectionList().size() < connectionsPerStation) {
                 int k = i;
                 int finalI = i;
                 List<TrainStation> sortedTs = locoBase
                         .getTrainStations()
                         .stream()
                         .filter(station -> ! station.equals(stations.get(finalI)))
                         .sorted(Comparator.comparingDouble
                                 ((TrainStation ts)
                                         ->
                                         locoBase.calcDistance
                                                 (ts, stations.get(k))
                                                 .doubleValue()))
                         .toList();
                RailroadLink tdc = new RailroadLink(stations.get(i), sortedTs.get(h++));
                 locoBase.registerRailroadConnection(tdc);}
        }
    }

    public static void makeRandomRailroadsConnections(int connectionsPerStation, LocoBase locoBase){
        List<TrainStation> sortedTs = locoBase
                .getTrainStations()
                .stream()
                .sorted(Comparator.naturalOrder()).toList();
        for (int i = 0; i < sortedTs.size() ; i++) {
            int h = 0;
            int finalI1 = i;
            int rangeBottom = max(i - 100, 0);
            int rangeTop = min(i + 100, sortedTs.size() - 1);
            List<TrainStation> potentialNeighbours = sortedTs.subList(rangeBottom, rangeTop).stream().filter(ts -> !ts.equals(sortedTs.get(finalI1))).toList();
            int finalI = i;
            List<TrainStation> sortedPotantialNeighbours = potentialNeighbours
                    .stream()
                    .sorted(Comparator.comparingDouble(
                            ((TrainStation ts)
                                    ->
                                    locoBase.calcDistance
                                                    (ts, sortedTs.get(finalI))
                                            .doubleValue())))
                    .toList();
            while(sortedTs.get(i).getTrainDirectConnectionList().size() < connectionsPerStation) {
                if (sortedPotantialNeighbours.size() < h) break;
                RailroadLink tdc = new RailroadLink(sortedTs.get(i), sortedPotantialNeighbours.get(h++));
                locoBase.registerRailroadConnection(tdc);

            }

        }
    }
    }


