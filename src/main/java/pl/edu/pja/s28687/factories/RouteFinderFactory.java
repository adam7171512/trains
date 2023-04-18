package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.logistics.*;

public class RouteFinderFactory {

    public static IRouteFinder getRouteFinder(RouteFindingAlgos algo, LocoBase locobase) {
        return switch (algo) {
            case DIJKSTRA -> new DijkstraRouteFinder(locobase);
            case A_STAR -> new AStarRouteFinder(locobase);
            case REVERSED_A_STAR -> new ReverseAStarRouteFinder(locobase);
            case DFS -> new DepthFirstSearchRouteFinder(locobase);
        };
    }
}
