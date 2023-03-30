package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.logistics.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPlacementStrategy implements IStationPlacementStrategy{
    @Override
    public List<Coordinates> createCoordinates(int numberOfStations, int xBound, int yBound) {
        Random random = new Random();
        List<Coordinates> coordinatesList = new ArrayList<>();
        for (int i = 0; i < numberOfStations; i++) {
            int x = random.nextInt(xBound - 30) + 30;
            int y = random.nextInt(yBound - 30) + 30;
            coordinatesList.add(new Coordinates(x, y));
        }
        return coordinatesList;
    }
}
