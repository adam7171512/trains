package pl.edu.pja.s28687.Factories;

import pl.edu.pja.s28687.Cars.LoadableRailroadCar;
import pl.edu.pja.s28687.Cars.RailroadCar;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.List;

public class CarAssignmentCenter {
    public static void assignCars(LocoBase locoBase){
        List<Locomotive> locs = locoBase.getLocomotiveList();
        int k = 0;
        for (Locomotive loc : locs){
            while (! locoBase.findSuitableCars(loc).isEmpty()) {
                    List<RailroadCar> cars = locoBase.findSuitableCars(loc);
                    loc.attach(cars.get(0));
                    System.out.println("assigning car");
                    System.out.println(k++);
                }
        }

    }
}
