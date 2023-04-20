package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.gui.LocoMap;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.AStarRouteFinder;

import java.util.List;
import java.util.Random;

public class Presentation extends AbstractLeafMenu{
    @Override
    public void menuSpecificAction() {
        TrainStationFactory trainStationFactory = resourceContainer.getTrainStationFactory();
        TrainSetFactory trainSetFactory = resourceContainer.getTrainSetFactory();
        CarsFactory carsFactory = resourceContainer.getCarsFactory();
        LoadFactory loadFactory = resourceContainer.getLoadFactory();
        RailroadsFactory railroadsFactory = resourceContainer.getRailroadsFactory();
        LocoBase locoBase = resourceContainer.getLocoBase();
        DispatchingCenter dispatchingCenter = resourceContainer.getDispatchingCenter();

        trainStationFactory.createTrainStationsPolishCoords();
        railroadsFactory.createOrderedConnectionsBetweenStations(4);

        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            int cars = random.nextInt(5) + 5;
            trainSetFactory.createRandomTrainSetWithCars(cars);
        }

        carsFactory.createRandomCars(100); // extra 100 cars for future use
        loadFactory.createRandomLoads(2000);

        LoadAssignmentCenter.assignLoadsToTrainSets(locoBase);
        dispatchingCenter.dispatchAllTrainSets();
        LocoMap canvas= LocoMap.getInstance(locoBase);
        canvas.show();
    }

    @Override
    public String getTitle() {
        return "Just prepare and launch the whole thing..";
    }

    @Override
    public String getDescription() {
        return "Creating objects.. please wait..";
    }
}
