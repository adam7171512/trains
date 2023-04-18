package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.TrainStation;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DijkstraRouteFinder implements IRouteFinder{

    private final LocoBase locobase;

    public DijkstraRouteFinder(LocoBase locobase) {
        this.locobase = locobase;
    }

    public Optional<List<RailroadLink>> findRoute(TrainStation source, TrainStation destination) {
        Comparator<RouteFinderRefactor.StationNode> comparator
                = RouteFinderRefactor.getStationNodeComparator(Heuristics.DISTANCE);
        return Optional.of(RouteFinderRefactor.findRoute(source, destination, comparator, locobase));
    }
}