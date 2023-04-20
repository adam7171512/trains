package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RailroadLink;
import pl.edu.pja.s28687.logistics.TrainStation;
import pl.edu.pja.s28687.logistics.RouteFindingAlgos;
import pl.edu.pja.s28687.logistics.RouteSegment;

import java.util.List;

public class GapFiller {

    public static void fill(LocoBase locoBase){
        List<TrainStation> stations = locoBase.getTrainStations().stream().toList();
        int i = 0;
        int j = stations.size() - 1;
        while ( i < j){
            TrainStation t = stations.get(i);
            List<RouteSegment> link = RouteFinderFactory
                    .getRouteFinder(RouteFindingAlgos.A_STAR, locoBase).findRoute(t, stations.get(j));
            if (link.isEmpty()) {
                RailroadLink r = new RailroadsFactory(locoBase).createRailroadLink(stations.get(i), stations.get(j));
                }
            i++;
            j--;
            }
        }

    }

