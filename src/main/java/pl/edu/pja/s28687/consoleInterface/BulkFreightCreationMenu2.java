package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.factories.LoadFactory;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BulkFreightCreationMenu2 extends Menu {
    static String name = "Bulk Freight Creation Menu";
    static String description = "Bulk Freight Creation Menu";

    public BulkFreightCreationMenu2() {
        super(name, description);
    }

    @Override
    public void createSubMenus() {

    }

    @Override
    public void menuAction() {
        System.out.println("Bulk Freight Creation");
        System.out.println("Please enter number of Freight to create");
        int number = scan.nextInt();
        System.out.println("Please select Type of Freight");
        System.out.println("1. Random");
        System.out.println("2. Passenger");
        System.out.println("3. Basic Freight");
        System.out.println("4. Heavy Freight");
        System.out.println("5. Mail/Luggage");
        System.out.println("6. Special Freight types");

        int selection = scan.nextInt();


        Set<Flags> flags = switch (selection) {
            case 2 -> Set.of(Flags.PASSENGERS);
            case 3 -> Set.of(Flags.BASIC_FREIGHT);
            case 4 -> Set.of(Flags.HEAVY_FREIGHT);
            case 5 -> Set.of(Flags.LUGGAGE);
            case 6 -> makeSpecialFreight();
            default -> Set.of(Flags.BASIC_FREIGHT);
        };

        LocoBase locobase = LocoBase.getInstance();
        LoadFactory factory = new LoadFactory(locobase);
        factory.createRandomLoadsOfType(number, flags);

        parentMenu.menuAction();
    }

    private Set<Flags> makeSpecialFreight() {
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
