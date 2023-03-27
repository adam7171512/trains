package pl.edu.pja.s28687.ConsoleInterface.FreightManagementMenus.Locomotives;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocomotivesManagementMenu {


    private static void printLocList(LocoBase locoBase){
        System.out.println("_______________________________");
        for(String s: getLocDescriptions(locoBase)) System.out.println(s);
        System.out.println("Enter locomotive ID or 0 to go back");
    }

    public static void managementMenu(LocoBase locoBase){
        printLocList(locoBase);
        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            printLocList(locoBase);
            selection = scan.nextInt();
            locManagementLoop(selection, locoBase);
        }
    }

    public static List<String> getLocDescriptions(LocoBase locoBase){
        List<Locomotive> locs = locoBase.getLocomotiveList();
        List<String> locsdescriptions = new ArrayList<>();
        for (int i = 0; i < locs.size(); i++){
            Locomotive loc = locs.get(i);
            String locDescr =
                    (i+1) + " ID: " + loc.getId() + " " +
                            loc.getLocName() + " " +
                            " cruising between " + loc.getSourceStation() +
                            " and " + loc.getDestStation() +
                    "\nCars occupied:" +
                            "\nregular " + loc.carsOccupied()+"/"+loc.getMaxCars() +
                            "\npowered: " + loc.poweredCarsOccupied() + "/" + loc.getMaxPoweredCars() +
                    "\nFreight used  : " + loc.getCurrentFreight() + "/" + loc.getMaxFreight() + "tonnes" +
                            "\nPassengers on board:" + loc.passengersOnBoard() +
                    "\n_  _   _   _   _   _  _  _  _  _";
            locsdescriptions.add(locDescr);
        }
        return locsdescriptions;
    }

    public static void locManagementLoop(int selection, LocoBase locoBase){
            switch (selection) {
                case 0 -> System.out.println("lol");
                default -> {
                    Locomotive loc = locoBase.findLoc(selection).get();
                    LocManagementAvailableCars.menu(loc, locoBase);
                }

        }
    }
}
