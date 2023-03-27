package pl.edu.pja.s28687.ConsoleInterface.FreightManagementMenus.RailroadCars;

import pl.edu.pja.s28687.Cars.LoadableRailroadCar;
import pl.edu.pja.s28687.Cars.PassengerCar;
import pl.edu.pja.s28687.Cars.RailroadCar;
import pl.edu.pja.s28687.ConsoleInterface.FreightManagementMenus.Locomotives.LocManagementAvailableCars;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RailroadCarsManagementMenu {

    private static final Scanner scan = new Scanner(System.in);


    private static void printCarList(LocoBase locobase){
        System.out.println("_______________________________");
        for(String s: getCarDescriptions(locobase)) System.out.println(s);
        System.out.println("Enter Railroad Car ID or 0 to go back");
    }

    public static void managementMenu(LocoBase locoBase){

        int selection = 99;

        while (selection != 0){
            printCarList(locoBase);
            selection = scan.nextInt();
            carManagementLoop(selection, locoBase);
        }
    }

    public static List<String> getCarDescriptions(LocoBase locoBase){
        List<RailroadCar> cars = locoBase.getRailroadCarsList();
        List<String> carsDescr = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++ ){
            RailroadCar rC = cars.get(i);
            String carDescr =
                    (i+1) + " ID: " + rC.getId() + " " +
                            rC.getName() + " " +
                            "\nAllowed freight types :" + rC.getAllowable() +
                            "\nWeight limit used  : " + rC.getCurrentWeight() + "/" + rC.grossWeight() + "tonnes";


            if (cars.get(i) instanceof PassengerCar) {
                carDescr += "\nPassenger seats taken : "
                        + ((PassengerCar) cars.get(i)).getNumberOfPassengers()
                        + " / " + ((PassengerCar) cars.get(i)).getNumberOfSeats();
            }
            carDescr += "\n_  _   _   _   _   _  _  _  _  _";
            carsDescr.add(carDescr);
        }
        return carsDescr;
    }

    public static void carManagementLoop(int selection, LocoBase locoBase){
        System.out.println("Press 1 to see loaded loads or 2 to see available loads");
        int actionType = scan.nextInt();
        switch (actionType) {
            case 0 -> System.out.println("lol");
            case 1 -> {
                LoadableRailroadCar<?> car = locoBase.findCar(selection).get();
                RailroadCarsManagementLoadedLoads.managementMenu(car, locoBase);
            }
            default -> {
                LoadableRailroadCar<?> car = locoBase.findCar(selection).get();
                RailroadCarsManagementAvailableLoads.managementMenu(car, locoBase);
            }

        }
    }
}
