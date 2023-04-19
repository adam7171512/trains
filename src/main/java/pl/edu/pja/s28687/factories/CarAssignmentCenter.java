package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.List;

public class CarAssignmentCenter {
    public static void assignCars(LocoBase locoBase) {
        List<Locomotive> locs = locoBase.getLocomotiveList();
        for (Locomotive loc : locs) {
            while (!locoBase.findSuitableCars(loc).isEmpty()) {
                List<IRailroadCar> cars = locoBase.findSuitableCars(loc);
                loc.attach(cars.get(0));
            }
        }
    }

    public static void assignCarsToTrainSet(LocoBase locoBase, TrainSet trainSet, CarType carType, int maxNumber) {

        Locomotive loc = trainSet.getLocomotive();

        List<IRailroadCar> cars = locoBase
                .findSuitableCars(loc)
                .stream()
                .filter(car -> car.getCarType() == carType)
                .limit(maxNumber)
                .toList();
        
        int i = 0;
        while (i < cars.size() && loc.validateCar(cars.get(i))) {
            loc.attach(cars.get(i++));
        }
    }

    public static void assignCarsToTrainSet(LocoBase locoBase, TrainSet trainSet) {
        Locomotive loc = trainSet.getLocomotive();

        List<IRailroadCar> cars = locoBase
                .findSuitableCars(loc)
                .stream()
                .toList();

        int i = 0;
        while (i < cars.size() && loc.validateCar(cars.get(i))) {
            loc.attach(cars.get(i++));
        }
    }

    public static void assignCarsToLocomotive(Locomotive loc, LocoBase locoBase) {
        while (true) {
            List<IRailroadCar> cars = locoBase
                    .findSuitableCars(loc)
                    .stream()
                    .toList();
            if (cars.isEmpty()) return;
            loc.attach(cars.get(0));
        }
    }
}

