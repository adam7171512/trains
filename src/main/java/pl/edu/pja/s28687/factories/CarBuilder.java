package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.*;
import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;

public class CarBuilder{

    private CarType flag;
    private Set<Flags> loadTypes;
    private final LocoBase locoBase;
    private IPassengerValidator passengerValidator;
    private IFreightValidator freightValidator;


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

    public CarBuilder setPassengerValidator(IPassengerValidator validator){
        this.passengerValidator = validator;
        return this;
    }

    public CarBuilder setFreightValidator(IFreightValidator validator){
        this.freightValidator = validator;
        return this;
    }

    public RailroadCar build(){
        if (flag == null){
            flag = CarType.BASIC_FREIGHT;
        }
        if (passengerValidator == null){
            passengerValidator = new PassengerValidator();
        }

        if (freightValidator == null){
            freightValidator = new FreightValidator();
        }
        RailroadCar car;
        int id = locoBase.getIdForCar();

        car = switch (flag) {
                case HEAVY_FREIGHT -> new HeavyFreightCar(id, freightValidator);
                case LIQUID -> new LiquidsCar(id, freightValidator);
                case LUGGAGE -> new MailAndLuggageCar(id, freightValidator);
                case GASEOUS -> new GaseousCar(id, freightValidator);
                case TOXIC -> new ToxicCar(id, freightValidator);
                case REFRIGERATED -> new RefrigeratedCar(id, freightValidator);
                case EXPLOSIVE -> new ExplosivesCar(id, freightValidator);
                case PASSENGERS -> new PassengerCar(id, passengerValidator);
                case POST_OFFICE -> new PostOfficeCar(id);
                case RESTAURANT -> new RestaurantCar(id);
                case LIQUID_TOXIC -> new LiquidToxicCar(id, freightValidator);
                default -> new BasicFreightCar(id, freightValidator);
            };
        if (car instanceof LoadableRailroadCar<?> && loadTypes != null) {
            ((LoadableRailroadCar<?>) car).setAllowedFlags(loadTypes);
        }
        locoBase.registerCar(car);
        return car;
    }
}