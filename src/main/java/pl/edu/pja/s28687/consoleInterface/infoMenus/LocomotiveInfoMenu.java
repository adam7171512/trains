package pl.edu.pja.s28687.consoleInterface.infoMenus;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.info.LocInfo;

import java.util.Optional;

public class LocomotiveInfoMenu extends AbstractLeafMenu implements IBrowsable<Locomotive> {
    @Override
    public void menuSpecificAction() {

        String message = "Please enter locomotive ID, ls to list out locomotives or 0 to exit";
        Optional<Locomotive> loc;
        do {
            loc = preProcessInput(scan, message);
            loc.ifPresentOrElse(
                    locomotive -> System.out.println(LocInfo.getFullInfo(locomotive)),
                    () -> System.out.println("No locomotive with this ID "));
        } while (loc.isPresent());
    }

    @Override
    public String getTitle() {
        return "Locomotive info";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void listElements() {
        String s = LocInfo.getAggregatedBasicInfo(resourceContainer.getLocoBase());
        if (s.isEmpty()) {
            System.out.println("No locomotives registered in the system !");
        } else {
            System.out.println(s);
        }
    }

    @Override
    public Optional<Locomotive> matchElement(String input) {
        int id = resourceContainer.parseToInt(input);
        return resourceContainer.getLocoBase().findLoc(id);
    }
}
