package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk;

import pl.edu.pja.s28687.consoleInterface.AbstractSelectMenu;

public class BulkObjectCreationMenu extends AbstractSelectMenu {
    @Override
    public String getTitle() {
        return "Bulk object creation";
    }

    @Override
    public String getDescription() {
        return "Please select objects to create";
    }
}
