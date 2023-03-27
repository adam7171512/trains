package pl.edu.pja.s28687.ConsoleInterface.ObjectCreationMenus;


import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Logistics.RailroadLink;
import pl.edu.pja.s28687.TrainStation;

import java.util.Scanner;

public class TrainConnectionCreationMenu {

    public static void createConnection(LocoBase locoBase){
        addToBase(make(locoBase), locoBase);
    }

    private static void addToBase(RailroadLink connection, LocoBase locoBase){
        locoBase.registerRailroadConnection(connection);
    }
    private static RailroadLink make(LocoBase locoBase){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter first Train Station Name");
        String name1 = scan.next();
        TrainStation ts1 = locoBase.findTrainStation(name1).get();
        System.out.println("Please enter second Train Station Name");
        String name2 = scan.next();
        TrainStation ts2 = locoBase.findTrainStation(name2).get();
        return new RailroadLink(ts1, ts2);
    }

}
