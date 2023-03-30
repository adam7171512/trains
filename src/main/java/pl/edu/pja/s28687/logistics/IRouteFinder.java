package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.TrainStation;

import java.util.List;
import java.util.Optional;

public interface IRouteFinder {
    Optional<List<RailroadLink>> findRoute(TrainStation source, TrainStation destination);
}
