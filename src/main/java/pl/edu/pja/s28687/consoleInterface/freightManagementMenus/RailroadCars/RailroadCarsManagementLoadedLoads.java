package pl.edu.pja.s28687.consoleInterface.freightManagementMenus.RailroadCars;

import pl.edu.pja.s28687.cars.LoadableRailroadCar;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Passengers;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RailroadCarsManagementLoadedLoads{

    public static void managementMenu(LoadableRailroadCar<?> car, LocoBase locoBase){

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("____________________________________________________");
            System.out.println("List of  loads loaded to your your car");
            System.out.println("____________________________________________________");
            printLoadedLoads(car);
            System.out.println("Enter Load ID to deload the delivarable or 0 to go back");
            int selection = scan.nextInt();
            if (selection == 0) break;
            Load<? extends IDeliverable> load = locoBase.getLoad(selection);
            car.deLoad((Load<IDeliverable>) load);

        }}


    public static void printLoadedLoads(LoadableRailroadCar<?> railroadCar){
        for (String s: showLoadedLoads(railroadCar)){
            System.out.println(s);
        }
    }


    public static List<String> showLoadedLoads(LoadableRailroadCar<? extends IDeliverable> railroadCar){
        List<Load<? extends IDeliverable>> loadedLoads = (List<Load<?>>) railroadCar.getLoads();
        List<String> loadDescriptions = new ArrayList<>();
        for (int i = 0; i < loadedLoads.size(); i++ ){
            Load<? extends IDeliverable> load = loadedLoads.get(i);
            String loadDescr =
                    (i+1) + " ID: " + load.getId() + " " +
                            load.getName() + " " +
                            "\nFreight used  : " + load.getWeight() + " tonnes" +
                            "\nFreight type :" + load.flags();
            if (loadedLoads.get(i) instanceof Passengers) {
                loadDescr += "\nPassengers number : " + load.getQuantity();
            }

            loadDescr += "\n_  _   _   _   _   _  _  _  _  _";
            loadDescriptions.add(loadDescr);
        }
        return loadDescriptions;
    }
}