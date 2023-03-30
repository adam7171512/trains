package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;

public class CarBuilder{

    private CarType flag;
    private Set<Flags> loadTypes;
    private final LocoBase locoBase;


    public CarBuilder(LocoBase locoBase){
        this.locoBase = locoBase;
    }


    public CarBuilder setFlag(CarType flag){
        this.flag = flag;
        return this;
    }

    public CarBuilder setRandomProperties(){
        this.flag = CarType.values()[(int) (Math.random() * CarType.values().length)];
        return this;
    }

    public RailroadCar build(){
        if (flag == null){
            flag = CarType.BASIC_FREIGHT;
        }
        RailroadCar car;
        int id = locoBase.getIdForCar();

        car = switch (flag) {
                case HEAVY_FREIGHT -> new HeavyFreightCar(id);
                case LIQUID -> new LiquidsCar(id);
                case LUGGAGE -> new MailAndLuggageCar(id);
                case GASEOUS -> new GaseousCar(id);
                case TOXIC -> new ToxicCar(id);
                case REFRIGERATED -> new RefrigeratedCar(id);
                case EXPLOSIVE -> new ExplosivesCar(id);
                case PASSENGERS -> new PassengerCar(id);
                case POST_OFFICE -> new PostOfficeCar(id);
                case RESTAURANT -> new RestaurantCar(id);
                case LIQUID_TOXIC -> new LiquidToxicCar(id);
                default -> new BasicFreightCar(id);
            };
        if (car instanceof LoadableRailroadCar<?> && loadTypes != null) {
            ((LoadableRailroadCar<?>) car).setAllowedFlags(loadTypes);
        }
        locoBase.registerCar(car);
        return car;
    }
}