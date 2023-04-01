package pl.edu.pja.s28687.consoleInterface;

public class BulkObjectCreationMenu extends Menu{
    static String name = "Bulk Object Creation Menu";
    static String description = "Bulk Object Creation Menu";

    public BulkObjectCreationMenu() {
        super(name, description);
    }

    @Override
    public void createSubMenus() {
        addSubMenu(new BulkTrainStationCreationMenu2());
        addSubMenu(new BulkTrainSetCreationMenu2());
        addSubMenu(new BulkLocomotiveCreationMenu2());
        addSubMenu(new BulkFreightCreationMenu2());
    }
}
