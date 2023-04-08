package pl.edu.pja.s28687.consoleInterface.freightManagementMenus;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.CarAssignmentCenter;
import pl.edu.pja.s28687.factories.LoadAssignmentCenter;

public class AutomaticFreightManagement extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        System.out.println("1. Automatically assign cars to trains");
        System.out.println("2. Automatically assign loads to cars");
        int selection;
        do {
            selection = resourceContainer.parseToInt(scan.nextLine());
        } while (selection < 1 || selection > 2);

        switch (selection) {
            case 1:
                CarAssignmentCenter.assignCars(resourceContainer.getLocoBase());
                break;
            case 2:
                LoadAssignmentCenter.assignLoads(resourceContainer.getLocoBase());
                break;
        }
    }

    @Override
    public String getTitle() {
        return "Automatic freight management";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
