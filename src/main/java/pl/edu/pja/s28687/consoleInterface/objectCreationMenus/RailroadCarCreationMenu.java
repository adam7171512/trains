package pl.edu.pja.s28687.consoleInterface.objectCreationMenus;


import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.factories.CarBuilder;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Scanner;

public class RailroadCarCreationMenu {

    public static RailroadCar createCar(LocoBase locoBase){
        RailroadCar car = make(locoBase);
        return car;
    }

    private static RailroadCar make(LocoBase locoBase){
        RailroadCar car;
        Scanner scan = new Scanner(System.in);
        String typeSelection = """
                _____________________________
                Please select car category :\s
                1 Passenger car\s
                2 Mail/Luggage car\s
                3 Basic Freight car\s
                4 Heavy Freight car\s
                5 Special purpose Freight cars
                6 Restaurant car
                7 Post office car
                _____________________________""";
        System.out.println(typeSelection);
        int typeSelect = scan.nextInt();
        CarBuilder carBuilder = new CarBuilder(locoBase);
        CarType carType = switch (typeSelect){
            case 1 -> CarType.PASSENGERS;
            case 2 -> CarType.LUGGAGE;
            case 4 -> CarType.HEAVY_FREIGHT;
            case 5 -> makeSpecialFreightCar();
            case 6 -> CarType.RESTAURANT;
            case 7 -> CarType.POST_OFFICE;
            default -> CarType.BASIC_FREIGHT;
        };
        return carBuilder.setFlag(carType).build();
    }

    private static CarType makeSpecialFreightCar(){
        String menu = ("""
                Please select type of the freight:\s
                1 Liquid\s
                2 Gaseous\s
                3 Toxic\s
                4 Explosive\s
                5 Frozen\s
                6 Liquid Toxic""");
        System.out.println(menu);
        Scanner scan = new Scanner(System.in);
        int type = scan.nextInt();

        return switch (type){
            case 1 -> CarType.LIQUID;
            case 2 -> CarType.GASEOUS;
            case 3 -> CarType.TOXIC;
            case 4 -> CarType.EXPLOSIVE;
            case 5 -> CarType.REFRIGERATED;
            case 6 -> CarType.LIQUID_TOXIC;
            default -> makeSpecialFreightCar();
        };
    }

}
