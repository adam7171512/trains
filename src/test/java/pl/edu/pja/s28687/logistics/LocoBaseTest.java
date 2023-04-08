package pl.edu.pja.s28687.logistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.cars.RailroadCar;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.IDeliverable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocoBaseTest {

    private  LocoBase locoBase;
    private LoadBuilder loadBuilder;
    private TrainSetBuilder trainSetBuilder;
    private LocomotiveBuilder locomotiveBuilder;
    private CarBuilder carBuilder;
    private LocomotiveFactory locomotiveFactory;
    private CarsFactory carsFactory;
    private TrainSetFactory trainSetFactory;
    private TrainStationFactory trainStationFactory;
    private LoadFactory loadFactory;


    @BeforeEach
    void setUp() {
        locoBase = new LocoBase();
        loadBuilder = new LoadBuilder(locoBase);
        trainSetBuilder = new TrainSetBuilder(locoBase);
        locomotiveBuilder = new LocomotiveBuilder(locoBase);
        carBuilder = new CarBuilder(locoBase);
        locomotiveFactory = new LocomotiveFactory(locoBase);
        carsFactory = new CarsFactory(locoBase);
        trainSetFactory = new TrainSetFactory(locoBase, locomotiveFactory, carsFactory);
        trainStationFactory = new TrainStationFactory(locoBase);
        loadFactory = new LoadFactory(locoBase);

    }

    @Test
    void getTrainSets() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        TrainSet t1 =  new TrainSetBuilder(locoBase).setLocomotive(loco).build();
        assertEquals(t1, locoBase.getTrainSets().get(0));
    }

    @Test
    void findTrainSet() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        TrainSet t1 =  new TrainSetBuilder(locoBase).setLocomotive(loco).build();
        assertEquals(t1, locoBase.findTrainSet(t1.getId()).get());
    }

    @Test
    void getLocomotiveList() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        TrainSet t1 =  new TrainSetBuilder(locoBase).setLocomotive(loco).build();
        assertEquals(loco, locoBase.getLocomotiveList().get(0));

        List<Locomotive> locos = new LocomotiveFactory(locoBase).makeRandomLocomotives(10);
        List<Locomotive> locoList = locoBase.getLocomotiveList();
        for (int i = 0; i < locos.size(); i++) {
            assertEquals(locos.get(i), locoList.get(i+1));
        }
    }

    @Test
    void getIdForTrainSet() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        TrainSet t1 =  new TrainSetBuilder(locoBase).setLocomotive(loco).build();
        assertEquals(1, t1.getId());

        Locomotive loco2 = new LocomotiveFactory(locoBase).createRandomLocomotive();
        TrainSet t2 =  new TrainSetBuilder(locoBase).setLocomotive(loco2).build();
        assertEquals(2, t2.getId());
    }

    @Test
    void registerTrainSet() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        TrainSet t1 =  new TrainSetBuilder(locoBase).setLocomotive(loco).build();
        assertEquals(t1, locoBase.getTrainSets().get(0));

        Locomotive loco2 = new LocomotiveFactory(locoBase).createRandomLocomotive();
        TrainSet t2 =  new TrainSetBuilder(locoBase).setLocomotive(loco2).build();
        assertEquals(t2, locoBase.getTrainSets().get(1));
    }

    @Test
    void registerCar() {
        RailroadCar car = new CarsFactory(locoBase).createRandomCar();
        assertEquals(car, locoBase.getRailroadCarsList().get(0));

        List<IRailroadCar> cars = new CarsFactory(locoBase).createRandomCars(10);
        List<IRailroadCar> carList = locoBase.getRailroadCarsList();
        for (int i = 0; i < cars.size(); i++) {
            assertEquals(cars.get(i), carList.get(i+1));
        }
    }

    @Test
    void getIdForCar() {
        RailroadCar car = new CarsFactory(locoBase).createRandomCar();
        assertEquals(1, car.getId());

        List<IRailroadCar> cars = new CarsFactory(locoBase).createRandomCars(10);
        for (int i = 0; i < cars.size(); i++) {
            assertEquals(i+2, cars.get(i).getId());
        }
    }

    @Test
    void unRegisterCar() {
        RailroadCar car = new CarsFactory(locoBase).createRandomCar();
        locoBase.unRegisterCar(car.getId());
        assertFalse(locoBase.getRailroadCarsList().contains(car));

        List<IRailroadCar> cars = new CarsFactory(locoBase).createRandomCars(10);
        for (IRailroadCar c : cars) {
            locoBase.unRegisterCar(c.getId());
        }
        assertTrue(locoBase.getRailroadCarsList().isEmpty());
    }

    @Test
    void getRailroadCarsList() {
        RailroadCar car = new CarsFactory(locoBase).createRandomCar();
        assertEquals(car, locoBase.getRailroadCarsList().get(0));

        List<IRailroadCar> cars = new CarsFactory(locoBase).createRandomCars(10);
        List<IRailroadCar> carList = locoBase.getRailroadCarsList();
        for (int i = 0; i < cars.size(); i++) {
            assertEquals(cars.get(i), carList.get(i+1));
        }
    }

    @Test
    void getIdForLocomotive() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        assertEquals(1, loco.getId());

        List<Locomotive> locos = new LocomotiveFactory(locoBase).makeRandomLocomotives(10);
        for (int i = 0; i < locos.size(); i++) {
            assertEquals(i+2, locos.get(i).getId());
        }
    }

    @Test
    void registerLocomotive() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        assertEquals(loco, locoBase.getLocomotiveList().get(0));

        List<Locomotive> locos = new LocomotiveFactory(locoBase).makeRandomLocomotives(10);
        List<Locomotive> locoList = locoBase.getLocomotiveList();
        for (int i = 0; i < locos.size(); i++) {
            assertEquals(locos.get(i), locoList.get(i+1));
        }
    }

    @Test
    void unregisterLoc() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        locoBase.unregisterLoc(loco.getId());
        assertFalse(locoBase.getLocomotiveList().contains(loco));

        List<Locomotive> locos = new LocomotiveFactory(locoBase).makeRandomLocomotives(10);
        for (Locomotive l : locos) {
            locoBase.unregisterLoc(l.getId());
        }
        assertTrue(locoBase.getLocomotiveList().isEmpty());
    }


    @Test
    void findLoc() {
        Locomotive loco = new LocomotiveFactory(locoBase).createRandomLocomotive();
        assertEquals(loco, locoBase.findLoc(loco.getId()).get());

        List<Locomotive> locos = new LocomotiveFactory(locoBase).makeRandomLocomotives(10);
        for (int i = 0; i < locos.size(); i++) {
            assertEquals(locos.get(i), locoBase.findLoc(i+2).get());
        }
    }

    @Test
    void findCar() {
        RailroadCar car = new CarsFactory(locoBase).createRandomCar();
        assertEquals(car, locoBase.findCar(car.getId()).get());

        List<IRailroadCar> cars = new CarsFactory(locoBase).createRandomCars(10);
        for (int i = 0; i < cars.size(); i++) {
            assertEquals(cars.get(i), locoBase.findCar(i+2).get());
        }
    }

    @Test
    void findLoadCarrier() {
        RailroadCar loadCarrier = new CarsFactory(locoBase).createCarOfType(CarType.BASIC_FREIGHT);
        assertEquals(loadCarrier, locoBase.findLoadCarrier(loadCarrier.getId()).get());

        List<IRailroadCar> loadCarriers = new CarsFactory(locoBase).createCarsOfType(CarType.HEAVY_FREIGHT, 10);
        for (int i = 0; i < loadCarriers.size(); i++) {
            assertEquals(loadCarriers.get(i), locoBase.findLoadCarrier(i+2).get());
        }
    }

    @Test
    void addTrainStation() {
        List<TrainStation> stations = new TrainStationFactory(locoBase)
                .createRandomTrainStations(7, new CircularPlacementStrategy(), 700, 700);
        for (TrainStation station : stations) {
            assertEquals(station, locoBase.findTrainStation(station.getName()).get());
        }
    }

    @Test
    void getTrainStations() {
        List<TrainStation> stations = new TrainStationFactory(locoBase)
                .createRandomTrainStations(10, new CircularPlacementStrategy(), 700, 700);
        for (TrainStation station : stations) {
            assertTrue(locoBase.getTrainStations().contains(station));
        }
    }

    //TODO: fix this test
//    @Test
    void getRailroadConnections() {
        List<TrainStation> stations = new TrainStationFactory(locoBase)
                .createRandomTrainStations(10, new CircularPlacementStrategy(), 700, 700);
        new RailroadsFactory(locoBase).createOrderedConnectionsBetweenStations(3, false);
        for (TrainStation station : stations) {
            System.out.println(station.getNeighbors());
            assertEquals(3, station.getTrainDirectConnectionList().size());
            assertEquals(3, station.getNeighbors().size());
        }



    }

    //TODO: fix this test
//    @Test
    void registerRailroadConnection() {
        List<TrainStation> stations = new TrainStationFactory(locoBase)
                .createRandomTrainStations(100, new CircularPlacementStrategy(), 700, 700);
        new RailroadsFactory(locoBase).createOrderedConnectionsBetweenStations(3, false);
        for (TrainStation station : stations) {
            assertEquals(3, station.getTrainDirectConnectionList().size());
            assertEquals(3, station.getNeighbors().size());
        }
    }

    @Test
    void findTrainStation() {
        List<TrainStation> stations = new TrainStationFactory(locoBase)
                .createRandomTrainStations(10, new CircularPlacementStrategy(), 700, 700);
        for (TrainStation station : stations) {
            assertEquals(station, locoBase.findTrainStation(station.getName()).get());
        }
    }

    @Test
    void getIdForLoad() {
        IDeliverable load = new LoadFactory(locoBase).createRandomLoad();
        assertEquals(1, load.getId());

        List<IDeliverable> loads = new LoadFactory(locoBase).createRandomLoads(10);
        for (int i = 0; i < loads.size(); i++) {
            assertEquals(i+2, loads.get(i).getId());
        }
    }

    @Test
    void registerLoad() {
        IDeliverable load = new LoadFactory(locoBase).createRandomLoad();
        assertEquals(load, locoBase.getLoadList().get(0));

        List<IDeliverable> loads = new LoadFactory(locoBase).createRandomLoads(1000);
        List<IDeliverable> loadList = (List<IDeliverable>) locoBase.getLoadList();
        assertEquals(1001, loadList.size());
        for (int i = 0; i < loads.size(); i++) {
            assertEquals(loads.get(i), loadList.get(i+1));
        }
    }

    @Test
    void getLoad() {
        IDeliverable load = new LoadFactory(locoBase).createRandomLoad();
        assertEquals(load, locoBase.getLoadList().get(0));

        List<IDeliverable> loads = new LoadFactory(locoBase).createRandomLoads(10);
        List<IDeliverable> loadList = (List<IDeliverable>) locoBase.getLoadList();
        for (int i = 0; i < loads.size(); i++) {
            assertEquals(loads.get(i), loadList.get(i+1));
        }
    }

    @Test
    void getLoadCarriers() {
        List<IRailroadCar> loadCarriers = new CarsFactory(locoBase).createCarsOfType(CarType.HEAVY_FREIGHT, 10);
        List<IRailroadCar> nonLoadCarriers = new CarsFactory(locoBase).createCarsOfType(CarType.RESTAURANT, 10);
        for (IRailroadCar loadCarrier : loadCarriers) {
            assertTrue(locoBase.getLoadCarriers().contains(loadCarrier));
        }
        for (IRailroadCar nonLoadCarrier : nonLoadCarriers) {
            assertFalse(locoBase.getLoadCarriers().contains(nonLoadCarrier));
        }
    }

    @Test
    void getLoadList() {
        List<IDeliverable> loads = new LoadFactory(locoBase).createRandomLoads(100);
        List<IDeliverable> loadList = (List<IDeliverable>) locoBase.getLoadList();

        assertEquals(loads.size(), loadList.size());
        assertEquals(loads.get(0), loadList.get(0));
        assertEquals(loads.get(loads.size()-1), loadList.get(loadList.size()-1));

        for (int i = 0; i < loads.size(); i++) {
            assertEquals(loads.get(i), loadList.get(i));
        }
    }

    @Test
    void findSuitableCars() {

        new CarsFactory(locoBase).createCarsOfType(CarType.PASSENGERS, 10);
        new CarsFactory(locoBase).createCarsOfType(CarType.HEAVY_FREIGHT, 30);
        new CarsFactory(locoBase).createCarsOfType(CarType.RESTAURANT, 20);
        new CarsFactory(locoBase).createCarsOfType(CarType.BASIC_FREIGHT, 12);
        new CarsFactory(locoBase).createCarsOfType(CarType.RESTAURANT, 7);
        new CarsFactory(locoBase).createCarsOfType(CarType.REFRIGERATED, 14);
        new CarsFactory(locoBase).createCarsOfType(CarType.EXPLOSIVE, 19);
        new CarsFactory(locoBase).createCarsOfType(CarType.TOXIC, 119);
        new CarsFactory(locoBase).createCarsOfType(CarType.GASEOUS, 9);
        new CarsFactory(locoBase).createCarsOfType(CarType.LIQUID, 11);
        new CarsFactory(locoBase).createCarsOfType(CarType.POST_OFFICE, 13);

        IDeliverable passengers = new LoadFactory(locoBase).createLoad(LoadType.PASSENGERS);
        assertEquals(10, locoBase.findSuitableCars(passengers).size());

        IDeliverable basicFreight = new LoadFactory(locoBase).createLoad(LoadType.BASIC_FREIGHT);
        assertEquals(12, locoBase.findSuitableCars(basicFreight).size());

        IDeliverable heavyFreight = new LoadFactory(locoBase).createLoad(LoadType.HEAVY_FREIGHT);
        assertEquals(30, locoBase.findSuitableCars(heavyFreight).size());

        IDeliverable liquidLoad = new LoadFactory(locoBase).createLoad(LoadType.LIQUID);
        assertEquals(11, locoBase.findSuitableCars(liquidLoad).size());

        IDeliverable gaseousLoad = new LoadFactory(locoBase).createLoad(LoadType.GASEOUS);
        assertEquals(9, locoBase.findSuitableCars(gaseousLoad).size());

        IDeliverable toxicLoad = new LoadFactory(locoBase).createLoad(LoadType.TOXIC);
        assertEquals(119, locoBase.findSuitableCars(toxicLoad).size());

        IDeliverable explosiveLoad = new LoadFactory(locoBase).createLoad(LoadType.EXPLOSIVE);
        assertEquals(19, locoBase.findSuitableCars(explosiveLoad).size());

        IDeliverable refrigeratedLoad = new LoadFactory(locoBase).createLoad(LoadType.REFRIGERATED);
        assertEquals(14, locoBase.findSuitableCars(refrigeratedLoad).size());

        new CarsFactory(locoBase).createCarsOfType(CarType.LIQUID_TOXIC, 99);
        IDeliverable liquidToxicLoad =
                new LoadBuilder(locoBase).addFlag(LoadType.LIQUID).addFlag(LoadType.TOXIC).build();

        assertEquals(99, locoBase.findSuitableCars(liquidToxicLoad).size());
        assertEquals(99 + 11, locoBase.findSuitableCars(liquidLoad).size());
        assertEquals(99 + 119, locoBase.findSuitableCars(toxicLoad).size());


    }

    @Test
    void findSuitableLoads() {
        RailroadCar pass = carsFactory.createCarOfType(CarType.PASSENGERS);
        RailroadCar basic = carsFactory.createCarOfType(CarType.BASIC_FREIGHT);
        RailroadCar heavy = carsFactory.createCarOfType(CarType.HEAVY_FREIGHT);
        RailroadCar liquid = carsFactory.createCarOfType(CarType.LIQUID);
        RailroadCar gaseous = carsFactory.createCarOfType(CarType.GASEOUS);
        RailroadCar toxic = carsFactory.createCarOfType(CarType.TOXIC);
        RailroadCar explosive = carsFactory.createCarOfType(CarType.EXPLOSIVE);
        RailroadCar refrigerated = carsFactory.createCarOfType(CarType.REFRIGERATED);
        RailroadCar liquidToxic = carsFactory.createCarOfType(CarType.LIQUID_TOXIC);


        IDeliverable passengerLoad = loadFactory.createPassengerLoad(20);
        IDeliverable basicLoad = loadFactory.createLoad(LoadType.BASIC_FREIGHT);
        IDeliverable heavyLoad = loadFactory.createLoad(LoadType.HEAVY_FREIGHT);

        assertEquals(passengerLoad, locoBase.findSuitableLoads(locoBase.findLoadCarrier(pass.getId()).get()).get(0));
        assertEquals(basicLoad, locoBase.findSuitableLoads(locoBase.findLoadCarrier(basic.getId()).get()).get(0));
        assertEquals(heavyLoad, locoBase.findSuitableLoads(locoBase.findLoadCarrier(heavy.getId()).get()).get(0));

        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(liquid.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(gaseous.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(toxic.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(explosive.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(refrigerated.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(liquidToxic.getId()).get()).size());

        IDeliverable liquidLoad = loadFactory.createLoad(LoadType.LIQUID);
        IDeliverable gaseousLoad = loadFactory.createLoad(LoadType.GASEOUS);
        IDeliverable toxicLoad = loadFactory.createLoad(LoadType.TOXIC);
        IDeliverable explosiveLoad = loadFactory.createLoad(LoadType.EXPLOSIVE);
        IDeliverable refrigeratedLoad = loadFactory.createLoad(LoadType.REFRIGERATED);
        IDeliverable liquidToxicLoad = loadBuilder.addFlag(LoadType.LIQUID).addFlag(LoadType.TOXIC).build();

        assertEquals(liquidLoad, locoBase.findSuitableLoads(locoBase.findLoadCarrier(liquid.getId()).get()).get(0));
        assertEquals(gaseousLoad, locoBase.findSuitableLoads(locoBase.findLoadCarrier(gaseous.getId()).get()).get(0));
        assertEquals(toxicLoad, locoBase.findSuitableLoads(locoBase.findLoadCarrier(toxic.getId()).get()).get(0));
        assertEquals(explosiveLoad, locoBase.findSuitableLoads(locoBase.findLoadCarrier(explosive.getId()).get()).get(0));
        assertEquals(refrigeratedLoad, locoBase.findSuitableLoads(locoBase.findLoadCarrier(refrigerated.getId()).get()).get(0));

        assertEquals(3, locoBase.findSuitableLoads(locoBase.findLoadCarrier(liquidToxic.getId()).get()).size());


        loadFactory.createRandomLoadsOfType(100, LoadType.LIQUID);
        loadFactory.createRandomLoadsOfType(100, LoadType.TOXIC);
        loadFactory.createRandomLoadsOfType(100, LoadType.GASEOUS);
        loadFactory.createRandomLoadsOfType(100, LoadType.EXPLOSIVE);
        loadFactory.createRandomLoadsOfType(100, LoadType.REFRIGERATED);

        assertEquals(101, locoBase.findSuitableLoads(locoBase.findLoadCarrier(liquid.getId()).get()).size());
        assertEquals(101, locoBase.findSuitableLoads(locoBase.findLoadCarrier(toxic.getId()).get()).size());
        assertEquals(101, locoBase.findSuitableLoads(locoBase.findLoadCarrier(gaseous.getId()).get()).size());
        assertEquals(101, locoBase.findSuitableLoads(locoBase.findLoadCarrier(explosive.getId()).get()).size());
        assertEquals(101, locoBase.findSuitableLoads(locoBase.findLoadCarrier(refrigerated.getId()).get()).size());
        assertEquals(203, locoBase.findSuitableLoads(locoBase.findLoadCarrier(liquidToxic.getId()).get()).size());


        LoadAssignmentCenter.assignLoads(locoBase);
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(liquid.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(toxic.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(gaseous.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(explosive.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(refrigerated.getId()).get()).size());
        assertEquals(0, locoBase.findSuitableLoads(locoBase.findLoadCarrier(liquidToxic.getId()).get()).size());






    }
    //TODO: later fix this test ( finds the link between station a and a)
    @Test
    void getLink() {
        TrainStation a = trainStationFactory.createTrainStation("A", 0, 0);
        TrainStation b = trainStationFactory.createTrainStation("B", 100, 100);
        TrainStation c = trainStationFactory.createTrainStation("C", 200, 200);
        RailroadsFactory railroadsFactory = new RailroadsFactory(locoBase);
        railroadsFactory.createRailroadLink("A", "B");
        railroadsFactory.createRailroadLink("B", "C");
        railroadsFactory.createRailroadLink("C", "A");

        assertNotNull(locoBase.getLink(a, b));
        assertNotNull(locoBase.getLink(b, c));
        assertNotNull(locoBase.getLink(c, a));
        assertNotNull(locoBase.getLink(a, c));

        assertTrue(locoBase.getLink(a, b).getDistance()
                .subtract(BigDecimal.valueOf(141))
                .compareTo(BigDecimal.ONE) < 0);

        assertEquals(locoBase.getLink(b, c).getDistance(), locoBase.getLink(c, b).getDistance());
        assertEquals(locoBase.getLink(a, c).getDistance(), locoBase.getLink(c, a).getDistance());
        assertEquals(locoBase.getLink(a, b).getDistance(), locoBase.getLink(b, c).getDistance());

    }

    @Test
    void testFindSuitableCars() {
        carsFactory.createRandomCars(100);
        Locomotive loco = locomotiveFactory.createLocomotiveOfType(LocomotivePurpose.HEAVY_FREIGHT);
        assertEquals(100, locoBase.findSuitableCars(loco).size());

        RailroadCar car = carBuilder.
                setRandomProperties()
                .setFlag(CarType.NON_STANDARD)
                .setMaxWeightForNonStandardCar(100000000)
                .setShipperForNonStandardCar("")
                .setSecurityInfoForNonStandardCar("")
                .setNetWeightForNonStandardCar(100000000)
                .setNumberOfSeatsForNonStandardCar(2)
                .setPoweredForNonStandardCar(false)
                .build();

        System.out.println(car.getGrossWeight());
        System.out.println(car.getNetWeight());
        assertEquals(100, locoBase.findSuitableCars(loco).size());

        RailroadCar car2 = carBuilder.
                setRandomProperties()
                .setFlag(CarType.NON_STANDARD)
                .setMaxWeightForNonStandardCar(50)
                .setShipperForNonStandardCar("")
                .setSecurityInfoForNonStandardCar("")
                .setNetWeightForNonStandardCar(50)
                .setNumberOfSeatsForNonStandardCar(2)
                .setPoweredForNonStandardCar(false)
                .build();

        assertEquals(101, locoBase.findSuitableCars(loco).size());
    }

    @Test
    void calcDistance() {
        TrainStation a = trainStationFactory.createTrainStation("A", 0, 0);
        TrainStation b = trainStationFactory.createTrainStation("B", 100, 100);
        TrainStation c = trainStationFactory.createTrainStation("C", 200, 200);
        RailroadsFactory railroadsFactory = new RailroadsFactory(locoBase);
        railroadsFactory.createRailroadLink("A", "B");
        railroadsFactory.createRailroadLink("B", "C");
        railroadsFactory.createRailroadLink("C", "A");

        assertEquals(BigDecimal.valueOf(141), locoBase.calcDistance(a, b).setScale(0, RoundingMode.FLOOR));
        assertEquals(BigDecimal.valueOf(282), locoBase.calcDistance(a, c).setScale(0, RoundingMode.FLOOR));
        assertEquals(BigDecimal.valueOf(141), locoBase.calcDistance(b, c).setScale(0, RoundingMode.FLOOR));
    }
}