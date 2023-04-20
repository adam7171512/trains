package pl.edu.pja.s28687.logistics;

import java.util.*;

public class DepthFirstSearchRouteFinder implements IRouteFinder{
    private final LocoBase locobase;
    public DepthFirstSearchRouteFinder(LocoBase locobase) {
        this.locobase = locobase;
    }
    @Override
    public List<RouteSegment> findRoute(TrainStation source, TrainStation destination) {
            return RouteFinder.findRouteDFS(source, destination, locobase);
    }
}
