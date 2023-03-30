package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {




//        new MainMenu().menu(LocoBase.getInstance());

        LocoBase locoBase = LocoBase.getInstance();

        TrainSet trainSet = new TrainSetBuilder(locoBase).
                setLocomotive(new LocoBuilder(locoBase).
                        setMaxPayload(BigDecimal.valueOf(30000))
                        .build())
                .build();
        new CarsFactory(locoBase).createRandomCars(200);
        new LoadFactory(locoBase).createRandomLoads(200000);

        CarAssignmentCenter.assignCarsToTrainSet(locoBase, trainSet, CarType.HEAVY_FREIGHT, 100);
        LoadAssignmentCenter.assignLoadsToTrainSets(locoBase);


        System.out.println(trainSet);




//        HeavyFreightCar  car = new HeavyFreightCar(1);
//        ToxicLoad toxicLoad = new ToxicLoad(1);
//        HeavyFreightLoad heavyFreightLoad = new HeavyFreightLoad(1);
//        car.load(toxicLoad);
//        car.load(heavyFreightLoad);

//        LoadBuilder lb = new LoadBuilder(10, 200, LocoBase.getInstance());
//        lb.addFlag(Flags.LIQUID);
//        lb.build();
//        lb.build();
//        LoadBuilder lb2 = new LoadBuilder(20, LocoBase.getInstance());
//        lb2.addFlag(Flags.LUGGAGE);
//        lb2.addFlag(Flags.PASSENGERS);
//        lb2.build();
    }
}