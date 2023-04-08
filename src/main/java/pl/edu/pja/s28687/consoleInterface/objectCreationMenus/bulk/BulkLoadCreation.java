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
                2 Mail/Luggage\s
                3 Basic Freight\s
                4 Heavy Freight\s
                5 Special Freight""";

        int selection;
        do {
            System.out.println(menu);
            selection = resourceContainer.parseToInt(scan.nextLine());
        } while (selection <0 || selection > 5);

        Set<LoadType> flags = switch (selection){
            case 1 -> Set.of(LoadType.PASSENGERS);
            case 2 -> Set.of(LoadType.LUGGAGE);
            case 4 -> Set.of(LoadType.HEAVY_FREIGHT);
            case 5 -> makeSpecialFreight();
            default -> Set.of(LoadType.BASIC_FREIGHT);
        };

        if (flags.contains(LoadType.PASSENGERS)){
            resourceContainer.getLoadFactory().createRandomLoadsOfType(number, LoadType.PASSENGERS);
        }
        else {
            resourceContainer.getLoadFactory().createRandomLoadsOfType(number, flags);
        }
    }

    private Set<LoadType> makeSpecialFreight(){
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
            for (Integer num : numbers){
                flags.add( switch (num){
                    case 1 -> LoadType.LIQUID;
                    case 2 -> LoadType.GASEOUS;
                    case 3 -> LoadType.TOXIC;
                    case 4 -> LoadType.EXPLOSIVE;
                    case 5 -> LoadType.REFRIGERATED;
                    default -> null;
                });
            }
            return flags;
        } catch (NumberFormatException e){
            System.out.println("Wrong input");
            return makeSpecialFreight();
        }
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
