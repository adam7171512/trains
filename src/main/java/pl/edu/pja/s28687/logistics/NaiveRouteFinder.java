package pl.edu.pja.s28687.logistics;
import pl.edu.pja.s28687.TrainStation;

import java.util.*;

public class NaiveRouteFinder implements IRouteFinder{

    private LocoBase locoBase;

    public NaiveRouteFinder(LocoBase locoBase){
        this.locoBase = locoBase;
    }


    public Optional<List<RailroadLink>> findRoute(TrainStation source, TrainStation destination){



        List<SegmentNode> shortest_path = new ArrayList<>();
        int max = Integer.MAX_VALUE;

        for (RailroadLink rL : source.getTrainDirectConnectionList()){
            PriorityQueue<SegmentNode> queue = new PriorityQueue<>((o1, o2) -> locoBase.calcDistance(o1.getTarget(), destination)
                    .compareTo(locoBase.calcDistance(o2.getTarget(), destination)));
            Set<SegmentNode> visited = new HashSet<>();
            List<SegmentNode> path = new ArrayList<>();
            SegmentNode l = makeSegmentNode(source, rL);
            findSegmentPath(source, destination, l, visited, queue, path);

            if (!path.isEmpty() && path.size() < max) {
                max = path.size();
                shortest_path = path;
            }
        }
        Collections.reverse(shortest_path);
        if (shortest_path.isEmpty()){
            return Optional.empty();
        }
        else return Optional.of(shortest_path
                .stream()
                .map(SegmentNode::getLink)
                .toList());
    }

    private void findSegmentPath(TrainStation source,
                                        TrainStation destination,
                                        SegmentNode currentNode,
                                        Set<SegmentNode> visited,
                                        PriorityQueue<SegmentNode> queue,
                                        List<SegmentNode> path){

        if (! path.isEmpty()) return;
        if (currentNode.getTarget() == destination) {
            path.add(currentNode);
            return;
        }

        visited.add(currentNode);

        for(SegmentNode l : currentNode.getChildren()){
            if (
                    ! visited.contains(l)
                            && ! queue.contains(l)
                            && ! l.getTarget().equals(source)
            )
            {
                queue.add(l);
            }
        }

        if (! queue.isEmpty()){
            SegmentNode l = queue.remove();
            findSegmentPath(source, destination, l, visited, queue, path);
        }

        if (!path.isEmpty() && !path.get(path.size() - 1).getSource().equals(source)){
            path.add(path.get(path.size() - 1).getParent());
        }
    }

    private SegmentNode makeSegmentNode(TrainStation source, RailroadLink rL){
        TrainStation t1 = rL.getStation1();
        TrainStation t2 = rL.getStation2();
        TrainStation s;
        TrainStation d;

        if( t1.equals(source)) {
            s = t1;
            d = t2;
        }
        else {
            s = t2;
            d = t1;
        }
        return new SegmentNode(rL, s, d);
    }

    private class SegmentNode {
        private RailroadLink link;
        private TrainStation source;
        private TrainStation target;
        private SegmentNode parent;
        public SegmentNode(RailroadLink link, TrainStation source, TrainStation target){
            this.link = link;
            this.source = source;
            this.target = target;
        }

        public TrainStation getSource(){
            return source;
        }

        public TrainStation getTarget(){
            return target;
        }

        public void setParent(SegmentNode parent){
            this.parent = parent;
        }

        public SegmentNode getParent() {
            return parent;
        }

        public RailroadLink getLink() {
            return link;
        }

        public List<SegmentNode> getChildren(){
            List<RailroadLink> links = getTarget().getTrainDirectConnectionList();
            List<SegmentNode> children = new ArrayList<>();
            for (RailroadLink tdc : links){
                SegmentNode l = makeSegmentNode(this.getTarget(), tdc);
                l.setParent(this);
                children.add(l);
            }
            return children;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SegmentNode that)) return false;
            return Objects.equals(link, that.link) && Objects.equals(source, that.source) && Objects.equals(target, that.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(link, source, target);
        }
    }
}
