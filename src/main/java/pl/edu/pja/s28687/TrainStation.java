package pl.edu.pja.s28687;

import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.RailroadLink;

import java.util.*;

public class TrainStation implements Comparable<TrainStation>{
    private final Coordinates coordinates;
    private final String name;

    private List<RailroadLink> railroadLinkList = new ArrayList<>();
    private Set<TrainStation> neighbors = new HashSet<>();
    public TrainStation(String name, Coordinates coordinates){
        this.name = name;
        this.coordinates = coordinates;
    }

    public TrainStation(String name, int x, int y){
        this.name = name;
        this.coordinates = new Coordinates(x, y);
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public void addNeighbor(TrainStation neighbor){
        neighbors.add(neighbor);
    }

    public Set<TrainStation> getNeighbors(){
        return neighbors;
    }



    public void addTrainDirectConnection(RailroadLink connection){
        railroadLinkList.add(connection);
        neighbors.add(getConnectionDest(connection));
    }


    public List<RailroadLink> getTrainDirectConnectionList(){
        return railroadLinkList;
    }

    public TrainStation getConnectionDest(RailroadLink connection){
        return connection.getStation1() == this
                ? connection.getStation2()
                : connection.getStation1();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainStation that)) return false;
        return
                coordinates.equals(that.coordinates)
                        &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }


    @Override
    public int compareTo(TrainStation o) {
        return this.getCoordinates().compareTo(o.getCoordinates());
    }
}
