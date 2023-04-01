package pl.edu.pja.s28687.misc;

import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.gui.Canvas;
import pl.edu.pja.s28687.gui.LocoMap;
import pl.edu.pja.s28687.info.Logger;
import pl.edu.pja.s28687.load.Flags;
import pl.edu.pja.s28687.logistics.BadRouteFinder;
import pl.edu.pja.s28687.logistics.LocoBase;
import pl.edu.pja.s28687.logistics.NaiveRouteFinder;
import pl.edu.pja.s28687.logistics.RouteFindingAlgos;

import java.io.IOException;

public class DemoPreparator {

    private LocoBase locoBase;
    private Canvas canvas;
    private Logger log;
    private LocomotiveFactory locomotiveFactory;
    private CarsFactory carsFactory;
    private LoadFactory loadFactory;
    private RailroadsFactory railroadsFactory;
    private TrainStationFactory trainStationFactory;
    private RouteFindingAlgos routeFindingAlgos;
    private TrainSetFactory trainSetFactory;
    private DispatchingCenter dispatchingCenter;


    public DemoPreparator(LocoBase locoBase) {
        this.locoBase = locoBase;
        this.carsFactory = new CarsFactory(locoBase);
        this.locomotiveFactory = new LocomotiveFactory(locoBase);
        this.loadFactory = new LoadFactory(locoBase);
        this.railroadsFactory = new RailroadsFactory(locoBase);
        this.trainStationFactory = new TrainStationFactory(locoBase);
        this.trainSetFactory = new TrainSetFactory(locoBase, locomotiveFactory, carsFactory);
        this.dispatchingCenter = new DispatchingCenter(locoBase);


    }
    public void demoStandard(){
        trainStationFactory.createRandomTrainStations(40, new RectangularNetPlacementStrategy(), 800, 800);
        trainSetFactory.createTrainSetsOfType(5, LocomotivePurpose.PASSENGER, new NaiveRouteFinder(locoBase));
        carsFactory.createRandomCars(20);
        loadFactory.createRandomLoads(2000);
        LoadAssignmentCenter.assignLoads(locoBase);
        CarAssignmentCenter.assignCars(locoBase);
        railroadsFactory.createOrderedConnectionsBetweenStations(3);
        dispatchingCenter.dispatchAllTrainSets();
        Canvas canvas= new Canvas(locoBase);
        canvas.start();

        Logger log= new Logger(locoBase);
        log.start();
    }

    public void demoHard() {
        trainStationFactory.createTrainStationsPolishCoords();
        trainSetFactory.createTrainSetsOfType(5, LocomotivePurpose.PASSENGER, new NaiveRouteFinder(locoBase));
        trainSetFactory.createTrainSetsOfType(5, LocomotivePurpose.BASIC_FREIGHT, new BadRouteFinder(locoBase));
        trainSetFactory.createRandomTrainSets(20);
        carsFactory.createRandomCars(2000);
        loadFactory.createRandomLoads(20000);
        loadFactory.createRandomLoadsOfType(1000, Flags.PASSENGERS);
        CarAssignmentCenter.assignCars(locoBase);

        LoadAssignmentCenter.assignLoadsToTrainSets(locoBase);
        railroadsFactory.createOrderedConnectionsBetweenStations(3);
        dispatchingCenter.dispatchAllTrainSets();
//        LoadAssignmentCenter.assignLoadsToTrainSets(locoBase);
        Canvas canvas= new Canvas(locoBase);
        canvas.start();

        Logger log= new Logger(locoBase);
        log.start();

    }
}
