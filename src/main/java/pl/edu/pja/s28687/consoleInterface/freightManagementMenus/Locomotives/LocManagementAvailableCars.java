package pl.edu.pja.s28687.consoleInterface.freightManagementMenus.Locomotives;

import pl.edu.pja.s28687.cars.LoadableRailroadCar;
import pl.edu.pja.s28687.cars.RailroadCar;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LocManagementAvailableCars {

    private static final String menuMessage =
            """
                ______________________________
                List of validated cars available
                for your locomotive
                ______________________________""";

    private static final String menuMessage2 =
            "Enter Railroad Car ID to attach to Locomotive or 0 to go back";

    public static void menu(Locomotive locomotive, LocoBase locoBase){


        Scanner scan = new Scanner(System.in);
        int selection = 99;

        while (selection != 0){
            System.out.println(menuMessage);
            printSuitableCars(locomotive, locoBase);
            selection = scan.nextInt();
            if (selection != 0) {
                Optional<LoadableRailroadCar<?>> car = locoBase.findCar(selection);
                car.ifPresent(locomotive::attach);
            }

        }}


    public static void printSuitableCars(Locomotive locomotive, LocoBase locoBase){
        for (String s: showAvailableCars(locomotive, locoBase)){
            System.out.println(s);
        }
        }


    public static List<String> showAvailableCars(Locomotive locomotive, LocoBase locoBase){
        List<RailroadCar> suitableCars = locoBase.findSuitableCars(locomotive);
        List<String> carsDescr = new ArrayList<>();
        for (int i = 0; i < suitableCars.size(); i++ ){
            RailroadCar rC = suitableCars.get(i);
            String singleDescr =
                    (i+1) + " ID: " + rC.getId() + " " +
                            rC.getName() + " " +
                            rC.getCarType();

            if (rC instanceof LoadableRailroadCar<?>){
                singleDescr +="\n Freight used : " + rC.getCurrentWeight() + "/" + rC.grossWeight() + "tonnes"
                    + "\nAllowed freight types :" + ((LoadableRailroadCar<?>) rC).getAllowedLoadFlags();
            }
            singleDescr += "\n_  _   _   _   _   _  _  _  _  _";
            carsDescr.add(singleDescr);

        }
        return carsDescr;
    }
}
