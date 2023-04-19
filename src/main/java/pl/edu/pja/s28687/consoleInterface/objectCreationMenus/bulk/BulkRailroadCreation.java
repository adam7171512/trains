package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.factories.GapFiller;
import pl.edu.pja.s28687.factories.RailroadsFactory;

public class BulkRailroadCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        System.out.println("1. Create random connections");
        System.out.println("2. Create connections based on proximity");
        System.out.println("3. Fill Gaps");;

        int type;
        do {
            type = resourceContainer.parseToInt(scan.nextLine());
        } while (type < 1 || type > 3);

        RailroadsFactory railroadsFactory = resourceContainer.getRailroadsFactory();

        switch (type) {
            case 1 -> {
                System.out.println("Please enter total number of connections");
                int number;
                do {
                    number = resourceContainer.parseToInt(scan.nextLine());
                } while (number < 0);
                try {
                    railroadsFactory.createRandomRailroadLinks(number);
                } catch (Exception e) {
                    System.err.println("Error while creating railroad links, likely not enough stations");
                    e.printStackTrace();
                }
            }
            case 2 -> {
                System.out.println("Please enter number of connections per station");
                int number;
                do {
                    number = resourceContainer.parseToInt(scan.nextLine());
                } while (number < 0);
                try {
                    railroadsFactory.createOrderedConnectionsBetweenStations(number);
                } catch (Exception e) {
                    System.err.println("Error while creating railroad links, likely not enough stations");
                    e.printStackTrace();
                }
            }
            case 3 ->{
                GapFiller.fill(resourceContainer.getLocoBase());
            }
        }
    }

    @Override
    public String getTitle() {
        return "Bulk railroad creation";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
