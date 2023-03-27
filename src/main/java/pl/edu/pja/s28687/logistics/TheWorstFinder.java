package pl.edu.pja.s28687.Logistics;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.*;

public class TheWorstFinder {

    public static Optional<List<RailroadLink>> findRoute(TrainStation source, TrainStation destination, LocoBase locoBase){



        List<SegmentNode> longest_path = new ArrayList<>();
        int max = Integer.MIN_VALUE;

         RailroadLink rL = source.getTrainDirectConnectionList().get(0);
        PriorityQueue<SegmentNode> queue = new PriorityQueue<>(new Comparator<SegmentNode>() {
            @Override
            public int compare(SegmentNode o1, SegmentNode o2) {
                return locoBase.calcDistance(o2.getTarget(), destination)
                        .compareTo(locoBase.calcDistance(o1.getTarget(), destination));
            }
        });
        Set<SegmentNode> visited = new HashSet<>();
        List<SegmentNode> path = new ArrayList<>();
        SegmentNode l = makeSegmentNode(source, rL);
        findSegmentPath(source, destination, l, visited, queue, path);


        longest_path = path;


        Collections.reverse(longest_path);
        if (longest_path.isEmpty()){
            return Optional.empty();
        }
        else return Optional.of(longest_path
                .stream()
                .map(SegmentNode::getLink)
                .toList());
    }

    private static void findSegmentPath(TrainStation source,
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
            int chance = new Random().nextInt(6);
            if (
                    ! (visited.contains(l) || chance == 4)
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

    private static SegmentNode makeSegmentNode(TrainStation source, RailroadLink rL){
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

    private static class SegmentNode {
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
