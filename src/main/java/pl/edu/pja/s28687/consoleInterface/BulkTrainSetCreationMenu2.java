package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.logistics.LocoBase;

public class BulkTrainSetCreationMenu2 extends Menu {

    static String name = "Bulk Train Set Creation Menu";
    static String description = "Bulk Train Set Creation Menu";

    public BulkTrainSetCreationMenu2() {
        super(name, description);
    }


    @Override
    public void menuAction(){
        System.out.println("Bulk TrainSet Creation");
        System.out.println("Please enter number of TrainSets to create");
        int number = scan.nextInt();
        System.out.println("Please select Type of TrainSets");
        System.out.println("1. Random without Railroad Cars");
        System.out.println("2. Random with Railroad Cars");
        System.out.println("3. Passenger Trains");
        System.out.println("4. Basic Freight trains");
        System.out.println("5. Heavy Freight trains");
        int type = scan.nextInt();

        LocoBase locobase = LocoBase.getInstance();
        TrainSetFactory factory = new TrainSetFactory(locobase, new LocomotiveFactory(locobase), new CarsFactory(locobase));
        switch (type) {
            case 1 -> factory.createRandomTrainSets(number);
            case 2 -> factory.createRandomTrainSetsWithCars(number);
            case 3 -> factory.createTrainSetsOfType(number, LocomotivePurpose.PASSENGER);
            case 4 -> factory.createTrainSetsOfType(number, LocomotivePurpose.BASIC_FREIGHT);
            case 5 -> factory.createTrainSetsOfType(number, LocomotivePurpose.HEAVY_FREIGHT);
        }

        parentMenu.menuAction();
    }
    @Override
    public void createSubMenus() {
    }
}
