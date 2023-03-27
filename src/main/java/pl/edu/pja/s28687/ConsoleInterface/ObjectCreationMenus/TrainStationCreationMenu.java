package pl.edu.pja.s28687.ConsoleInterface.ObjectCreationMenus;


import pl.edu.pja.s28687.Logistics.Coordinates;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.TrainStation;

import java.util.Scanner;

public class TrainStationCreationMenu {

    public static void createTrainStation(LocoBase locoBase){
        addToBase(make(), locoBase);
    }

    private static void addToBase(TrainStation trainStation, LocoBase locoBase){
        locoBase.addTrainStation(trainStation);
    }
    private static TrainStation make(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter Train Station Name");
        String name = scan.next();
        System.out.println("Please enter Train Station X coordinate");
        int x = scan.nextInt();
        System.out.println("Please enter Train Station Y coordinate");
        int y = scan.nextInt();
        Coordinates coordinates = new Coordinates(x, y);
        return new TrainStation(name, coordinates);
    }

}
