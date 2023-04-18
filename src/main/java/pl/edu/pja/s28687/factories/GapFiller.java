package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.RailroadLink;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.logistics.RouteFindingAlgos;

import java.util.List;
import java.util.Optional;

public class GapFiller {

    public static void fill(LocoBase locoBase){

        List<TrainStation> stations = locoBase.getTrainStations().stream().toList();
        int i = 0;
        int j = stations.size() - 1;
        while ( i < j){
            TrainStation t = stations.get(i);
            Optional<List<RailroadLink>> link = RouteFinderFactory
                    .getRouteFinder(RouteFindingAlgos.A_STAR, locoBase).findRoute(t, stations.get(j));
            if (link.isEmpty()) {
                System.out.println("filling gaps");
                RailroadLink r = new RailroadLink(t, stations.get(j));
                locoBase.registerRailroadConnection(r);
                System.out.println(i);
                }
            i++;
            j--;
            }
        }

    }

