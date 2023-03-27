package pl.edu.pja.s28687.ConsoleInterface.ObjectCreationMenus;


import pl.edu.pja.s28687.Cars.*;
import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.Coordinates;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RailroadCarCreationMenu {

    public static RailroadCar createCar(LocoBase locoBase){
        RailroadCar car = make(locoBase);
        locoBase.registerCar(car);
        return car;
    }

    private static RailroadCar make(LocoBase locoBase){
        RailroadCar car;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter Car Name");
        String name = scan.next();
        String typeSelection = """
                _____________________________
                Please select car category :\s
                1 Passenger car\s
                2 Mail/Luggage car\s
                3 Basic Freight car\s
                4 Heavy Freight car\s
                5 Special purpose Freight cars
                _____________________________""";
        System.out.println(typeSelection);
        int typeSelect = scan.nextInt();
        car = switch (typeSelect){
            case 1 -> makePassengersCar(name, locoBase);
            case 2 ->makeLuggageCar(name, locoBase);
            case 3 -> makeBasicFreightCar(locoBase);
            case 4 -> makeHeavyFreightCar(locoBase);
            case 5 -> makeSpecialFreightCar(locoBase);
            default -> make(locoBase);
        };
        return car;
    }

    private static PassengerCar makePassengersCar(String name, LocoBase locoBase){
        return new PassengerCar(locoBase);
    }

    private static MailAndLuggageCar makeLuggageCar(String name, LocoBase locoBase){
        return new MailAndLuggageCar(locoBase);
    }

    private static BasicFreightCar makeBasicFreightCar(LocoBase locoBase){
        return new BasicFreightCar(locoBase);
    }

    private static HeavyFreightCar makeHeavyFreightCar(LocoBase locoBase){
        return new HeavyFreightCar(locoBase);
    }

    private static RailroadCar makeSpecialFreightCar(LocoBase locoBase){
        RailroadCar car;
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

        car = switch (type){
            case 1 -> new LiquidsCar(locoBase);
            case 2 -> new GaseousCar(locoBase);
            case 3 -> new ToxicCar(locoBase);
            case 4 -> new ExplosivesCar(locoBase);
            case 5 -> new RefrigeratedCar(locoBase);
//            case 6 -> new LiquidToxicCar<>();
            case 6 -> new LiquidToxicCar(locoBase);
            default -> makeSpecialFreightCar(locoBase);
        };
        return car;
    }

}
