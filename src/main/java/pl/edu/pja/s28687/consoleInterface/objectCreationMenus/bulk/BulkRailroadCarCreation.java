package pl.edu.pja.s28687.consoleInterface.objectCreationMenus.bulk;

import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.consoleInterface.AbstractLeafMenu;

public class BulkRailroadCarCreation extends AbstractLeafMenu {
    @Override
    public void menuSpecificAction() {
        System.out.println("Please enter number of cars to create");
        int number;
        do {
            number = resourceContainer.parseToInt(scan.nextLine());
            if (number < 1) {
                System.out.println("Number must be greater than 0");
            }
        } while (number < 1);


        String typeSelection = """
                Please select car category :
                1 Passenger car
                2 Mail/Luggage car
                3 Basic Freight car
                4 Heavy Freight car
                5 Special purpose Freight cars
                6 Restaurant car
                7 Post office car
                8 Random""";
        System.out.println(typeSelection);

        int typeSelect;
        do {
            typeSelect = resourceContainer.parseToInt(scan.nextLine());
            if (typeSelect < 1 || typeSelect > 8) {
                System.out.println("Please select number from 1 to 8");
            }
        } while (typeSelect < 1 || typeSelect > 8);

        if (typeSelect == 8) {
            resourceContainer.getCarsFactory().createRandomCars(number);
            return;
        }

        CarType carType = switch (typeSelect) {
            case 1 -> CarType.PASSENGERS;
            case 2 -> CarType.LUGGAGE;
            case 4 -> CarType.HEAVY_FREIGHT;
            case 5 -> makeSpecialFreightCar();
            case 6 -> CarType.RESTAURANT;
            case 7 -> CarType.POST_OFFICE;
            default -> CarType.BASIC_FREIGHT;
        };
        resourceContainer.getCarsFactory().createCarsOfType(carType, number);
    }

    private CarType makeSpecialFreightCar() {
        String menu = ("""
                Please select type of the freight:\s
                1 Liquid\s
                2 Gaseous\s
                3 Toxic\s
                4 Explosive\s
                5 Refrigerated\s
                6 Liquid Toxic""");
        System.out.println(menu);

        int type;
        do {
            type = resourceContainer.parseToInt(scan.nextLine());
            if (type < 1 || type > 6) {
                System.out.println("Please select number from 1 to 6");
            }
        } while (type < 1 || type > 6);

        return switch (type) {
            case 1 -> CarType.LIQUID;
            case 2 -> CarType.GASEOUS;
            case 3 -> CarType.TOXIC;
            case 4 -> CarType.EXPLOSIVE;
            case 5 -> CarType.REFRIGERATED;
            case 6 -> CarType.LIQUID_TOXIC;
            default -> makeSpecialFreightCar();
        };
    }

    @Override
    public String getTitle() {
        return "Bulk Railroad Car Creation";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
