package pl.edu.pja.s28687;

import pl.edu.pja.s28687.Logistics.Coordinates;
import pl.edu.pja.s28687.Logistics.RailroadLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrainStation implements Comparable<TrainStation>{
    private Coordinates coordinates;
    private String name;

    private List<RailroadLink> railroadLinkList = new ArrayList<>();
    public TrainStation(String name, Coordinates coordinates){
        this.name = name;
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }



    public void addTrainDirectConnection(RailroadLink connection){
        railroadLinkList.add(connection);
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
        return coordinates.equals(that.coordinates) && name.equals(that.name);
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
