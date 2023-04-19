package pl.edu.pja.s28687.logistics;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.List;


public class RouteSegment {
    private final RailroadLink link;
    private final TrainStation source;
    private final TrainStation destination;

    public RouteSegment(RailroadLink link, TrainStation source, TrainStation destination){
        this.link = link;
        this.source = source;
        this.destination = destination;
    }
    public RailroadLink getLink() {
        return link;
    }
    public TrainStation getSource(){
        return source;
    }
    public TrainStation getDestination(){
        return destination;
    }
    public BigDecimal getDistance(){
        return link.getDistance();
    }
    public List<Locomotive> getWaitingLocomotives(){
        return link.getWaitingLocomotives();
    }

    public void enterRailway(Locomotive locomotive) {
        link.enterRailway(locomotive);
    }

    public void leaveRailway(Locomotive locomotive) {
        link.leaveRailway(locomotive);
    }

    @Override
    public String toString() {
        return "Source : " + getSource().getName()
                + " Destination : " + getDestination().getName()
                + " Distance : " + getDistance();
    }
}
