package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.train.Locomotive;
import pl.edu.pja.s28687.train.LocomotivePurpose;
import pl.edu.pja.s28687.train.TrainSet;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.logistics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrainSetFactory {

    private LocoBase locoBase;
    private LocomotiveFactory locomotiveFactory;
    private CarsFactory carsFactory;
    private Random random;

    public TrainSetFactory(LocoBase locoBase, LocomotiveFactory locomotiveFactory, CarsFactory carsFactory){
        this.locoBase = locoBase;
        this.locomotiveFactory = locomotiveFactory;
        this.carsFactory = carsFactory;
        this.random = new Random();
    }

    public TrainSet createRandomTrainSet(){
        return new TrainSetBuilder(locoBase).build();
    }

    public TrainSet createTrainSetWithAlgorithm(IRouteFinder algorithm){
        return new TrainSetBuilder(locoBase).setAlgorithm(algorithm).build();
    }

    public List<TrainSet> createRandomTrainSets(int count){
        List<TrainSet> trainSets = new ArrayList<>();
        for (int i = 0; i < count; i++){
            trainSets.add(createRandomTrainSet());
        }
        return trainSets;
    }

    public List<TrainSet> createTrainSetsOfType(int count, LocomotivePurpose type, IRouteFinder algorithm){
        List<TrainSet> trainSets = new ArrayList<>();
        for (int i = 0; i < count; i++){
            trainSets.add(createTrainSetOfType(type, algorithm));
        }
        return trainSets;
    }

    public TrainSet createTrainSetOfType(LocomotivePurpose type, IRouteFinder algorithm){
        Locomotive loco = locomotiveFactory.createLocomotiveOfType(type);
        TrainSet trainSet = new TrainSetBuilder(locoBase)
                .setLocomotive(loco)
                .setAlgorithm(algorithm)
                .build();
        int numberOfCars = random.nextInt(trainSet.getLocomotive().getCarLimit() - 5) + 5;
        List<IRailroadCar> cars = createCarsForTrainSet(trainSet, numberOfCars, type);

        for (IRailroadCar car : cars)
            loco.attach(car);
        return trainSet;
    }

    public TrainSet createTrainSetOfType(LocomotivePurpose type){
        return createTrainSetOfType(type, new AStarRouteFinder(locoBase));
    }

    public List<TrainSet> createTrainSetsOfType(int count, LocomotivePurpose type){
        return createTrainSetsOfType(count, type, new AStarRouteFinder(locoBase));
    }

    private List<IRailroadCar> createCarsForTrainSet(TrainSet trainSet, int maxCount, LocomotivePurpose type){
        List<IRailroadCar> cars = new ArrayList<>();
        int number = Math.min(maxCount, trainSet.getLocomotive().getCarLimit());
        int count = 0;

        switch (type){
            case PASSENGER:
                if (number > 10) {
                    cars.addAll(carsFactory.createCarsOfType(CarType.LUGGAGE, number/10));
                    count += number/10;
                    cars.add(carsFactory.createCarOfType(CarType.RESTAURANT));
                    count++;
                }
                if (number > 20){
                    cars.add(carsFactory.createCarOfType(CarType.POST_OFFICE));
                    count++;
                }
                cars.addAll(carsFactory.createCarsOfType(CarType.PASSENGERS, number - count));
                break;
            case BASIC_FREIGHT:
                cars.addAll(carsFactory.createCarsOfType(CarType.BASIC_FREIGHT, number));
                break;
            case HEAVY_FREIGHT:
                cars.addAll(carsFactory.createCarsOfType(CarType.HEAVY_FREIGHT, number));
                break;
        }
        return cars;
        }

    private List<IRailroadCar> createRandomCarsForTrainSet(TrainSet trainSet, int maxCount){
        List<IRailroadCar> cars = new ArrayList<>();
        int number = Math.min(maxCount, trainSet.getLocomotive().getCarLimit());
        int count = 0;
        while(trainSet.getLocomotive().getPoweredCarsNumber() < (trainSet.getLocomotive().getPoweredCarLimit()) && count < number){
            cars.add(carsFactory.createRandomCar());
            count++;
        }
        while (trainSet.getLocomotive().getCurrentCarNumber() < (trainSet.getLocomotive().getCarLimit()) && count < number){
            cars.add(carsFactory.createRandomNonPoweredCar());
            count++;
        }
        return cars;
    }

    public TrainSet createRandomTrainSetWithCars() {
        TrainSet trainSet = createRandomTrainSet();
        List<IRailroadCar> cars = createRandomCarsForTrainSet(trainSet, 99);
        for (IRailroadCar car : cars){
            if (trainSet.getLocomotive().validateCar(car)){
            trainSet.getLocomotive().attach(car);
            }
        }
        return trainSet;
    }

    public List<TrainSet> createRandomTrainSetsWithCars(int numberOfTrainSets, int numberOfCars){
        List<TrainSet> trainSets = new ArrayList<>();
        for (int i = 0; i < numberOfTrainSets; i++){
            trainSets.add(createRandomTrainSetWithCars(numberOfCars));
        }
        return trainSets;
    }

    public TrainSet createRandomTrainSetWithCars(int numberOfCars) {
        TrainSet trainSet = createRandomTrainSet();
        while (trainSet.getLocomotive().getCurrentCarNumber() < numberOfCars){
            IRailroadCar car = carsFactory.createRandomCar();
            if (trainSet.getLocomotive().validateCar(car)){
                trainSet.getLocomotive().attach(car);
            }
        }
        return trainSet;
    }

    public List<TrainSet> createRandomTrainSetsWithCars(int number){
        List<TrainSet> trainSets = new ArrayList<>();
        for (int i = 0; i < number; i++){
            trainSets.add(createRandomTrainSetWithCars());
        }
        return trainSets;
    }

    public TrainSet createTrainSetFromLocomotive(Locomotive locomotive){
        return new TrainSetBuilder(locoBase).setLocomotive(locomotive).build();
    }

    public List<TrainSet> createTrainSetsFromLocomotives(List<Locomotive> locomotives){
        List<TrainSet> trainSets = new ArrayList<>();
        for (Locomotive loco : locomotives){
            trainSets.add(createTrainSetFromLocomotive(loco));
        }
        return trainSets;
    }
}

