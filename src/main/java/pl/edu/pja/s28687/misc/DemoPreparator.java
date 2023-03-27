package pl.edu.pja.s28687.Misc;

import pl.edu.pja.s28687.Factories.*;
import pl.edu.pja.s28687.Gui.Canvas;
import pl.edu.pja.s28687.Info.Logger;
import pl.edu.pja.s28687.Logistics.LocoBase;
import pl.edu.pja.s28687.Logistics.RouteFindingAlgos;

import java.io.IOException;

public class DemoPreparator {
    public static void demoStandard(LocoBase locoBase){
        TrainStationFactory.makeRandomTrainStationsSquaredNet(100, locoBase);
        LocomotiveFactory.makeRandomLocomotives(20, RouteFindingAlgos.NAIVE, locoBase);
        CarsFactory.makeRandomCars(200, locoBase);
        LoadFactory.makeRandomLoads(800, locoBase);
        LoadAssignmentCenter.assignLoads(locoBase);
        CarAssignmentCenter.assignCars(locoBase);


        RailroadsFactory.makeRandomRailroadsConnectionsOldAlgo(3, locoBase);
        Canvas canvas= new Canvas(locoBase);
        canvas.start();
        Logger log= new Logger(locoBase);
        log.start();
    }

    public static void demoHard(LocoBase locoBase) {
        try {
            TrainStationFactory.makeTrainStationsOfPolishTowns(locoBase);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LocomotiveFactory.makeRandomLocomotives(50, locoBase);
        CarsFactory.makeRandomCars(200, locoBase);
        LoadFactory.makeRandomLoads(2000, locoBase);
        LoadAssignmentCenter.assignLoads(locoBase);
        CarAssignmentCenter.assignCars(locoBase);
        RailroadsFactory.makeRandomRailroadsConnections(4, locoBase);
        Canvas canvas= new Canvas(locoBase);
        canvas.start();
        Logger log= new Logger(locoBase);
        log.start();
    }
}
