package pl.edu.pja.s28687.consoleInterface.objectCreationMenus;


import pl.edu.pja.s28687.factories.TrainStationFactory;
import pl.edu.pja.s28687.logistics.Coordinates;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.TrainStation;

import java.util.Scanner;

public class TrainStationCreationMenu {
    private TrainStationFactory trainStationFactory;

    public TrainStationCreationMenu(TrainStationFactory trainStationFactory) {
        this.trainStationFactory = trainStationFactory;
    }

    public void createTrainStation(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter Train Station Name");
        String name = scan.next();
        System.out.println("Please enter Train Station X coordinate");
        int x = scan.nextInt();
        System.out.println("Please enter Train Station Y coordinate");
        int y = scan.nextInt();
        Coordinates coordinates = new Coordinates(x, y);
        trainStationFactory.createTrainStation(name, coordinates);
    }

}
