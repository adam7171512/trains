package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.logistics.LocoBase;

public class BulkTrainStationCreationMenu2 extends Menu {

    static String name = "Bulk Train Station Creation Menu";
    static String description = "Bulk Train Station Creation Menu";

    public BulkTrainStationCreationMenu2() {
        super(name, description);
    }

    @Override
    public void menuAction() {
        System.out.println("Bulk Train Station Creation Menu");
        System.out.println("Please enter number of Train Stations to create");
        int number = scan.nextInt();
        System.out.println("Please enter City Placement Pattern");
        System.out.println("1. Random");
        System.out.println("2. Rectangular Grid");
        System.out.println("3. Circular");
        System.out.println("4. Polish Cities (2270 stations)");
        int pattern = scan.nextInt();
        IStationPlacementStrategy strategy = switch (pattern) {
            case 1 -> new RandomPlacementStrategy();
            case 2 -> new RectangularNetPlacementStrategy();
            case 3 -> new CircularPlacementStrategy();
            default -> null;
        };
        if (strategy != null) {
            new TrainStationFactory(new LocoBase()).createRandomTrainStations(number, strategy, 800, 800);
        } else {
            try {
            TrainStationFactory.makeTrainStationsOfPolishTowns(LocoBase.getInstance());
            } catch (Exception e) {
                System.out.println("Error while creating Train Stations");
                e.printStackTrace();
            }
        }
        parentMenu.menuAction();
    }
    @Override
    public void createSubMenus() {

    }
}
