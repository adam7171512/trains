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
                ______________________________
                Load creation menu
                Please select load category
                ______________________________\s
                1 Passengers\s
                2 Mail/Luggage\s
                3 Basic Freight\s
                4 Heavy Freight\s
                5 Special Freight""";

        System.out.println(menu);

        int selection;
        do {
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
            System.out.println("Please enter number of passengers");

            int passengers;
            do {
                passengers = resourceContainer.parseToInt(scan.nextLine());
            } while (passengers == -1);
            resourceContainer.getLoadFactory().createPassengerLoad(passengers);
        }
        else {
            System.out.println("Please enter load weight");
            double weight = resourceContainer.parseToDouble(scan.nextLine());
            resourceContainer.getLoadFactory().createLoad(flags, weight);
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
        return "Load creation";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
