package pl.edu.pja.s28687.ConsoleInterface;

import pl.edu.pja.s28687.ConsoleInterface.ObjectCreationMenus.*;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.Scanner;

public class ObjectCreationMenu {
    private static final String menuChoices =
            """
                ______________________________
                Object creation menu
                What would you like to create?
                ______________________________\s
                1 Train Stations\s
                2 Locomotives\s
                3 Railroad cars\s
                4 Deliverables
                5 Railroad connections
                6.Automated bulk creation
                0 Back
                _______________________________""";
    public static void menu(LocoBase locoBase){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuChoices);
            selection = scan.nextInt();
            goTo(selection, locoBase);
        }
    }

    public static void goTo(int selection, LocoBase locoBase){
        switch (selection) {
            case 1 -> TrainStationCreationMenu.createTrainStation(locoBase);
            case 2 -> LocomotiveCreationMenu.createLocomotive(locoBase);
            case 3 -> RailroadCarCreationMenu.createCar(locoBase);
            case 4 -> LoadCreationMenu.menu(locoBase);
            case 5 -> TrainConnectionCreationMenu.createConnection(locoBase);
            case 6 -> BulkCreationMenu.menu(locoBase);
            default -> System.out.println("default");
        }
    }
}
