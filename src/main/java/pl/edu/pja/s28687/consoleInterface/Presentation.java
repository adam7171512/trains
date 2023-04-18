package pl.edu.pja.s28687.consoleInterface;

import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.gui.Canvas;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.AStarRouteFinder;
import pl.edu.pja.s28687.logistics.ReverseDijkstraRouteFinder;

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
        railroadsFactory.createOrderedConnectionsBetweenStations(3);

        trainSetFactory.createTrainSetsOfType(7, LocomotivePurpose.PASSENGER, new AStarRouteFinder(locoBase));
        trainSetFactory.createTrainSetsOfType(7, LocomotivePurpose.BASIC_FREIGHT, new AStarRouteFinder(locoBase));
        trainSetFactory.createRandomTrainSets(16);
        carsFactory.createRandomCars(400);
        loadFactory.createRandomLoads(2000);
        loadFactory.createRandomLoadsOfType(100, LoadType.PASSENGERS);

        CarAssignmentCenter.assignCars(locoBase);
        LoadAssignmentCenter.assignLoadsToTrainSets(locoBase);

        dispatchingCenter.dispatchAllTrainSets();

        Canvas canvas= new Canvas(locoBase);
        canvas.start();
    }

    @Override
    public String getTitle() {
        return "Just prepare and launch the whole thing..";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
