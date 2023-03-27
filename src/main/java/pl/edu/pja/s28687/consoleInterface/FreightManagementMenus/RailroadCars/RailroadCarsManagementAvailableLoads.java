package pl.edu.pja.s28687.ConsoleInterface.FreightManagementMenus.RailroadCars;

import pl.edu.pja.s28687.Cars.LoadableRailroadCar;
import pl.edu.pja.s28687.Cars.PassengerCar;
import pl.edu.pja.s28687.Cars.RailroadCar;
import pl.edu.pja.s28687.Load.IPassengers;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Load.IDeliverable;
import pl.edu.pja.s28687.Load.Passengers;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RailroadCarsManagementAvailableLoads{

        public static void managementMenu(LoadableRailroadCar<?> car, LocoBase locoBase){

            Scanner scan = new Scanner(System.in);
            while (true) {
                System.out.println("____________________________________________________");
                System.out.println("List of validated loads available for your car");
                System.out.println("____________________________________________________");
                printSuitableLoads(car, locoBase);
                System.out.println("Enter Load ID to load up deliverable to the car or 0 to go back");
                int selection = scan.nextInt();
                if (selection == 0) break;
                Load<? extends IDeliverable> load = locoBase.getLoad(selection);
                car.load( (Load<IDeliverable>) load );

                printSuitableLoads(car, locoBase);

            }}


        public static void printSuitableLoads(LoadableRailroadCar<?> railroadCar, LocoBase locoBase){
            for (String s: showAvailableLoads(railroadCar, locoBase)){
                System.out.println(s);
            }
        }


        public static List<String> showAvailableLoads(LoadableRailroadCar<?> railroadCar, LocoBase locoBase){
            List<Load<?>> suitableLoads = locoBase.findSuitableLoads(railroadCar);
            List<String> loadDescriptions = new ArrayList<>();
            for (int i = 0; i < suitableLoads.size(); i++ ){
                Load<? extends IDeliverable> load = suitableLoads.get(i);
                String loadDescr =
                        (i+1) + " ID: " + load.getId() + " " +
                                load.getName() + " " +
                                "\nFreight used  : " + load.getWeight() + " tonnes" +
                                "\nFreight type :" + load.flags();
                if (suitableLoads.get(i) instanceof Passengers) {
                    loadDescr += "\nPassengers number : " + load.getQuantity();
                }

                loadDescr += "\n_  _   _   _   _   _  _  _  _  _";
                loadDescriptions.add(loadDescr);
            }
            return loadDescriptions;
        }
}