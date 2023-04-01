package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.LiquidLoadCar;
import pl.edu.pja.s28687.cars.LiquidToxicLoadCar;
import pl.edu.pja.s28687.consoleInterface.MainMenu2;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.validators.CarFreightValidator;

public class Main {
    public static void main(String[] args) {

//    LocoBase locoBase = new LocoBase();
//    RailroadCar car = new CarsFactory(locoBase).createRandomCar();
//
//    Locomotive loco = new LocomotiveFactory(locoBase).makeRandomLocomotive();
//
//    TrainSet trainset = new TrainSetFactory(locoBase, new LocomotiveFactory(locoBase), new CarsFactory(locoBase))
//            .createTrainSetOfType(LocomotivePurpose.PASSENGER);
//
//    System.out.println(TrainSetInfo.getTrainSetInfo(trainset));
        LiquidLoad liquidLoad = new LiquidLoad(2, 2);
        LiquidToxicLoad liquidToxicLoad = new LiquidToxicLoad(2);
        LiquidToxicLoadCar liquidToxicLoadCar = new LiquidToxicLoadCar(2, new CarFreightValidator());


        LiquidLoadCar liquidLoadCar = new LiquidLoadCar(2, new CarFreightValidator());
        liquidLoadCar.load(liquidLoad);
        BasicFreightLoad freight = new BasicFreightLoad(2);
//        liquidLoadCar.load(freight);
//
//        liquidToxicLoadCar.load(liquidToxicLoad);
//        liquidToxicLoadCar.load(liquidLoad);
    new MainMenu2().menuAction();

    }
}