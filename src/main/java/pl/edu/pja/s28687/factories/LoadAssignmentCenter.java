package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.LoadableRailroadCar;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.Load;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoadAssignmentCenter {
    public static void assignLoads(LocoBase locoBase) {
        List<ILoadCarrier<? extends IDeliverable>> cars = new ArrayList<>(locoBase.getLoadCarriers());

        for (ILoadCarrier<? extends IDeliverable> car : cars) {
            while (true) {
                List<Load<? extends IDeliverable>> loads = locoBase.findSuitableLoads(car);
                if (loads.isEmpty()) break;
                car.load((Load<IDeliverable>) loads.get(0));
                loads.get(0).setLoaded();
            }
        }
    }

    public static void assignLoadsToTrainSets(LocoBase locoBase){
       List<Load<? extends IDeliverable>> loads = locoBase.getLoadList();
       List<TrainSet> trainSets = locoBase.getTrainSets();
        System.out.println(trainSets);
         for (Load<? extends IDeliverable> load : loads){
              if (! load.isLoaded()) {
                  Optional<TrainSet> tS =
                          trainSets
                                  .stream()
                                  .filter(trainSet -> trainSet.validateLoad(load))
                                  .findFirst();
                  tS.ifPresent(trainSet -> trainSet.load(load));
                  if (tS.isEmpty()) System.out.println("No train set found for load " + load);
              }
         }
        System.out.println(trainSets);
    }
}

