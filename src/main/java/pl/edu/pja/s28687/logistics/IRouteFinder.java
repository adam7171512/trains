package pl.edu.pja.s28687.logistics;

import java.util.List;

public interface IRouteFinder {
    List<RouteSegment> findRoute(TrainStation source, TrainStation destination);
}
