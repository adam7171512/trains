package pl.edu.pja.s28687;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.consoleInterface.MainMenu;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

    RailroadCar car = new CarsFactory(new LocoBase()).createCar(CarType.PASSENGERS);
    PassengerValidator validator = new PassengerValidator();
    LoadableRailroadCar<?> lc = new PassengerCar(1, validator);
    PassengerCar pc = new PassengerCar(1, validator);
    Mail mailLoad = new Mail(1);





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