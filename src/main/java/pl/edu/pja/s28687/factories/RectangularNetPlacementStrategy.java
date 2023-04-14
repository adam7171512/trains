package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.logistics.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class RectangularNetPlacementStrategy implements IStationPlacementStrategy{
    @Override
    public List<Coordinates> createCoordinates(int numberOfStations, int xBound, int yBound) {

        if (numberOfStations < 1) {
            throw new IllegalArgumentException("Number of stations must be greater than 0");
        }

        List<Coordinates> coordinatesList = new ArrayList<>();

        int ratio = Math.ceilDiv(xBound, yBound);
        int columns = (int) Math.ceil(Math.sqrt(Math.ceilDiv(numberOfStations , ratio)));
        int rows = (int) Math.ceil(Math.ceilDiv(numberOfStations , columns));

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (coordinatesList.size() == numberOfStations) break;
                int xPos = ((xBound) / rows) * i;
                int yPos = ((yBound) / columns) * j;
                Coordinates c = new Coordinates(xPos, yPos);
                coordinatesList.add(c);
            }}
        return coordinatesList;
    }
}
