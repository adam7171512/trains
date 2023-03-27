package pl.edu.pja.s28687.Factories;

import pl.edu.pja.s28687.Conductor;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Logistics.RailroadLink;
import pl.edu.pja.s28687.Logistics.RouteFindingAlgos;
import pl.edu.pja.s28687.TrainStation;

import java.math.BigDecimal;
import java.util.*;

public class LocomotiveFactory {

    public static Random random = new Random();

    public static final List<String> prefixes = new ArrayList<>(List.of(
            "Small",
            "Big",
            "Fast",
            "Slow",
            "Happy",
            "Sad",
            "Angry",
            "Fastest",
            "Heavy",
            "Lightweight",
            "Smart",
            "Silly",
            "Angry",
            "Drunk",
            "Careless"
    ));
    public static final List<String> suffixes = new ArrayList<>(List.of(
            "Joe",
            "Musk",
            "Steve",
            "Adam",
            "The King",
            "Tadeusz",
            "Jola",
            "Batman",
            "Superman",
            "Chojrak",
            "Dog",
            "Cat",
            "Pants",
            "Monkey",
            "Gonzales",
            "Goofy"
    ));

    public static void makeRandomLocomotives(int quantity, RouteFindingAlgos algorithm, LocoBase locoBase){
        for (int i = 0; i < quantity; i++){
            Collections.shuffle(prefixes);
            Collections.shuffle(suffixes);
            String name = prefixes.get(0) + " " + suffixes.get(0);
            int regular = random.nextInt(50) + 9;
            int powered = random.nextInt(20) + 5;
            BigDecimal maxF = BigDecimal.valueOf(random.nextInt(9000) + 400);
            BigDecimal speed = BigDecimal.valueOf(random.nextInt(100) + 50);

            Locomotive l = new Locomotive(name, regular, maxF, powered, speed, LocoBase.getInstance());
            List<TrainStation> stations = locoBase.getTrainStations().stream().toList();
            int r = random.nextInt(stations.size());
            l.setHomeStation(stations.get(r));
            l.setSourceStation(stations.get(r));
            int h = random.nextInt(stations.size());
            l.setDestStation(stations.get(h));
            Conductor c = new Conductor(l, locoBase);
            c.setLogisticSkill(algorithm);
            l.setConductor(c);
            c.start();
        }
    }

    public static void makeRandomLocomotives(int quantity, LocoBase locoBase){
        makeRandomLocomotives(quantity, RouteFindingAlgos.NAIVE, locoBase);
    }

    public static void makeDLoc(int quantity, LocoBase locoBase){
        for (int i = 0; i < quantity; i++){
            Collections.shuffle(prefixes);
            Collections.shuffle(suffixes);
            String name = prefixes.get(0) + " " + suffixes.get(0);
            int regular = random.nextInt(50) + 9;
            int powered = random.nextInt(20) + 5;
            BigDecimal maxF = BigDecimal.valueOf(random.nextInt(9000) + 400);
            BigDecimal speed = BigDecimal.valueOf(random.nextInt(140) + 200000);

            Locomotive l = new Locomotive(name, regular, maxF, powered, speed, LocoBase.getInstance());
            List<TrainStation> stations = locoBase.getTrainStations().stream().toList();


            List <TrainStation> dickStations = TrainStationFactory.makeDickStations();
            l.setHomeStation(dickStations.get(0));
            l.setSourceStation(dickStations.get(0));
//            int h = random.nextInt(stations.size());
            l.setDestStation(dickStations.get(1));
            List<RailroadLink> route = new ArrayList<>();
            Conductor c = new Conductor(l, locoBase);
            for (int j = 0; j < dickStations.size() - 1; j++){
                RailroadLink rl = new RailroadLink(dickStations.get(j), dickStations.get(j + 1));
                route.add(rl);
            }
            c.setRoute(route);
            System.out.println(route);
//            l.setConductor(c);
            c.start();
        }
    }
}
