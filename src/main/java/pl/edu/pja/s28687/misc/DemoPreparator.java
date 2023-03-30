package pl.edu.pja.s28687.misc;

import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.gui.Canvas;
import pl.edu.pja.s28687.gui.LocoMap;
import pl.edu.pja.s28687.info.Logger;
import pl.edu.pja.s28687.logistics.LocoBase;
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


    public DemoPreparator(){
        this.locoBase = LocoBase.getInstance();
        this.carsFactory = new CarsFactory(locoBase);
        this.locomotiveFactory = new LocomotiveFactory(locoBase);
        this.loadFactory = new LoadFactory(locoBase);
        this.railroadsFactory = new RailroadsFactory(locoBase);
        this.trainStationFactory = new TrainStationFactory(locoBase);
        this.trainSetFactory = new TrainSetFactory(locoBase);
        this.dispatchingCenter = new DispatchingCenter(locoBase);


    }
    public void demoStandard(LocoBase locoBase){
        trainStationFactory.createRandomTrainStations(40, new RectangularNetPlacementStrategy(), 800, 800);
        locomotiveFactory.makeRandomLocomotives(1);
        carsFactory.createRandomCars(2);
        loadFactory.createRandomLoads(2);
        LoadAssignmentCenter.assignLoads(locoBase);
        CarAssignmentCenter.assignCars(locoBase);
        railroadsFactory.createOrderedConnectionsBetweenStations(3);
        Canvas canvas= new Canvas(locoBase);
        canvas.start();

        //temporary for testing
        LocoMap locoMap = canvas.getLocoMap();
        locoBase.getLocomotiveList().forEach(locomotive -> locomotive.getConductor().setLocoMap(locoMap));



        Logger log= new Logger(locoBase);
        log.start();
    }

    public void demoHard(LocoBase locoBase) {
        trainStationFactory.createTrainStationsPolishCoords();
        trainSetFactory.createRandomTrainSets(10);
        carsFactory.createRandomCars(2);
        loadFactory.createRandomLoads(2);
        LoadAssignmentCenter.assignLoads(locoBase);
        CarAssignmentCenter.assignCars(locoBase);
        railroadsFactory.createOrderedConnectionsBetweenStations(3);
        dispatchingCenter.dispatchAllTrainSets();
        Canvas canvas= new Canvas(locoBase);
        canvas.start();

    }
}
