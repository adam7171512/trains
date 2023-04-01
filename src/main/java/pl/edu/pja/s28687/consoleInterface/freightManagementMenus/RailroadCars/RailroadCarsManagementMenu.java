package pl.edu.pja.s28687.consoleInterface.freightManagementMenus.RailroadCars;

import pl.edu.pja.s28687.cars.LoadableRailroadCar;
import pl.edu.pja.s28687.cars.PassengerCar;
import pl.edu.pja.s28687.cars.RailroadCar;
import pl.edu.pja.s28687.logistics.LocoBase;

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
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cars.size(); i++ ){
            RailroadCar rC = cars.get(i);
            stringBuilder.append(i + 1)
                    .append(" ID: ")
                    .append(rC.getId())
                    .append("\nCar type :")
                    .append(rC.getCarType())
                    .append("\nWeight limit used  : ")
                    .append(rC.getCurrentWeight())
                    .append("/")
                    .append(rC.getMaxWeight())
                    .append("tonnes");


            if (cars.get(i) instanceof PassengerCar) {
                stringBuilder
                        .append("\nPassengers : ")
                        .append(((PassengerCar) cars.get(i))
                                .getNumberOfPassengers())
                        .append(" / ")
                        .append(((PassengerCar) cars.get(i))
                                .getNumberOfSeats());
            }

            cars.get(i).getLocomotive()
                    .ifPresent(locomotive -> stringBuilder
                            .append("\nAttached to locomotive : ")
                            .append(locomotive.getLocName())
                            .append(" ID: ")
                            .append(locomotive.getId())
                    );
            stringBuilder.append("\n_  _   _   _   _   _  _  _  _  _\n");
            carsDescr.add(stringBuilder.toString());
        }
        return carsDescr;
    }

    public static void carManagementLoop(int selection, LocoBase locoBase){
        System.out.println("Press 1 to see loaded loads or 2 to see available loads");
        int actionType = scan.nextInt();
        switch (actionType) {
            case 0 -> System.out.println("lol");
            case 1 -> {
                LoadableRailroadCar<?> car = locoBase.findLoadCarrier(selection).get();
                RailroadCarsManagementLoadedLoads.managementMenu(car, locoBase);
            }
            default -> {
                LoadableRailroadCar<?> car = locoBase.findLoadCarrier(selection).get();
                RailroadCarsManagementAvailableLoads.managementMenu(car, locoBase);
            }

        }
    }
}
