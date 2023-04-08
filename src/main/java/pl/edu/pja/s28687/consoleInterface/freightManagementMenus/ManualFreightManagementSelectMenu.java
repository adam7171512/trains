package pl.edu.pja.s28687.consoleInterface.freightManagementMenus;

import pl.edu.pja.s28687.consoleInterface.AbstractSelectMenu;

public class ManualFreightManagementSelectMenu extends AbstractSelectMenu {
    @Override
    public String getTitle() {
        return "Manual freight management";
    }

    @Override
    public String getDescription() {
        return "Please select freight to manage";
    }
}
