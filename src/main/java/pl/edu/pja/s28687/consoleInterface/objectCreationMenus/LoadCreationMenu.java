package pl.edu.pja.s28687.consoleInterface.objectCreationMenus;


import pl.edu.pja.s28687.factories.LoadBuilder;
import pl.edu.pja.s28687.load.Explosives;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        Scanner scan = new Scanner(System.in);
        LoadBuilder loadBuilder = new LoadBuilder(locoBase);
        Set<Flags> flags = switch (selection){
            case 1 -> Set.of(Flags.PASSENGERS);
            case 2 -> Set.of(Flags.LUGGAGE);
            case 4 -> Set.of(Flags.HEAVY_FREIGHT);
            case 5 -> makeSpecialFreight();
            default -> Set.of(Flags.BASIC_FREIGHT);
        };

        loadBuilder.setFlags(flags);

        if (flags.contains(Flags.PASSENGERS)){
            System.out.println("Please enter number of passengers");
            int passengers = scan.nextInt();
            loadBuilder.setQuantity(passengers);
        }
        else {
            System.out.println("Please enter load weight");
            double weight = scan.nextDouble();
            loadBuilder.setWeight(weight);
        }

        loadBuilder.build();
    }


    private static Set<Flags> makeSpecialFreight(){
        String menu = ("""
                Please select type of the freight\s
                or multiple types split with space:\s
                1 Liquid\s
                2 Gaseous\s
                3 Toxic\s
                4 Explosive\s
                5 Refrigerated\s""");
        System.out.println(menu);
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        Integer[] numbers = Arrays
                .stream(input.split(" "))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        Set<Flags> flags = new HashSet<>();
        for (Integer num : numbers){
            flags.add( switch (num){
                case 1 -> Flags.LIQUID;
                case 2 -> Flags.GASEOUS;
                case 3 -> Flags.TOXIC;
                case 4 -> Flags.EXPLOSIVE;
                case 5 -> Flags.REFRIGERATED;
                default -> null;
            });
        }
        return flags;
    }

}
