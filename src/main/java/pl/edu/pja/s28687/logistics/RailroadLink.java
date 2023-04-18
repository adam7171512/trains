package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RailroadLink {
    private final TrainStation station1;
    private final TrainStation station2;
    private final Set<TrainStation> stations;
    private BigDecimal distance;
    private RailroadLock lock = new RailroadLock();

    public RailroadLink(TrainStation station1, TrainStation station2) {
        this.station1 = station1;
        this.station2 = station2;
        this.stations = Set.of(station1, station2);
        addConnections();
    }

    private void addConnections() {
        station1.addTrainDirectConnection(this);
        station2.addTrainDirectConnection(this);
    }

    public TrainStation getStation1() {
        return station1;
    }

    public TrainStation getStation2() {
        return station2;
    }

    public void enterRailway() {
        lock.lock();
    }

    public void enterRailway(Locomotive locomotive) {
        lock.lock(locomotive);
    }

    public void leaveRailway() {
        lock.unlock();
    }

    public void leaveRailway(Locomotive locomotive) {
        lock.unlock();
    }

    public List<Locomotive> getWaitingLocomotives() {
        return lock.getWaitingLocomotives();
    }

    public boolean isBusy() {
        return lock.isLocked();
    }

    public BigDecimal getDistance() {
        if (distance == null) calcDistance();
        return distance;
    }

    private void calcDistance() {
        Coordinates sourceCoords = station1.getCoordinates();
        Coordinates destCoords = station2.getCoordinates();
        double xDist = Math.abs(sourceCoords.getX() - destCoords.getX());
        double yDist = Math.abs(sourceCoords.getY() - destCoords.getY());
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
        distance = BigDecimal.valueOf(dist).setScale(2, RoundingMode.CEILING);
    }

    @Override
    public String toString() {
        return "Railway " +
                "between " + station1 +
                " and " + station2 +
                ", distance=" + distance +
                " km";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RailroadLink that)) return false;
        return Set.of(station1, station2).equals(Set.of(that.station1, that.station2));
    }

    @Override
    public int hashCode() {
        return Objects.hash(station1, station2);
    }

    public Set<TrainStation> getStations() {
        return stations;
    }
}
