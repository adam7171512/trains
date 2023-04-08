package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.individual;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;

public class TrainStationCreation extends AbstractLeafMenu {

    @Override
    public void menuSpecificAction() {
        System.out.println("Please enter Train Station City Name");
        String name = scan.nextLine();
        System.out.println("Please enter Train Station x Coordinate");
        int x;
        do {
            x = resourceContainer.parseToInt(scan.nextLine());
        } while (x < 0);

        System.out.println("Please enter Train Station y Coordinate");
        int y;
        do {
            y = resourceContainer.parseToInt(scan.nextLine());
        } while (y < 0);

        resourceContainer.getTrainStationFactory().createTrainStation(name, x, y);
    }

    @Override
    public String getTitle() {
        return "Train Station Creator";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
