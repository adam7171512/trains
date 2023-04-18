package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.gui.LocoMap;

import java.math.BigDecimal;
import java.util.*;

public class RouteFinderRefactor {

    static class StationNode {
        private TrainStation station;
        private StationNode parent;
        private List<StationNode> children;
        private double distance;
        private double heuristic;

        public StationNode(TrainStation station, StationNode parent, double distance, double heuristic){
            this.station = station;
            this.parent = parent;
            this.distance = distance;
            this.heuristic = heuristic;
        }

        public TrainStation getStation(){
            return station;
        }

        public StationNode getParent(){
            return parent;
        }

        public double getDistanceFromSourceStation(){
            return distance;
        }

        public double getHeuristic(){
            return heuristic;
        }

        public double getF(){
            return distance + heuristic;
        }

        public Set<TrainStation> getStationNeighbours(){
            return station.getNeighbors();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StationNode that)) return false;
            return station.equals(that.station);
        }

        @Override
        public int hashCode() {
            return Objects.hash(station);
        }
    }

    public static List<RailroadLink> findRoute(TrainStation source, TrainStation destination, LocoBase locoBase){
        Comparator<StationNode> pathComparator = getStationNodeComparator(Heuristics.DISTANCE_PLUS_DESTINATION_DISTANCE);
        LinkedList<StationNode> path = getNodePath(source, destination, pathComparator);
        return convertStationNodeListToRoute(path, locoBase);
    }

    public static List<RailroadLink> findRoute(TrainStation source, TrainStation destination, Comparator<StationNode> comparator, LocoBase locoBase){
        LinkedList<StationNode> path = getNodePath(source, destination, comparator);
        return convertStationNodeListToRoute(path, locoBase);
    }

    public static List<RailroadLink> findRouteDFS(TrainStation source, TrainStation destination, LocoBase locoBase){
        LinkedList<StationNode> path = getNodePathDFS(source, destination);
        return convertStationNodeListToRoute(path, locoBase);
    }

    public static List<RailroadLink> convertStationNodeListToRoute(List<StationNode> path, LocoBase locoBase){
        LinkedList<RailroadLink> route = new LinkedList<>();
        path.forEach(node  -> {
            if (node.getParent() != null) {
                locoBase.findLink(
                        Set.of(node.getStation(), node.getParent().getStation()))
                        .ifPresentOrElse(
                        route::addFirst,
                        () -> {
                            throw new IllegalStateException(
                                    "No link found between "
                                            + node.getStation() + " and "
                                            + node.getParent().getStation());
                        });
            }
        });
        return route;
    }

    public static Comparator<StationNode> getStationNodeComparator(Heuristics heuristic){
        return switch (heuristic) {
            case DISTANCE -> Comparator.comparingDouble(StationNode::getDistanceFromSourceStation);
            case DISTANCE_PLUS_DESTINATION_DISTANCE -> Comparator.comparingDouble(StationNode::getF);
            case REVERSE_DISTANCE -> (o1, o2) -> {
                if (o1.getDistanceFromSourceStation() == o2.getDistanceFromSourceStation())
                    return 0;
                return o1.getDistanceFromSourceStation() < o2.getDistanceFromSourceStation() ? 1 : -1;
            };
            case REVERSE_DISTANCE_PLUS_DESTINATION_DISTANCE -> (o1, o2) -> {
                if (o1.getF() == o2.getF())
                    return 0;
                return o1.getF() < o2.getF() ? 1 : -1;
            };
        };
    }

    public static LinkedList<StationNode> getNodePathDFS(TrainStation source, TrainStation destination){
        Deque<StationNode> stack = new ArrayDeque<>();
        LinkedList<StationNode> path = new LinkedList<>();
        Set<StationNode> visited = new HashSet<>();
        StationNode start = new StationNode(source, null, 0, 0);
        stack.push(start);

        while (!stack.isEmpty()){
            StationNode current = stack.pop();
            visited.add(current);

            if (current.getStation() == destination){
                path.add(current);
                while (current.getParent() != null){
                    current = current.getParent();
                    path.add(current);
                }
                break;
            }

            for (TrainStation trainStation : current.getStationNeighbours()){
                StationNode child =
                        new StationNode(
                                trainStation,
                                current,
                                0,
                                         0);
                if (!stack.contains(child) && !visited.contains(child)){
                    stack.push(child);
                }
            }
        }
        return path;
    }

    public static LinkedList<StationNode> getNodePath(TrainStation source, TrainStation destination, Comparator<StationNode> pathComparator) {
        PriorityQueue<StationNode> queue = new PriorityQueue<>(pathComparator);
        LinkedList<StationNode> path = new LinkedList<>();
        Set<StationNode> visited = new HashSet<>();
        StationNode start = new StationNode(source, null, 0, 0);
        queue.add(start);

        while (!queue.isEmpty()){

            StationNode current = queue.poll();
            visited.add(current);

            if (current.getStation() == destination){
                path.add(current);
                while (current.getParent() != null){
                    current = current.getParent();
                    path.add(current);
                }
                break;
            }
            for (TrainStation trainStation : current.getStationNeighbours()){
                double segmentDistance = LocoBase.calcDistance(current.getStation(), trainStation).doubleValue();  //todo: move from locobase
                double straightLineDistanceToDestination = LocoBase.calcDistance(trainStation, destination).doubleValue();
                StationNode child =
                        new StationNode(
                                trainStation,
                                current,
                                current.getDistanceFromSourceStation()
                                         + (segmentDistance),
                                straightLineDistanceToDestination);
                if (!queue.contains(child) && !visited.contains(child)){
                    queue.add(child);
                }
            }
        }
        return path;
    }
}




