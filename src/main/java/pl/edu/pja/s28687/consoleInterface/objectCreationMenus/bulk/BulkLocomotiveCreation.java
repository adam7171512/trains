package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk;

import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.LocomotiveFactory;

public class BulkLocomotiveCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        System.out.println("Please enter number of Locomotives to create");
        int number;
        do {
            number = resourceContainer.parseToInt(scan.nextLine());
        } while (number < 0);

        String menu = """
                Please select Type of Locomotives
                1. Random
                2. Passenger train suitable Locomotives
                3. Basic Freight train suitable Locomotives
                4. Heavy Freight train suitable Locomotives""";
        System.out.println(menu);

        int type;
        do {
            type = resourceContainer.parseToInt(scan.nextLine());
        } while (type < 1 || type > 4);

        LocomotiveFactory locomotiveFactory = resourceContainer.getLocomotiveFactory();

        switch (type) {
            case 1 -> locomotiveFactory.makeRandomLocomotives(number);
            case 2 -> locomotiveFactory.createLocomotivesOfType(number, LocomotivePurpose.PASSENGER);
            case 3 -> locomotiveFactory.createLocomotivesOfType(number, LocomotivePurpose.BASIC_FREIGHT);
            case 4 -> locomotiveFactory.createLocomotivesOfType(number, LocomotivePurpose.HEAVY_FREIGHT);
        }
    }

    @Override
    public String getTitle() {
        return "Bulk locomotive creation";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
