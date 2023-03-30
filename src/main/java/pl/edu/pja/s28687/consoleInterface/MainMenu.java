package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.consoleInterface.freightManagementMenus.FreightManagementMenu;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.gui.Canvas;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.misc.DemoPreparator;

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

    private ObjectCreationMenu objectCreationMenu;
    private LocoBase locoBase;
    private DemoPreparator demoPreparator;
    public MainMenu(){

        this.locoBase = LocoBase.getInstance();
        this.demoPreparator = new DemoPreparator();
        this.objectCreationMenu = new ObjectCreationMenu(
                new LocomotiveFactory(locoBase)
                , new CarsFactory(locoBase)
                , new LoadFactory(locoBase)
                , new RailroadsFactory(locoBase)
                , new TrainStationFactory(locoBase)
                , locoBase
        );
    }

    public void menu(LocoBase locoBase){
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuChoices);
            selection = scan.nextInt();
            goTo(selection, locoBase);
        }
    }



    private void goTo(int selection, LocoBase locoBase) {

        switch (selection) {
            case 1 -> objectCreationMenu.menu(locoBase);
            case 5 -> {
                demoPreparator.demoStandard(locoBase);
            }
            case 6 -> {
                demoPreparator.demoHard(locoBase);
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




