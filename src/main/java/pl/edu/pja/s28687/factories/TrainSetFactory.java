package pl.edu.pja.s28687.factories;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.RailroadCar;
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
        List<Locomotive> locos = locomotiveFactory.createLocomotivesOfType(count, type);
        for (int i = 0; i < count; i++){
            TrainSet trainSet = new TrainSetBuilder(locoBase)
                    .setLocomotive(locos.get(i))
                    .setAlgorithm(algorithm)
                    .setLocoBase(locoBase)
                    .build();
            int numberOfCars = random.nextInt(trainSet.getLocomotive().getMaxCars() - 5) + 5;
            List<RailroadCar> cars = createCarsForTrainSet(trainSet, numberOfCars, type);

            for (RailroadCar car : cars)
                trainSet.getLocomotive().attach(car);
            trainSets.add(trainSet);
        }
        return trainSets;
    }

    public List<TrainSet> createTrainSetsOfType(int count, LocomotivePurpose type){
        return createTrainSetsOfType(count, type, new NaiveRouteFinder(locoBase));
    }

    private List<RailroadCar> createCarsForTrainSet(TrainSet trainSet, int maxCount, LocomotivePurpose type){
        List<RailroadCar> cars = new ArrayList<>();
        int number = Math.min(maxCount, trainSet.getLocomotive().getMaxCars());
        int count = 0;

        switch (type){
            case PASSENGER:
                if (number > 10) {
                    cars.addAll(carsFactory.createCars(CarType.LUGGAGE, number/10));
                    count += number/10;
                    cars.add(carsFactory.createCar(CarType.RESTAURANT));
                    count++;
                }
                if (number > 20){
                    cars.add(carsFactory.createCar(CarType.POST_OFFICE));
                    count++;
                }
                cars.addAll(carsFactory.createCars(CarType.PASSENGERS, number - count));
                break;
            case BASIC_FREIGHT:
                cars.addAll(carsFactory.createCars(CarType.BASIC_FREIGHT, number));
                break;
            case HEAVY_FREIGHT:
                cars.addAll(carsFactory.createCars(CarType.HEAVY_FREIGHT, number));
                break;
        }
        return cars;
        }
    }

