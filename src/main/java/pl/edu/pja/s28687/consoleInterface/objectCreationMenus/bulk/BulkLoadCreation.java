package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.load.LoadType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BulkLoadCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        int number;
        do {
            System.out.println("Enter number of loads to create");
            number = resourceContainer.parseToInt(scan.nextLine());
        } while (number == -1);

        String menu = """
                Please select load category\s
                1 Passengers\s
                2 Mail\s
                3 Luggage\s
                4 Basic Freight\s
                5 Heavy Freight\s
                6 Liquid Freight\s
                7 Liquid Toxic Freight\s
                8 Toxic Freight\s
                9 Explosive Freight\s
                10 Refrigerated Freight\s
                11 Gaseous Freight\s
                12 Random\s""";

        int selection;
        do {
            System.out.println(menu);
            selection = resourceContainer.parseToInt(scan.nextLine());
        } while (selection <0 || selection > 12);

        if (selection == 12){
            resourceContainer.getLoadFactory().createRandomLoads(number);
            return;
        }

        LoadType flag = switch (selection){
            case 1 -> LoadType.PASSENGERS;
            case 2 -> LoadType.MAIL;
            case 3 -> LoadType.LUGGAGE;
            case 5 -> LoadType.HEAVY_FREIGHT;
            case 6 -> LoadType.LIQUID;
            case 7 -> LoadType.LIQUID_TOXIC;
            case 8 -> LoadType.TOXIC;
            case 9 -> LoadType.EXPLOSIVE;
            case 10 -> LoadType.REFRIGERATED;
            case 11 -> LoadType.GASEOUS;
            default -> LoadType.BASIC_FREIGHT;
        };
        resourceContainer.getLoadFactory().createRandomLoadsOfType(number, flag);
    }
    @Override
    public String getTitle() {
        return "Bulk load creation";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
