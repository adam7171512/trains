package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;

public class BulkMultiCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        String s = "1. Small set : 10 train sets, " +
                "10 Stations (2 connections each)" +
                ", 10 Locos,  40 wagons, 200 loads";

        System.out.println(s);

        int input;
        do {
            input = resourceContainer.parseToInt(scan.nextLine());
        } while (input < 0);
        switch (input) {
            case 1 -> createSmallSet();
            //case 2 -> createMediumSet();
        }
    }

    @Override
    public String getTitle() {
        return "Create multiple objects";
    }

    @Override
    public String getDescription() {
        return null;
    }

    private void createSmallSet(){
        resourceContainer.getTrainSetFactory().createRandomTrainSets(5);
        resourceContainer.getTrainSetFactory().createRandomTrainSetsWithCars(5);
        resourceContainer.getTrainStationFactory().createRandomTrainStations(10);
        resourceContainer.getRailroadsFactory().createOrderedConnectionsBetweenStations(2);
        resourceContainer.getLocomotiveFactory().makeRandomLocomotives(10);
        resourceContainer.getCarsFactory().createRandomCars(40);
        resourceContainer.getLoadFactory().createRandomLoads(200);
    }
}
