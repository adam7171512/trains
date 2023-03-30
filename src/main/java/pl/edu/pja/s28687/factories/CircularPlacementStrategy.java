package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.logistics.Coordinates;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CircularPlacementStrategy implements IStationPlacementStrategy {
    @Override
    public List<Coordinates> createCoordinates(int numberOfStations, int xBound, int yBound) {
        Random random = new Random();
        List<Coordinates> coordinatesList = new ArrayList<>();
        int wideRadius = xBound / 2;
        int radius = wideRadius;
        int offset = 50 + xBound / 2;
        for (int j = 0; j < 10; j++){
            for (int i = 0; i < numberOfStations / 10; i++){
                BigDecimal x = BigDecimal.valueOf(Math.random()*radius);
                double a = (
                        Math.sqrt(
                                Math.pow(radius, 2)
                                        - Math.pow((x.doubleValue()), 2)
                        ));
                BigDecimal y = BigDecimal.valueOf(a);
                x = random.nextInt(3) > 1 ? x : x.negate();
                y = random.nextInt(3) > 1 ? y : y.negate();

                x = x.add(BigDecimal.valueOf(offset));
                y = y.add(BigDecimal.valueOf(offset));
                coordinatesList.add(new Coordinates(x.intValue(), y.intValue()));

            }
                radius-= wideRadius * 0.07;
        }
        return coordinatesList;
    }
}

