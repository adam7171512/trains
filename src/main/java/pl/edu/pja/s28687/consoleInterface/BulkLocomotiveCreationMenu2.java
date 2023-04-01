package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.factories.LocomotiveFactory;
import pl.edu.pja.s28687.logistics.LocoBase;

public class BulkLocomotiveCreationMenu2 extends Menu {
    static String name = "Bulk Locomotive Creation Menu";
    static String description = "Bulk Locomotive Creation Menu";

    public BulkLocomotiveCreationMenu2() {
        super(name, description);
    }

    @Override
    public void createSubMenus() {

    }

    @Override
    public void menuAction() {
        System.out.println("Bulk Locomotive Creation");
        System.out.println("Please enter number of Locomotives to create");
        int number = scan.nextInt();
        System.out.println("Please select Type of Locomotives");
        System.out.println("1. Random");
        System.out.println("2. Passenger train suitable Locomotives");
        System.out.println("3. Basic Freight train suitable Locomotives");
        System.out.println("4. Heavy Freight train suitable Locomotives");
        int type = scan.nextInt();

        LocoBase locobase = LocoBase.getInstance();
        LocomotiveFactory factory = new LocomotiveFactory(locobase);
        switch (type) {
            case 1 -> factory.makeRandomLocomotives(number);
            case 2 -> factory.createLocomotivesOfType(number, LocomotivePurpose.PASSENGER);
            case 3 -> factory.createLocomotivesOfType(number, LocomotivePurpose.BASIC_FREIGHT);
            case 4 -> factory.createLocomotivesOfType(number, LocomotivePurpose.HEAVY_FREIGHT);
        }

        parentMenu.menuAction();
    }
}
