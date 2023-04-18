package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.logistics.LocoBase;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CarsFactory {
    private LocoBase locoBase;

    public CarsFactory(LocoBase locoBase){
        this.locoBase = locoBase;
    }

    public AbstractRailroadCar createCarOfType(CarType carType){
        CarBuilder carBuilder = new CarBuilder(locoBase);
        return carBuilder
                .setFlag(carType)
                .build();
    }

    public AbstractRailroadCar createMultiplePurposeSpecialCarForLoadTypes(Set<LoadType> loadTypes){
        CarBuilder carBuilder = new CarBuilder(locoBase);
        return carBuilder
                .setFlag(CarType.NON_STANDARD)
                .setLoadTypes(loadTypes)
                .build();
    }

    public AbstractRailroadCar createRandomCar(){
        CarBuilder carBuilder = new CarBuilder(locoBase);
        return carBuilder
                .setRandomProperties()
                .build();
    }

    public AbstractRailroadCar createRandomNonPoweredCar(){
        CarBuilder carBuilder = new CarBuilder(locoBase);
        List<CarType> nonPoweredCarTypes = new ArrayList<>(List.of(CarType.values()));
        nonPoweredCarTypes.remove(CarType.NON_STANDARD);
        nonPoweredCarTypes.remove(CarType.PASSENGERS);
        nonPoweredCarTypes.remove(CarType.RESTAURANT);
        nonPoweredCarTypes.remove(CarType.POST_OFFICE);
        nonPoweredCarTypes.remove(CarType.REFRIGERATED);
        CarType randomCarType = nonPoweredCarTypes.get((int) (Math.random() * nonPoweredCarTypes.size()));
        return carBuilder
                .setRandomProperties()
                .setFlag(randomCarType)
                .build();
    }



    public List<IRailroadCar> createRandomCars(int quantity){
        List<IRailroadCar> cars = new ArrayList<>();
        for (int i = 0; i < quantity ; i++){
            cars.add(createRandomCar());
        }
        return cars;
    }

    public List<IRailroadCar> createCarsOfType(CarType carType, int quantity){
        List<IRailroadCar> cars = new ArrayList<>();
        for (int i = 0; i < quantity ; i++){
            cars.add(createCarOfType(carType));
        }
        return cars;
    }
}
