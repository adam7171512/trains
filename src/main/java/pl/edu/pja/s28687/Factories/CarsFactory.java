package pl.edu.pja.s28687.Factories;

import pl.edu.pja.s28687.Cars.*;
import pl.edu.pja.s28687.Load.*;
import pl.edu.pja.s28687.Logistics.LocoBase;


import java.util.Random;

public class CarsFactory {

    public static void makeRandomCars(int quantity, LocoBase locoBase){
        Random random = new Random();
        for(int i = 0; i < quantity; i++){
            int r = random.nextInt(13);
            RailroadCar car = switch (r) {
                case 1, 14 -> new HeavyFreightCar(locoBase);
                case 2, 9, 10, 11, 12 -> new PassengerCar(locoBase);
                case 3 -> new LiquidsCar(locoBase);
                case 4 -> new MailAndLuggageCar(locoBase);
                case 5 -> new GaseousCar(locoBase);
                case 6 -> new RefrigeratedCar(locoBase);
                case 7 -> new ToxicCar(locoBase);
                case 8 -> new LiquidToxicCar(locoBase);
                default -> new BasicFreightCar(locoBase);
            };
            locoBase.registerCar(car);
        }
    }
}
