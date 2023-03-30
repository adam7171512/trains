package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.misc.PLCoordReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PolishTownsPlacementStrategy implements IStationPlacementStrategy{

    File file;
    BufferedReader bufferedReader;

    @Override
    public List<Coordinates> createCoordinates(int numberOfStations, int xBound, int yBound) {
        List<Coordinates> coordinatesList = new ArrayList<>();
        file = new File("src/main/java/pl/edu/pja/s28687/misc/coords.txt");
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String st;
        final double latDist = 73.07 * (xBound / 750.0);
        final double longDist = 119.19 * (yBound / 750.0);

        while (true) {
            try {
                st = bufferedReader.readLine();
                if ((st = bufferedReader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String[] r = st.trim().split("\\s+|\\)|\\(");
            String name = "";
            String latitude;
            String longitude;
            longitude = r[r.length - 1];
            latitude = r[r.length - 2];
            for (int i = 0; i <= r.length - 3; i++) {
                name += r[i];
            }
            String[] lat = latitude.split("[°']");
            String[] longit = longitude.split("[°']");

            double latD = Double.parseDouble(lat[0]);
            double longitD = Double.parseDouble(longit[0]);

            if (latD > 24 || latD < 14  || longitD > 55|| longitD < 49) continue;

            double xCoord = (latD - 14) * latDist
                    + (Double.parseDouble(lat[1]) / 60 ) * latDist;
            double yCoord = (longitD - 49) * longDist
                    + (Double.parseDouble(longit[1]) / 60 ) * longDist;
            yCoord = yBound - yCoord;
            Coordinates coordinates = new Coordinates(xCoord, yCoord);
            coordinatesList.add(coordinates);
    }
        return coordinatesList;
    }
}
