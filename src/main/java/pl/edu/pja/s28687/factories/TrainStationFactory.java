package pl.edu.pja.s28687.factories;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.misc.PLCoordReader;
import pl.edu.pja.s28687.TrainStation;


public class TrainStationFactory {

    public static final List<String> prefixes = new ArrayList<>(List.of(
            "Smallest",
            "Small",
            "Medium",
            "Big",
            "Great",
            "New",
            "Old",
            "Dark",
            "Bright",
            "Creepy",
            "Smelly",
            "White",
            "Sunny",
            "Happy",
            "Rainy",
            "Beverly",
            "Windy"
    ));
    public static final List<String> suffixes = new ArrayList<>(List.of(
            "Alley",
            "Valley",
            "Forest",
            "Hills",
            "Newark",
            "Fields",
            "Forests",
            "River",
            "Meadows",
            "England",
            "Boston",
            "Wales",
            "Plants",
            "Tunnels",
            "Beach",
            "Compton",
            "Henderson",
            "Boats",
            "Gotham"
    ));
    public static final List<String> lastFixes = new ArrayList<>(List.of(
            "Station",
            "Town",
            "City",
            "Village",
            "Place",
            "Ville",
            "Township"));

    private LocoBase locoBase;

    private Random random;

    public TrainStationFactory(LocoBase locoBase){
        this.locoBase = locoBase;
    }


    public TrainStation createTrainStation(String name, int x, int y){
        TrainStation trainStation = new TrainStation(name, new Coordinates(x, y));
        locoBase.addTrainStation(trainStation);
        return trainStation;
    }

    public TrainStation createTrainStation(String name, Coordinates coordinates){
        TrainStation trainStation = new TrainStation(name, coordinates);
        locoBase.addTrainStation(trainStation);
        return trainStation;
    }

    public List<TrainStation> createTrainStationsPolishCoords()  {
        List<TrainStation> trainStations = new ArrayList<>();
        for (TrainStation ts : new PLCoordReader().prepareTrainStations()){
            locoBase.addTrainStation(ts);
            trainStations.add(ts);
        }
        return trainStations;
    }

    public List<TrainStation> createRandomTrainStations(int quantity
            , IStationPlacementStrategy placementStrategy
            , int xBound
            , int yBound) {

        List<TrainStation> trainStations = new ArrayList<>();
        List<Coordinates> coordinatesList =
                placementStrategy
                        .createCoordinates(quantity, xBound, yBound);
        for(Coordinates c : coordinatesList){
            Collections.shuffle(prefixes);
            Collections.shuffle(suffixes);
            Collections.shuffle(lastFixes);
            String name = prefixes.get(0) + " " + suffixes.get(0) + " " + lastFixes.get(0);
            TrainStation t = createTrainStation(name, c);
            trainStations.add(t);
        }

        return trainStations;
    }

    public List<TrainStation> createRandomTrainStations(int quantity){
        return createRandomTrainStations(quantity, new RandomPlacementStrategy(), 700, 700);
    }

    public void makeTrainStationsOfPolishTowns() throws IOException {
        for (TrainStation ts : new PLCoordReader().prepareTrainStations()){
            locoBase.addTrainStation(ts);
        }
    }
    }

