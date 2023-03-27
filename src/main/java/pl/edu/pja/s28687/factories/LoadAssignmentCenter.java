package pl.edu.pja.s28687.Factories;

import pl.edu.pja.s28687.Cars.ILoadCarrier;
import pl.edu.pja.s28687.Cars.LoadableRailroadCar;
import pl.edu.pja.s28687.Cars.RailroadCar;
import pl.edu.pja.s28687.Load.IDeliverable;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;

public class LoadAssignmentCenter {
    public static void assignLoads(LocoBase locoBase) {
        List<LoadableRailroadCar<? extends IDeliverable>> cars = new ArrayList<>(locoBase.getLoadCarriers());

        for (LoadableRailroadCar<? extends IDeliverable> car : cars) {
            while (true) {
                List<Load<? extends IDeliverable>> loads = locoBase.findSuitableLoads(car);
                if (loads.isEmpty()) break;
                car.load((Load<IDeliverable>) loads.get(0));
                loads.get(0).setLoaded();
            }
        }
    }
}

