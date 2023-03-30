package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.TrainStation;

import java.util.*;

public class BadRouteFinder implements IRouteFinder {

    private LocoBase locoBase;

    public BadRouteFinder(LocoBase locoBase){
        this.locoBase = locoBase;
    }


    public Optional<List<RailroadLink>> findRoute(TrainStation source, TrainStation destination){
        HashSet<TrainStation> trainStationHashSet = new HashSet<>();
        List<RailroadLink> tt = new ArrayList<>();
        return Optional.of(find2(source, destination, tt, trainStationHashSet));
    }

    private static List<RailroadLink> find2(TrainStation source, TrainStation destination, List<RailroadLink> list, Set<TrainStation> trainStationHashSet){

        trainStationHashSet.add(source);
        if (source == destination) {
            return list;
        }
        for (RailroadLink tL : source.getTrainDirectConnectionList()){
            TrainStation tS  = source.getConnectionDest(tL);
            if (!trainStationHashSet.contains(tS)){
                list.add(tL);
                List<RailroadLink> path = find2(tS, destination, list, trainStationHashSet);
                if (! path.isEmpty()) return path;
                else list.remove(tL);
            }
        }
        return new ArrayList<>();
    }

}


