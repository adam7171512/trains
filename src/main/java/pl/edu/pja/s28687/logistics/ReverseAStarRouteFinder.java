package pl.edu.pja.s28687.logistics;

import java.util.Comparator;
import java.util.List;

public class ReverseAStarRouteFinder implements IRouteFinder{

        private final LocoBase locobase;

        public ReverseAStarRouteFinder(LocoBase locobase) {
            this.locobase = locobase;
        }

        public List<RouteSegment> findRoute(TrainStation source, TrainStation destination) {
            Comparator<RouteFinder.StationNode> comparator
                    = RouteFinder.getStationNodeComparator(Heuristics.REVERSE_DISTANCE_PLUS_DESTINATION_DISTANCE);
            return RouteFinder.findRoute(source, destination, comparator, locobase);
        }
    }

