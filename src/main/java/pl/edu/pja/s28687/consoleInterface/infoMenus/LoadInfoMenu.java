package pl.edu.pja.s28687.consoleInterface.infoMenus;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;
import pl.edu.pja.s28687.info.LoadInfo;
import pl.edu.pja.s28687.load.IDeliverable;

import java.util.Optional;

public class LoadInfoMenu extends AbstractLeafMenu implements IBrowsable<IDeliverable> {
    @Override
    public void menuSpecificAction() {

        String message = "Please enter load ID, ls to list out loads or 0 to exit";
        Optional<IDeliverable> load;
        do {
            load = preProcessInput(scan, message);
            load.ifPresentOrElse(
                    rLoad -> System.out.println(rLoad.getFullInfo()),
                    () -> System.out.println("No load with this ID "));
        } while (load.isPresent());
    }

    @Override
    public String getTitle() {
        return "Load info";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void listElements() {
        String s = LoadInfo.getAggregatedBasicInfo(resourceContainer.getLocoBase());
        if (s.isEmpty()) {
            System.out.println("No loads registered in the system !");
        } else {
            System.out.println(s);
        }
    }

    @Override
    public Optional<IDeliverable> matchElement(String input) {
        int id = resourceContainer.parseToInt(input);
        return resourceContainer.getLocoBase().findLoad(id);
    }
}
