package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.TrainStation;

import java.util.*;

public class DepthFirstSearchRouteFinder implements IRouteFinder{
    private final LocoBase locobase;
    public DepthFirstSearchRouteFinder(LocoBase locobase) {
        this.locobase = locobase;
    }
    @Override
    public Optional<List<RailroadLink>> findRoute(TrainStation source, TrainStation destination) {
            return Optional.of(RouteFinderRefactor.findRouteDFS(source, destination, locobase));
    }
}
