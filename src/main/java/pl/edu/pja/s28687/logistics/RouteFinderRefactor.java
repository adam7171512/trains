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
        private BigDecimal distance;
        private BigDecimal heuristic;

        public StationNode(TrainStation station, StationNode parent, BigDecimal distance, BigDecimal heuristic){
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

        public BigDecimal getDistance(){
            return distance;
        }

        public double getHeuristic(){
            return heuristic.doubleValue();
        }

        public double getF(){
            return distance.add(heuristic).doubleValue();
        }

        public List<StationNode> getChildren(){
            return children;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StationNode that)) return false;
            return station.equals(that.station) && Objects.equals(parent, that.parent) && Objects.equals(children, that.children) && Objects.equals(distance, that.distance) && Objects.equals(heuristic, that.heuristic);
        }

        @Override
        public int hashCode() {
            return Objects.hash(station, parent, children, distance, heuristic);
        }
    }

    public static List<RailroadLink> getRoute(List<TrainStation> path, LocoBase locoBase){

        List<RailroadLink> route = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            TrainStation station = path.get(i);
            TrainStation nextStation = path.get(i + 1);
            RailroadLink rL = locoBase.getLink(station, nextStation);
            route.add(rL);
        }
        return route;

    }



    public static Optional<List<RailroadLink>> findRoute2(TrainStation source, TrainStation destination, LocoBase locoBase, Optional<LocoMap> map) throws InterruptedException {
        PriorityQueue<StationNode> queue = new PriorityQueue<>(Comparator.comparingDouble(station -> station.getF()));
        List<StationNode> path = new ArrayList<>();
        Set<TrainStation> visited = new HashSet<>();
        StationNode start = new StationNode(source, null, BigDecimal.ZERO, BigDecimal.ZERO);
        queue.add(start);
        int n = 0;
        while (!queue.isEmpty()){
            StationNode current = queue.poll();

            if (visited.contains(current.getStation())){
                continue;
            }
            System.out.println(n++);
            System.out.println(current.getStation().getName());

            Thread.sleep(100);
            if (map.isPresent()) System.out.println("map present");
            map.ifPresent(locoMap -> locoMap.lightUpStation(current.getStation()));

            visited.add(current.getStation());
            if (current.getStation() == destination){
                path.add(current);
                break;
            }
            for (TrainStation trainStation : current.getStation().getNeighbors()){
                StationNode child =
                        new StationNode(
                                trainStation,
                                current,
                                current.getDistance()
                                        .add((locoBase.calcDistance(current.getStation(), trainStation))),
                                BigDecimal.ZERO);
                if (!queue.contains(child) && trainStation != current.getStation()){
                    queue.add(child);
                }
            }
        }
        if (path.isEmpty()) {
            return Optional.empty();
        }
        while (path.get(0).getParent() != null){
            path.add(0, path.get(0).getParent());
        }
        List<RailroadLink> route = getRoute(path.stream().map(StationNode::getStation).toList(), locoBase);
        return Optional.of(route);
    }


    public static Optional<List<RailroadLink>> findRouteGood(TrainStation source, TrainStation destination, LocoBase locoBase, Optional<LocoMap> map) throws InterruptedException {
        PriorityQueue<StationNode> queue = new PriorityQueue<>(Comparator.comparingDouble(station -> station.getF()));
        List<StationNode> path = new ArrayList<>();
        Set<TrainStation> visited = new HashSet<>();
        StationNode start = new StationNode(source, null, BigDecimal.ZERO, BigDecimal.ZERO);
        queue.add(start);
        int n = 0;

        while (!queue.isEmpty()){

            StationNode current = queue.poll();

            if (visited.contains(current.getStation())){
                continue;
            }
            System.out.println(n++);
            System.out.println(current.getStation().getName());

            Thread.sleep(100);
            if (map.isPresent()) System.out.println("map present");
            map.ifPresent(locoMap -> locoMap.lightUpStation(current.getStation()));


            visited.add(current.getStation());
            if (current.getStation() == destination){
                path.add(current);
                break;
            }
            for (TrainStation trainStation : current.getStation().getNeighbors()){
                StationNode child =
                        new StationNode(
                                trainStation,
                                current,
                                current.getDistance()
                                        .add((locoBase.calcDistance(current.getStation(), trainStation))),
                                locoBase.calcDistance(trainStation, destination));
                if (!queue.contains(child) && trainStation != current.getStation()){
                    queue.add(child);
                }
            }
        }
        if (path.isEmpty()) {
            return Optional.empty();
        }
        while (path.get(0).getParent() != null){
            path.add(0, path.get(0).getParent());
        }
        List<RailroadLink> route = getRoute(path.stream().map(StationNode::getStation).toList(), locoBase);
        return Optional.of(route);
    }


    public static Optional<List<RailroadLink>> findRoute(TrainStation source, TrainStation destination, LocoBase locoBase, Optional<LocoMap> map) throws InterruptedException {
        PriorityQueue<StationNode> queue = new PriorityQueue<>(Comparator.comparingDouble(station -> station.getHeuristic()));
        List<StationNode> path = new ArrayList<>();
        Set<TrainStation> visited = new HashSet<>();
        StationNode start = new StationNode(source, null, BigDecimal.ZERO, BigDecimal.ZERO);
        queue.add(start);
        int n = 0;

        while (!queue.isEmpty()){

            StationNode current = queue.poll();

            if (visited.contains(current.getStation())){
                continue;
            }
            System.out.println(n++);
            System.out.println(current.getStation().getName());

////            Thread.sleep(100);
//            if (map.isPresent()) System.out.println("map present");
//            map.ifPresent(locoMap -> locoMap.lightUpStation(current.getStation()));


            visited.add(current.getStation());
            if (current.getStation() == destination){
                path.add(current);
                break;
            }
            for (TrainStation trainStation : current.getStation().getNeighbors()){
                StationNode child =
                        new StationNode(
                                trainStation,
                                current,
                                current.getDistance()
                                        .add((locoBase.calcDistance(current.getStation(), trainStation))),
                                locoBase.calcDistance(trainStation, destination));
                if (!queue.contains(child) && trainStation != current.getStation()){
                    queue.add(child);
                }
            }
        }
        if (path.isEmpty()) {
            return Optional.empty();
        }
        while (path.get(0).getParent() != null){
            path.add(0, path.get(0).getParent());
        }
        List<RailroadLink> route = getRoute(path.stream().map(StationNode::getStation).toList(), locoBase);
        return Optional.of(route);
    }
}




