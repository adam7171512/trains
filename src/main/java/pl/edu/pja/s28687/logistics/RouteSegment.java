package pl.edu.pja.s28687.logistics;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.List;


public record RouteSegment(RailroadLink link, TrainStation source, TrainStation destination) {
    public BigDecimal getDistance() {
        return link.getDistance();
    }

    public List<Locomotive> getWaitingLocomotives() {
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
        return "Source : " + source().getName()
                + " Destination : " + destination().getName()
                + " Distance : " + getDistance();
    }
}
