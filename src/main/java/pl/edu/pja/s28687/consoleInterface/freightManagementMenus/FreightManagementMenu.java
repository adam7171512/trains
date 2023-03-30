package pl.edu.pja.s28687.consoleInterface.freightManagementMenus;

import pl.edu.pja.s28687.consoleInterface.freightManagementMenus.Locomotives.LocomotivesManagementMenu;
import pl.edu.pja.s28687.consoleInterface.freightManagementMenus.RailroadCars.RailroadCarsManagementMenu;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Scanner;

public class FreightManagementMenu {
    private static final String menuChoices =
            """
                ______________________________
                Freight management menu
                Select to see available units
                ______________________________\s
                1 Locomotives\s
                2 Railroad cars\s
                3 Deliverables\s
                0 Back
                _______________________________""";
    public static void menu(LocoBase locoBase){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuChoices);
            selection = scan.nextInt();
            goTo(selection, locoBase);
        }
    }

    public static void goTo(int selection, LocoBase locoBase){
        switch (selection) {
            case 1 -> LocomotivesManagementMenu.managementMenu(locoBase);
            case 2 -> RailroadCarsManagementMenu.managementMenu(locoBase);
            default -> System.out.println("default");
        }
    }
}