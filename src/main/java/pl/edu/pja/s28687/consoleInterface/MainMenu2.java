package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.consoleInterface.freightManagementMenus.FreightManagementMenu;

public class MainMenu2 extends Menu{

    static String name = "Main menu";
    static String description = "Main menu";

    public MainMenu2() {
        super(name, description);
    }

    @Override
    public void createSubMenus() {
        addSubMenu(new ObjectCreationMenu2());
//        addSubMenu(new TrainStationCreationMenu2());
//        addSubMenu(new FreightManagementMenu2());
    }
}
