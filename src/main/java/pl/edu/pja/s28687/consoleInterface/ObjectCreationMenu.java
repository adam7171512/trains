package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.consoleInterface.objectCreationMenus.*;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Scanner;

public class ObjectCreationMenu {




    private final String menuChoices =
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


    private BulkCreationMenu bulkCreationMenu;
    private LocomotiveCreationMenu locomotiveCreationMenu;

    private CarsFactory carsFactory;
    private LoadFactory loadFactory;
    private RailroadsFactory railroadsFactory;
    private TrainStationFactory trainStationFactory;
    private LocomotiveFactory locomotiveFactory;
    private RailroadCarCreationMenu railroadCarCreationMenu;
    private LoadCreationMenu loadCreationMenu;
    private TrainStationCreationMenu trainStationCreationMenu;
    private TrainConnectionCreationMenu trainConnectionCreationMenu;


    public ObjectCreationMenu(LocomotiveFactory locomotiveFactory
            , CarsFactory carsFactory
            , LoadFactory loadFactory
            , RailroadsFactory railroadsFactory
            , TrainStationFactory trainStationFactory) {
        this.locomotiveFactory = locomotiveFactory;
        this.carsFactory = carsFactory;
        this.loadFactory = loadFactory;
        this.railroadsFactory = railroadsFactory;
        this.trainStationFactory = trainStationFactory;

        bulkCreationMenu = new BulkCreationMenu(
                carsFactory
                , loadFactory
                , railroadsFactory
                , trainStationFactory
                , locomotiveFactory
        );
        locomotiveCreationMenu = new LocomotiveCreationMenu(
                locomotiveFactory
        );

        railroadCarCreationMenu = new RailroadCarCreationMenu(
                carsFactory
        );

        loadCreationMenu = new LoadCreationMenu(
                loadFactory
        );

        trainStationCreationMenu = new TrainStationCreationMenu(
                trainStationFactory
        );

        trainConnectionCreationMenu = new TrainConnectionCreationMenu(
                railroadsFactory
        );

    }
    public void menu(){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuChoices);
            selection = scan.nextInt();
            goTo(selection);
        }
    }

    public void goTo(int selection){
        switch (selection) {
            case 1 -> trainStationCreationMenu.createTrainStation();
            case 2 -> locomotiveCreationMenu.createLocomotive();
            case 3 -> railroadCarCreationMenu.createCar();
            case 4 -> loadCreationMenu.menu();
            case 5 -> trainConnectionCreationMenu.createConnection();
            case 6 -> bulkCreationMenu.menu();
            default -> System.out.println("default");
        }
    }
}
