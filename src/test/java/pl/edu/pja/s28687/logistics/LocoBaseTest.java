package pl.edu.pja.s28687.logistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.LocomotivePurpose;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.cars.AbstractRailroadCar;
import pl.edu.pja.s28687.cars.RestaurantCar;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.load.IDeliverable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LocoBaseTest {

    //todo: rewrite everything here

    private LocoBase locoBase;
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
    void getIdForTrainSet_WhenNoTrainSets_ShouldReturn1() {
        assertEquals(1, locoBase.getIdForTrainSet());
    }

    @Test
    void getIdForTrainSet_WhenNoTrainSetsAndCalledTwice_ShouldReturn2() {
        assertEquals(1, locoBase.getIdForTrainSet());
        assertEquals(2, locoBase.getIdForTrainSet());
    }

    @Test
    void getIdForTrainSet_WhenOneTrainSet_ShouldReturn2() {
        TrainSet t = trainSetFactory.createRandomTrainSet();
        assertEquals(2, locoBase.getIdForTrainSet());
    }

    @Test
    void getIdForLocomotive_WhenNoLocomotives_ShouldReturn1() {
        assertEquals(1, locoBase.getIdForLocomotive());
    }

    @Test
    void getIdForLocomotive_WhenNoLocomotivesAndCalledTwice_ShouldReturn2() {
        assertEquals(1, locoBase.getIdForLocomotive());
        assertEquals(2, locoBase.getIdForLocomotive());
    }

    @Test
    void getIdForLocomotive_WhenOneLocomotive_ShouldReturn2() {
        Locomotive l = locomotiveFactory.createRandomLocomotive();
        assertEquals(2, locoBase.getIdForLocomotive());
    }

    @Test
    void getIdForRailroadCar_WhenNoRailroadCars_ShouldReturn1() {
        assertEquals(1, locoBase.getIdForCar());
    }

    @Test
    void getIdForRailroadCar_WhenNoRailroadCarsAndCalledTwice_ShouldReturn2() {
        assertEquals(1, locoBase.getIdForCar());
        assertEquals(2, locoBase.getIdForCar());
    }

    @Test
    void getIdForRailroadCar_WhenOneRailroadCar_ShouldReturn2() {
        IRailroadCar r = carsFactory.createRandomCar();
        assertEquals(2, locoBase.getIdForCar());
    }

    @Test
    void getTrainSets_WhenNoTrainSets_ShouldReturnEmptyList() {
        assertEquals(0, locoBase.getTrainSets().size());
    }

    @Test
    void getTrainSets_WhenOneTrainSet_ShouldReturnListWithOneTrainSet() {
        TrainSet t = trainSetFactory.createRandomTrainSet();
        assertEquals(1, locoBase.getTrainSets().size());
        assertEquals(t, locoBase.getTrainSets().get(0));
    }

    @Test
    void getLocomotiveList_WhenNoLocomotives_ShouldReturnEmptyList() {
        assertEquals(0, locoBase.getLocomotiveList().size());
    }

    @Test
    void getLocomotiveList_WhenOneLocomotive_ShouldReturnListWithOneLocomotive() {
        Locomotive l = locomotiveFactory.createRandomLocomotive();
        assertEquals(1, locoBase.getLocomotiveList().size());
        assertEquals(l, locoBase.getLocomotiveList().get(0));
    }

    @Test
    void getRailroadCarList_WhenNoRailroadCars_ShouldReturnEmptyList() {
        assertEquals(0, locoBase.getRailroadCarsList().size());
    }

    @Test
    void getRailroadCarList_WhenOneRailroadCar_ShouldReturnListWithOneRailroadCar() {
        IRailroadCar r = carsFactory.createRandomCar();
        assertEquals(1, locoBase.getRailroadCarsList().size());
        assertEquals(r, locoBase.getRailroadCarsList().get(0));
    }

    @Test
    void getLoadCarriers_WhenNoCars_ShouldReturnEmptyList() {
        assertEquals(0, locoBase.getLoadCarriers().size());
    }

    @Test
    void getLoadCarriers_WhenOneCarButNotCarrier_ShouldReturnEmptyList() {
        int id = locoBase.getIdForCar();
        RestaurantCar r = new RestaurantCar(id);
        locoBase.registerCar(r);
        assertEquals(1, locoBase.getRailroadCarsList().size());
        assertEquals(0, locoBase.getLoadCarriers().size());
    }

    @Test
    void getLoadCarriers_WhenOneCarrierCarAndOneNotCarrier_ShouldReturnListWithOneCar() {
        AbstractRailroadCar notCarrier = carsFactory.createCarOfType(CarType.RESTAURANT);
        AbstractRailroadCar carrier = carsFactory.createCarOfType(CarType.BASIC_FREIGHT);
        assertEquals(2, locoBase.getRailroadCarsList().size());
        assertEquals(1, locoBase.getLoadCarriers().size());
        assertEquals(carrier, locoBase.getLoadCarriers().get(0));
    }

    @Test
    void getLoadCarriers_WhenOneCarrierCar_ShouldReturnListWithOneCar() {
        AbstractRailroadCar carrier = carsFactory.createCarOfType(CarType.BASIC_FREIGHT);
        assertEquals(1, locoBase.getRailroadCarsList().size());
        assertEquals(1, locoBase.getLoadCarriers().size());
        assertEquals(carrier, locoBase.getLoadCarriers().get(0));
    }

    //todo: its a set, not a list
    @Test
    void getTrainStationList_WhenNoTrainStations_ShouldReturnEmptyList() {
        assertEquals(0, locoBase.getTrainStations().size());
    }

    @Test
    void getTrainStationList_WhenOneTrainStation_ShouldReturnListWithOneTrainStation() {
        Set<TrainStation> trainStationSet = new HashSet<>(trainStationFactory.createRandomTrainStations(1));
        assertEquals(1, locoBase.getTrainStations().size());
        assertEquals(trainStationSet, locoBase.getTrainStations());
    }

    @Test
    void findTrainSet_WhenNoTrainSets_ShouldReturnEmptyOptional() {
        assertFalse(locoBase.findTrainSet(1).isPresent());
    }

    @Test
    void findTrainSet_WhenPresent_ShouldReturnOptionalWithTrainSet() {
        TrainSet t = trainSetFactory.createRandomTrainSet();
        assertEquals(t, locoBase.findTrainSet(t.getId()).get());
    }

    @Test
    void findLoc_WhenNoLocomotives_ShouldReturnEmptyOptional() {
        assertFalse(locoBase.findLoc(1).isPresent());
    }

    @Test
    void findLoc_WhenPresent_ShouldReturnOptionalWithLocomotive() {
        Locomotive l = locomotiveFactory.createRandomLocomotive();
        assertEquals(l, locoBase.findLoc(l.getId()).get());
    }

    @Test
    void findCar_WhenNoRailroadCars_ShouldReturnEmptyOptional() {
        assertFalse(locoBase.findCar(1).isPresent());
    }

    @Test
    void findCar_WhenPresent_ShouldReturnOptionalWithRailroadCar() {
        IRailroadCar r = carsFactory.createRandomCar();
        assertEquals(r, locoBase.findCar(r.getId()).get());
    }

    @Test
    void findLoadCarrier_WhenNoLoadCarriers_ShouldReturnEmptyOptional() {
        assertFalse(locoBase.findLoadCarrier(1).isPresent());
    }

    @Test
    void findLoadCarrier_WhenPresent_ShouldReturnOptionalWithLoadCarrier() {
        AbstractRailroadCar l = carsFactory.createCarOfType(CarType.BASIC_FREIGHT);
        assertTrue(locoBase.findLoadCarrier(l.getId()).isPresent());
        assertEquals(l, locoBase.findLoadCarrier(l.getId()).get());
    }

    @Test
    void findTrainStation_WhenNoTrainStations_ShouldReturnEmptyOptional() {
        assertFalse(locoBase.findTrainStation("test").isPresent());
    }

    @Test
    void findTrainStation_WhenPresent_ShouldReturnOptionalWithTrainStation() {
        TrainStation t = trainStationFactory.createRandomTrainStations(1).get(0);
        final String NAME = t.getName();
        locoBase.registerTrainStation(t);
        assertTrue(locoBase.findTrainStation(NAME).isPresent());
        assertEquals(t, locoBase.findTrainStation(NAME).get());
    }

    @Test
    void findLoad_WhenNoLoads_ShouldReturnEmptyOptional() {
        assertFalse(locoBase.findLoad(1).isPresent());
    }

    @Test
    void findLoad_WhenPresent_ShouldReturnOptionalWithLoad() {
        IDeliverable l = loadFactory.createRandomLoad();
        assertTrue(locoBase.findLoad(l.getId()).isPresent());
        assertEquals(l, locoBase.findLoad(l.getId()).get());
    }

    @Test
    void unRegisterCar_WhenCarIsNotRegistered_ShouldReturnFalse() {
        assertFalse(locoBase.unRegisterCar(1));
    }

    @Test
    void unRegisterCar_WhenCarIsRegistered_ShouldRemoveCar() {
        AbstractRailroadCar car = new CarsFactory(locoBase).createRandomCar();
        assertTrue(locoBase.getRailroadCarsList().contains(car));
        locoBase.unRegisterCar(car.getId());
        assertFalse(locoBase.getRailroadCarsList().contains(car));
    }

    @Test
    void unRegisterLoc_WhenLocIsNotRegistered_ShouldReturnFalse() {
        assertFalse(locoBase.unregisterLoc(1));
    }

    @Test
    void unRegisterLoc_WhenLocIsRegistered_ShouldRemoveLoc() {
        Locomotive loc = new LocomotiveFactory(locoBase).createRandomLocomotive();
        assertTrue(locoBase.getLocomotiveList().contains(loc));
        locoBase.unregisterLoc(loc.getId());
        assertFalse(locoBase.getLocomotiveList().contains(loc));
    }

    @Test
    void registerRailroadConnection_WhenConnectionIsValid_ShouldRegisterConnection() {
        TrainStation station1 = new TrainStation("test1", 0, 0);
        TrainStation station2 = new TrainStation("test2", 1, 1);
        RailroadLink connection = new RailroadLink(station1, station2);
        locoBase.registerRailroadConnection(connection);
        assertTrue(locoBase.getRailroadConnections().contains(connection));
    }

    @Test
    void registerRailroadConnection_WhenConnectionAlreadyExist_ShouldReturnFalse() {
        TrainStation station1 = new TrainStation("test1", 0, 0);
        TrainStation station2 = new TrainStation("test2", 1, 1);
        RailroadLink connection = new RailroadLink(station1, station2);
        locoBase.registerRailroadConnection(connection);
        assertFalse(locoBase.registerRailroadConnection(connection));
    }

    @Test
    void getRailroadConnections_WhenNoConnections_ShouldReturnEmptySet() {
        assertEquals(0, locoBase.getRailroadConnections().size());
    }

    @Test
    void getRailroadConnections_WhenOneConnection_ShouldReturnSetWithOneConnection() {
        TrainStation station1 = new TrainStation("test1", 0, 0);
        TrainStation station2 = new TrainStation("test2", 1, 1);
        RailroadLink connection = new RailroadLink(station1, station2);
        locoBase.registerRailroadConnection(connection);
        assertEquals(1, locoBase.getRailroadConnections().size());
        assertTrue(locoBase.getRailroadConnections().contains(connection));
    }

    //TODO: from here refactor rest of tests
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

        assertNotNull(locoBase.findLink(a, b));
        assertNotNull(locoBase.findLink(b, c));
        assertNotNull(locoBase.findLink(c, a));
        assertNotNull(locoBase.findLink(a, c));

        assertTrue(locoBase.findLink(a, b).getDistance()
                .subtract(BigDecimal.valueOf(141))
                .compareTo(BigDecimal.ONE) < 0);

        assertEquals(locoBase.findLink(b, c).getDistance(), locoBase.findLink(c, b).getDistance());
        assertEquals(locoBase.findLink(a, c).getDistance(), locoBase.findLink(c, a).getDistance());
        assertEquals(locoBase.findLink(a, b).getDistance(), locoBase.findLink(b, c).getDistance());

    }

    @Test
    void testFindSuitableCars() {
        carsFactory.createRandomCars(100);
        Locomotive loco = locomotiveFactory.createLocomotiveOfType(LocomotivePurpose.HEAVY_FREIGHT);
        assertEquals(100, locoBase.findSuitableCars(loco).size());

        AbstractRailroadCar car = carBuilder.
                setRandomProperties()
                .setFlag(CarType.NON_STANDARD)
                .setMaxWeightForNonStandardCar(100000000)
                .setShipperForNonStandardCar("")
                .setSecurityInfoForNonStandardCar("")
                .setNetWeightForNonStandardCar(100000000)
                .setNumberOfSeatsForNonStandardCar(2)
                .setPoweredForNonStandardCar(false)
                .build();

        assertEquals(100, locoBase.findSuitableCars(loco).size());

        AbstractRailroadCar car2 = carBuilder.
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
    void calcDistance_ShouldProperlyCalculateDistance() {
        TrainStation a = trainStationFactory.createTrainStation("A", 0, 0);
        TrainStation b = trainStationFactory.createTrainStation("B", 100, 100);
        TrainStation c = trainStationFactory.createTrainStation("C", 200, 200);
        RailroadsFactory railroadsFactory = new RailroadsFactory(locoBase);
        railroadsFactory.createRailroadLink("A", "B");
        railroadsFactory.createRailroadLink("B", "C");
        railroadsFactory.createRailroadLink("C", "A");
        Coordinates aCoordinates = a.getCoordinates();
        Coordinates bCoordinates = b.getCoordinates();
        Coordinates cCoordinates = c.getCoordinates();

        assertEquals(141.42, Coordinates.getDistance(aCoordinates, bCoordinates), 0.01);
        assertEquals(141.42 * 2, Coordinates.getDistance(aCoordinates, cCoordinates), 0.01);
        assertEquals(141.42, Coordinates.getDistance(bCoordinates, cCoordinates), 0.01);
    }
}