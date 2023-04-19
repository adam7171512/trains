package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.individual;

import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;
import pl.edu.pja.s28687.load.LoadType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LoadCreation extends AbstractLeafMenu {

    @Override
    public void menuSpecificAction() {
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
        } while (selection < 0 || selection > 12);

        if (selection == 12) {
            resourceContainer.getLoadFactory().createRandomLoad();
            return;
        }

        LoadType flag = switch (selection) {
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
        resourceContainer.getLoadFactory().createRandomLoadOfType(flag);
    }


    private Set<LoadType> makeSpecialFreight() {
        String menu = ("""
                Please select type of the freight\s
                or multiple types split with space:\s
                1 Liquid\s
                2 Gaseous\s
                3 Toxic\s
                4 Explosive\s
                5 Refrigerated\s""");
        System.out.println(menu);
        String input = scan.nextLine();

        try {
            Integer[] numbers = Arrays
                    .stream(input.split(" "))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
            Set<LoadType> flags = new HashSet<>();
            for (Integer num : numbers) {
                flags.add(switch (num) {
                    case 1 -> LoadType.LIQUID;
                    case 2 -> LoadType.GASEOUS;
                    case 3 -> LoadType.TOXIC;
                    case 4 -> LoadType.EXPLOSIVE;
                    case 5 -> LoadType.REFRIGERATED;
                    default -> null;
                });
            }
            return flags;
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
            return makeSpecialFreight();
        }
    }

    @Override
    public String getTitle() {
        return "Load creation";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
