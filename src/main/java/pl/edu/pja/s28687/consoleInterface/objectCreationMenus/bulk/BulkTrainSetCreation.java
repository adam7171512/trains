package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk;

import pl.edu.pja.s28687.train.LocomotivePurpose;
import pl.edu.pja.s28687.train.TrainSet;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.TrainSetFactory;

import java.util.List;

public class BulkTrainSetCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        System.out.println("Please enter number of TrainSets to create");
        int number;
        do {
            number = resourceContainer.parseToInt(scan.nextLine());
        } while (number < 0);

        String menu = """
                Please select Type of TrainSets
                1. Random without Railroad Cars
                2. Random with Railroad Cars
                3. Passenger Trains
                4. Basic Freight trains
                5. Heavy Freight trains""";
        System.out.println(menu);

        int type;
        do {
            type = resourceContainer.parseToInt(scan.nextLine());
        } while (type < 1 || type > 5);

        TrainSetFactory trainSetFactory = resourceContainer.getTrainSetFactory();

        List<TrainSet> trainSets = switch (type) {
            case 1 -> trainSetFactory.createRandomTrainSets(number);
            case 2 -> trainSetFactory.createRandomTrainSetsWithCars(number);
            case 3 -> trainSetFactory.createTrainSetsOfType(number, LocomotivePurpose.PASSENGER);
            case 4 -> trainSetFactory.createTrainSetsOfType(number, LocomotivePurpose.BASIC_FREIGHT);
            case 5 -> trainSetFactory.createTrainSetsOfType(number, LocomotivePurpose.HEAVY_FREIGHT);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        System.out.println("Enter 1 to randomly dispatch train sets or any other input to finish" +
                "\nMore dispatching options available in dispatch menu");
        if (scan.nextLine().equals("1")) {
            resourceContainer.getDispatchingCenter().dispatchTrainSets(trainSets);
        }
    }

    @Override
    public String getTitle() {
        return "Bulk train set creation menu";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
