package pl.edu.pja.s28687.consoleInterface.objectCreationMenus;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.LoadAssignmentCenter;

public class SimpleLocoSystemPreparation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        int stationsNumber;
        do {
            System.out.println("Please enter number of stations (higher than 2)");
            stationsNumber = resourceContainer.parseToInt(scan.nextLine());
        } while (stationsNumber < 2);
        resourceContainer.getTrainStationFactory().createRandomTrainStations(stationsNumber);
        resourceContainer.getRailroadsFactory().createOrderedConnectionsBetweenStations(3);

        int trainSetNumber;
        do {
            System.out.println("Please enter number of train sets (higher than 0)");
            trainSetNumber = resourceContainer.parseToInt(scan.nextLine());
        } while (trainSetNumber < 1);

        resourceContainer.getTrainSetFactory().createRandomTrainSetsWithCars(trainSetNumber);
        resourceContainer.getLoadFactory().createRandomLoads(trainSetNumber * 100);
        //todo: change from static to non-static
        LoadAssignmentCenter.assignLoads(resourceContainer.getLocoBase());
        resourceContainer.getDispatchingCenter().dispatchAllTrainSetsWithCars();
        System.out.println("Train system prepared");
    }

    @Override
    public String getTitle() {
        return "Simple loco system preparation";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
