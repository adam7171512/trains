package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.individual;

import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.consoleInterface.IBrowsable;

import java.util.Optional;

public class RailroadCreation extends AbstractLeafMenu implements IBrowsable<TrainStation> {
    @Override
    public void menuSpecificAction() {

        Optional<TrainStation> station1;
        Optional<TrainStation> station2;


        station1 = preProcessInput(scan, "Please enter first Train Station City " +
                "name or ls to list all existing stations or 0 to exit");
        if (station1.isEmpty()) {
            return;
        }

        station2 = preProcessInput(scan, "Please enter second Train Station City " +
                "name or ls to list all existing stations or 0 to exit");
        if (station2.isEmpty()) {
            return;
        }

        resourceContainer.getRailroadsFactory().createRailroadLink(station1.get(), station2.get());

    }

    @Override
    public String getTitle() {
        return "Railroad creation";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void listElements() {
        resourceContainer.getLocoBase().getTrainStations().forEach(ts -> System.out.println(ts.getName()));
    }

    @Override
    public Optional<TrainStation> matchElement(String input) {
        return resourceContainer.getLocoBase().findTrainStation(input);
    }
}
