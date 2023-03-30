package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.logistics.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class RectangularNetPlacementStrategy implements IStationPlacementStrategy{
    @Override
    public List<Coordinates> createCoordinates(int numberOfStations, int xBound, int yBound) {
        List<Coordinates> coordinatesList = new ArrayList<>();
        int x = (int) Math.sqrt(numberOfStations);

        for (int i = 0; i < x; i++){
            for (int j = 0; j < x; j++){
                int xPos = 30 + ((xBound - 30) / x) * i;
                int yPos = 30 + ((yBound - 30) / x) * j;
                System.out.println("xPos: " + xPos + " yPos: " + yPos);
                Coordinates c = new Coordinates(xPos, yPos);
                coordinatesList.add(c);
            }}
        return coordinatesList;
    }
}
