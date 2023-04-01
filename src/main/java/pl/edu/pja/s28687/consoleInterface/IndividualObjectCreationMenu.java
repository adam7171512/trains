package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.consoleInterface.objectCreationMenus.LocomotiveCreationMenu;

public class IndividualObjectCreationMenu extends Menu{

    static String name = "Individual Object Creation Menu";
    static String description = "Individual Object Creation Menu";

    public IndividualObjectCreationMenu() {
        super(name, description);
    }

    @Override
    public void createSubMenus() {
        addSubMenu(new TrainStationCreationMenu2());
//        addSubMenu(new TrainSetCreationMenu2());
//        addSubMenu(new LocomotiveCreationMenu2());
//        addSubMenu(new FreightCreationMenu2());

    }
}
