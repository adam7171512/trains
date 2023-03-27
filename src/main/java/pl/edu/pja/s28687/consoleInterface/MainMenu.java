package pl.edu.pja.s28687.ConsoleInterface;

import pl.edu.pja.s28687.ConsoleInterface.FreightManagementMenus.FreightManagementMenu;
import pl.edu.pja.s28687.Gui.Canvas;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Misc.DemoPreparator;

import java.util.Scanner;

public class MainMenu {
    private static final String menuChoices =
                """
                ______________________\s
                Main menu
                Select function
                ______________________\s
                1 Object creation\s
                2 Freight management\s
                3 Route management \s
                4 Info\s
                5 DEMO - SIMPLE\s
                6.DEMO - COMPLEX
                _____________________\s
                """;
    public static void menu(LocoBase locoBase){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuChoices);
            selection = scan.nextInt();
            goTo(selection, locoBase);
        }
    }

    private static void goTo(int selection, LocoBase locoBase) {

        switch (selection) {
            case 1 -> ObjectCreationMenu.menu(locoBase);
            case 5 -> {
                DemoPreparator.demoStandard(locoBase);
            }
            case 6 -> {
                DemoPreparator.demoHard(locoBase);
            }
            case 4 -> {
                InfoMenu.menu(locoBase);
            }
            case 7 -> {
                Canvas canvas= new Canvas(locoBase);
                canvas.start();
            }
            default -> FreightManagementMenu.menu(locoBase);
        }
    }
}




