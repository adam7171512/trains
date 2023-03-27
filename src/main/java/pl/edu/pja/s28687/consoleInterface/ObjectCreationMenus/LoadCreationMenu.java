package pl.edu.pja.s28687.ConsoleInterface.ObjectCreationMenus;


import pl.edu.pja.s28687.Load.Explosives;
import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.Scanner;

public class LoadCreationMenu {

    private static final String menuChoices =
            """
                ______________________________
                Load creation menu
                Please select load category
                ______________________________\s
                1 Passengers\s
                2 Mail/Luggage\s
                3 Basic Freight\s
                4 Heavy Freight\s
                5 Special Freight""";

    public static void menu(LocoBase locoBase){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuChoices);
            selection = scan.nextInt();
            createLoad(selection, locoBase);
        }
    }

    public static void createLoad(int selection, LocoBase locoBase){
        Load<? extends IDeliverable> load;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter Load name");
        String name = scan.next();
        load = switch (selection){
            case 1 -> makePassengersLoad(name);
            case 2 -> makeLuggage(name);
            case 3 -> makeBasicFreight(name);
            case 4 -> makeHeavyFreightLoad(name);
            case 5 -> makeSpecialFreight(name);
            default -> null;
        };
        locoBase.registerLoad(load);
    }

    private static Passengers makePassengersLoad(String name){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter number of passengers");
        int numberOfPassengers = scan.nextInt();
        return new Passengers(numberOfPassengers);
    }

    private static Luggage makeLuggage(String name){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter weight of Mail/Luggage load in tonnes");
        double weight =  scan.nextDouble();
        return new Luggage(weight);
    }

    private static BasicFreightLoad makeBasicFreight(String name){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter weight of Basic Freight load in tonnes");
        double weight =  scan.nextDouble();
        return new BasicFreightLoad(weight);
    }

    private static HeavyFreightLoad makeHeavyFreightLoad(String name){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter weight of Heavy Freight load in tonnes");
        double weight =  scan.nextDouble();
        return new HeavyFreightLoad(weight);
    }

    private static Load<? extends IDeliverable> makeSpecialFreight(String name){
        Load<? extends IDeliverable> freight;
        String menu = ("""
                Please select type of the freight :\s
                1 Liquid\s
                2 Gaseous\s
                3 Toxic\s
                4 Explosive\s
                5 Frozen\s
                6 Liquid Toxic""");
        System.out.println(menu);
        Scanner scan = new Scanner(System.in);
        int type = scan.nextInt();

        System.out.println("Please enter weight of Special Freight load in tonnes");
        double weight =  scan.nextDouble();
        freight = switch (type){
            case 1 -> new LiquidLoad(weight);
            case 2 -> new GaseousLoad(weight);
            case 3 -> new ToxicLoad(weight);
            case 4 -> new Explosives(weight);
            case 5 -> new RefrigeratedLoad(weight);
            case 6 -> new LiquidToxicLoad(weight);
            default -> makeSpecialFreight(name);
        };
        return freight;
    }

}
