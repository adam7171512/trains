package pl.edu.pja.s28687.logistics;

import org.junit.jupiter.api.RepeatedTest;
import pl.edu.pja.s28687.factories.RailroadsFactory;
import pl.edu.pja.s28687.factories.TrainStationFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteFindersTest {
    private static final LocoBase locobase = new LocoBase();
    private static final TrainStationFactory factory = new TrainStationFactory(locobase);
    private static final RailroadsFactory railroadsFactory = new RailroadsFactory(locobase);
    private static final IRouteFinder depthFirstSearchRouteFinder = new DepthFirstSearchRouteFinder(locobase);
    private static final IRouteFinder dijkstraRouteFinder = new DijkstraRouteFinder(locobase);
    private static final IRouteFinder aStarRouteFinder = new AStarRouteFinder(locobase);
    private static final IRouteFinder reverseAStarRouteFinder = new ReverseAStarRouteFinder(locobase);
    private static final IRouteFinder reverseDijkstraRouteFinder = new ReverseDijkstraRouteFinder(locobase);
    private static final List<TrainStation> stations = prepareStations();


    static {
        prepareConnections();
    }

    /**
     * Prepared test map:
     * 4  - 9 - 14   19
     * |    |   |    |
     * 3 -  8 - 13 - 18
     * |    |   |    |
     * 2 -  7   12   17
     * |    |   |    |
     * 1 -  6   11   16
     * |    |   |    |
     * 0 -  5 - 10 - 15
     */
    private static List<TrainStation> prepareStations(){
        List<TrainStation> stations = new ArrayList<>();
        int n = 0;
        for (int i = 0; i <= 40; i+=10){
            for (int j = 0; j <= 40; j+=10){
                stations.add(factory.createTrainStation(String.valueOf(n), i, j));
                n++;
            }
        }
        stations.add(factory.createTrainStation("Separated", 50, 50));
        return stations;
    }

    private static void prepareConnections(){
        for (int i = 0; i < 19; i++){
            if (i == 4 || i == 9 || i == 14) continue;
            railroadsFactory.createRailroadLink(stations.get(i), stations.get(i+1));
            }
        railroadsFactory.createRailroadLink(stations.get(0), stations.get(5));
        railroadsFactory.createRailroadLink(stations.get(1), stations.get(6));
        railroadsFactory.createRailroadLink(stations.get(2), stations.get(7));
        railroadsFactory.createRailroadLink(stations.get(3), stations.get(8));
        railroadsFactory.createRailroadLink(stations.get(4), stations.get(9));

        railroadsFactory.createRailroadLink(stations.get(6), stations.get(11));
        railroadsFactory.createRailroadLink(stations.get(8), stations.get(13));
        railroadsFactory.createRailroadLink(stations.get(9), stations.get(14));

        railroadsFactory.createRailroadLink(stations.get(10), stations.get(15));
        railroadsFactory.createRailroadLink(stations.get(13), stations.get(18));
    }

    @RepeatedTest(10)
    void findRoute_AStarRouteFinder_Station7to19_ShouldCreate4SegmentRoute() {
        var route = aStarRouteFinder.findRoute(stations.get(7), stations.get(19));
        assertEquals(4, route.size());
    }

    @RepeatedTest(10)
    void findRoute_DijkstraRouteFinder_Station7to19_ShouldCreate4SegmentRoute() {
        var route = dijkstraRouteFinder.findRoute(stations.get(7), stations.get(19));
        assertEquals(4, route.size());
    }

    @RepeatedTest(10)
    void findRoute_ReversedDijkstraRouteFinder_Station7to19_ShouldCreate6SegmentRoute() {
        var route = reverseDijkstraRouteFinder.findRoute(stations.get(7), stations.get(19));
        assertEquals(6, route.size());

    }

    @RepeatedTest(10)
    void findRoute_ReversedAStarRouteFinder_Station7to19_ShouldCreate8SegmentRoute() {
        var route = reverseAStarRouteFinder.findRoute(stations.get(7), stations.get(19));
        assertEquals(8, route.size());
    }

    @RepeatedTest(10)
    void findRoute_DepthFirstSearchRouteFinder_Station7to19_ShouldCreate8SegmentRoute() {
        var route = depthFirstSearchRouteFinder.findRoute(stations.get(7), stations.get(19));
        assertEquals(8, route.size());
    }

    @RepeatedTest(10)
    void findRoute_AStarRouteFinder_WhenThereIsNoLink_ShouldReturnEmptyList() {
        var route = aStarRouteFinder.findRoute(stations.get(7), stations.get(20));
        assertTrue(route.isEmpty());
    }
}