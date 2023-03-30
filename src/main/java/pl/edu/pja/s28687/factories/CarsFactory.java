package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;


import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Random;

public class CarsFactory {
    private LocoBase locoBase;

    public CarsFactory(LocoBase locoBase){
        this.locoBase = locoBase;
    }

    public RailroadCar createCar(CarType carType){
        CarBuilder carBuilder = new CarBuilder(locoBase);
        return carBuilder
                .setFlag(carType)
                .build();
    }

    public RailroadCar createRandomCar(){
        CarBuilder carBuilder = new CarBuilder(locoBase);
        return carBuilder
                .setRandomProperties()
                .build();
    }

    public List<RailroadCar> createRandomCars(int quantity){
        List<RailroadCar> cars = new ArrayList<>();
        for (int i = 0; i < quantity ; i++){
            cars.add(createRandomCar());
        }
        return cars;
    }

    public List<RailroadCar> createCars(CarType carType, int quantity){
        List<RailroadCar> cars = new ArrayList<>();
        for (int i = 0; i < quantity ; i++){
            cars.add(createCar(carType));
        }
        return cars;
    }
}
