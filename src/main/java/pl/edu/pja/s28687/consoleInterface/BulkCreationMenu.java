package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Scanner;

public class BulkCreationMenu {

    private CarsFactory carsFactory;
    private LoadFactory loadFactory;
    private RailroadsFactory railroadsFactory;
    private TrainStationFactory trainStationFactory;
    private LocomotiveFactory locomotiveFactory;


    public BulkCreationMenu(CarsFactory carsFactory,
                            LoadFactory loadFactory,
                            RailroadsFactory railroadsFactory,
                            TrainStationFactory trainStationFactory,
                            LocomotiveFactory locomotiveFactory){
        this.carsFactory = carsFactory;
        this.loadFactory = loadFactory;
        this.railroadsFactory = railroadsFactory;
        this.trainStationFactory = trainStationFactory;
        this.locomotiveFactory = locomotiveFactory;
    }
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
    public void menu(){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuChoices);
            selection = scan.nextInt();
            System.out.println("How many objects would you like to make?" +
                    "If you selected Railroads, its connections per station!");
            int n = scan.nextInt();
            goTo(selection, n);
        }
    }

    public void goTo(int selection, int number){
        switch (selection) {
            case 1 -> {
                trainStationFactory.createRandomTrainStations(
                        number, new RandomPlacementStrategy(), 750, 750);
            }
            case 2 -> locomotiveFactory.makeRandomLocomotives(number);
            case 3 -> carsFactory.createRandomCars(number);
            case 4 -> loadFactory.createRandomLoads(number);
            case 5 -> railroadsFactory.createOrderedConnectionsBetweenStations(number);
            default -> System.out.println("default");
        }
    }
}
