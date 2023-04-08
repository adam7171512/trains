package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.CircularPlacementStrategy;
import pl.edu.pja.s28687.factories.IStationPlacementStrategy;
import pl.edu.pja.s28687.factories.RandomPlacementStrategy;
import pl.edu.pja.s28687.factories.RectangularNetPlacementStrategy;

import java.io.IOException;

public class BulkTrainStationCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        System.out.println("Please enter number of Train Stations to create");

        int number;
        do {
            number = resourceContainer.parseToInt(scan.nextLine());
        } while (number < 0);

        String menu = """
                Please enter City Placement Pattern
                1. Random
                2. Rectangular Grid
                3. Circular
                4. Polish Cities (2270 stations)""";

        System.out.println(menu);

        int pattern;
        do {
            pattern = resourceContainer.parseToInt(scan.nextLine());
        } while (pattern < 1 || pattern > 4);

        IStationPlacementStrategy strategy = switch (pattern) {
            case 1 -> new RandomPlacementStrategy();
            case 2 -> new RectangularNetPlacementStrategy();
            case 3 -> new CircularPlacementStrategy();
            case 4 -> null;
            default -> throw new IllegalStateException("Unexpected value: " + pattern);
        };

        if (strategy == null){
            try {
                resourceContainer
                        .getTrainStationFactory()
                        .makeTrainStationsOfPolishTowns();
            } catch (IOException e) {
                System.err.println("Error while creating Train Stations");
                e.printStackTrace();
            }
        } else {
            resourceContainer
                    .getTrainStationFactory()
                    .createRandomTrainStations
                            (number, strategy, 800, 800);
        }
    }

    @Override
    public String getTitle() {
        return "Bulk train station creation";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
