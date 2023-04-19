package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.TrainStation;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ReverseDijkstraRouteFinder implements IRouteFinder{
    private final LocoBase locobase;

    public ReverseDijkstraRouteFinder(LocoBase locobase) {
        this.locobase = locobase;
    }

    public List<RouteSegment> findRoute(TrainStation source, TrainStation destination) {
        Comparator<RouteFinderRefactor.StationNode> comparator
                = RouteFinderRefactor.getStationNodeComparator(Heuristics.REVERSE_DISTANCE);
        return RouteFinderRefactor.findRoute(source, destination, comparator, locobase);
    }
}
