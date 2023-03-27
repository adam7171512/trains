package pl.edu.pja.s28687.ConsoleInterface;

import pl.edu.pja.s28687.ConsoleInterface.ObjectCreationMenus.*;
import pl.edu.pja.s28687.Factories.*;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.Scanner;

public class BulkCreationMenu {
    private static final String menuChoices =
            """
                ______________________________
                Automated bulk object creation
                What would you like to create?
                ______________________________\s
                1 Train Stations\s
                2 Locomotives\s
                3 Railroad cars\s
                4 Deliverables
                5 Railroad connections
                0 Back
                _______________________________""";
    public static void menu(LocoBase locoBase){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuChoices);
            selection = scan.nextInt();
            System.out.println("How many objects would you like to make?" +
                    "If you selected Railroads, its connections per station!");
            int n = scan.nextInt();
            goTo(selection, n, locoBase);
        }
    }

    public static void goTo(int selection, int number, LocoBase locoBase){
        switch (selection) {
            case 1 -> TrainStationFactory.makeRandomTrainStations(number, locoBase);
            case 2 -> LocomotiveFactory.makeRandomLocomotives(number, locoBase);
            case 3 -> CarsFactory.makeRandomCars(number, locoBase);
            case 4 -> LoadFactory.makeRandomLoads(number, locoBase);
            case 5 -> RailroadsFactory.makeRandomRailroadsConnections(number, locoBase);
            default -> System.out.println("default");
        }
    }
}
