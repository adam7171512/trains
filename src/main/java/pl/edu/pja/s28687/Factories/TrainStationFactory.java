package pl.edu.pja.s28687.Factories;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import pl.edu.pja.s28687.Logistics.Coordinates;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Misc.PLCoordReader;
import pl.edu.pja.s28687.TrainStation;


public class TrainStationFactory {

    private static Random random = new Random();
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


    public static TrainStation makeTrainStation(String name, int x, int y){
        return new TrainStation(name, new Coordinates(x, y));
    }

    public static void makeRandomTrainStations(int quantity, LocoBase locoBase){
        for (int i = 0; i < quantity; i++){
            Collections.shuffle(prefixes);
            Collections.shuffle(suffixes);
            Collections.shuffle(lastFixes);
            String name = prefixes.get(0) + " " + suffixes.get(0) + " " + lastFixes.get(0);
            int x = random.nextInt(1600) + 100;
            int y = random.nextInt(900) + 100;

            TrainStation t = makeTrainStation(name, x, y);
            locoBase.addTrainStation(t);
    }
    }

    public static void makeTrainStationsOfPolishTowns(LocoBase locoBase) throws IOException {
        for (TrainStation ts : new PLCoordReader().prepareTrainStations()){
            locoBase.addTrainStation(ts);
        }
    }

    public static void makeRandomTrainStationsSquaredNet(int quantity, LocoBase locoBase){
        int x = (int) Math.sqrt(quantity);
        for (int i = 0; i < x; i++){

            for (int j = 0; j < x; j++){
            Collections.shuffle(prefixes);
            Collections.shuffle(suffixes);
            Collections.shuffle(lastFixes);
            String name = prefixes.get(0) + " " + suffixes.get(0) + " " + lastFixes.get(0);
            TrainStation t = makeTrainStation(name,50 + (1400/x) * i, 50 + (800/x) * j);
            locoBase.addTrainStation(t);
        }}
    }


    public static void makeRandomStationsCircularPlacement(int quantity, LocoBase locoBase){
        int firstRadius = 340;
        int radius = firstRadius;
        int delta = 200;
        for (int j = 0; j < quantity / 10; j++){
        for (int i = 0; i < quantity / 10; i++){
            BigDecimal x = BigDecimal.valueOf(Math.random()*radius);
            double a = (
                    Math.sqrt(
                            Math.pow(radius, 2)
                    - Math.pow((x.doubleValue()), 2)
                    ));
            BigDecimal y = BigDecimal.valueOf(a);
            x = random.nextInt(3) > 1 ? x : x.negate();
            y = random.nextInt(3) > 1 ? y : y.negate();

            x = x.add(BigDecimal.valueOf(delta));
            y = y.add(BigDecimal.valueOf(delta));

            Collections.shuffle(prefixes);
            Collections.shuffle(suffixes);
            Collections.shuffle(lastFixes);
            String name = prefixes.get(0) + " " + suffixes.get(0) + " " + lastFixes.get(0);
            TrainStation t = makeTrainStation(name,delta + x.intValue(), delta +y.intValue());
            locoBase.addTrainStation(t);
            }
        radius-= firstRadius * 0.07;
        }}

    public static List<TrainStation>  makeDickStations(){
        String[] arr = {
            "370 700",
            "200 650",
            "150 520",
            "200 500",
            "370 520",
                "400 520",
            "460 250",
            "440 180",
            "600 210",
            "700 200",
            "730 170",
            "710 110",
            "570 50",
            "570 120",
            "540 50",
            "470 50",
            "400 110",
            "500 170",
            "500 210",
            "700 200",
            "720 500",
            "830 510",
            "900 530",
            "1000 620",
            "900 700",
            "500 700",
        };
        List<TrainStation> stations = new ArrayList<>();
        for(String s: arr){
            int x = Integer.parseInt(s.split(" ")[0]);
            int y = Integer.parseInt(s.split(" ")[1]);
            TrainStation ts = new TrainStation(prefixes.get(0) + suffixes.get(0),new Coordinates(x, y));
            stations.add(ts);
        }
        return stations;
    }
    }

