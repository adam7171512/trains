package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.logistics.Coordinates;

import java.util.List;

public interface IStationPlacementStrategy {
    List<Coordinates> createCoordinates(int numberOfStations, int xBound, int yBound);
}
