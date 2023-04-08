package pl.edu.pja.s28687.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.factories.*;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RailroadCarTest {

    private LocoBase locoBase;
    private CarsFactory carsFactory;
    private LocomotiveFactory locomotiveFactory;
    private LoadFactory loadFactory;
    private CarBuilder carBuilder;


    @BeforeEach
    void setUp() {
        locoBase = new LocoBase();
        carsFactory = new CarsFactory(locoBase);
        locomotiveFactory = new LocomotiveFactory(locoBase);
        loadFactory = new LoadFactory(locoBase);
        carBuilder = new CarBuilder(locoBase);

    }

    @Test
    void getMaxWeight() {
        RailroadCar car = carBuilder.setRandomProperties().setFlag(CarType.NON_STANDARD)
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.TOXIC)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(100).setNetWeightForNonStandardCar(50).build();
        assertEquals(100, car.getMaxWeight().intValue());

    }

    @Test
    void getNetWeight() {
        RailroadCar car = carBuilder.setRandomProperties().setFlag(CarType.NON_STANDARD)
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.TOXIC)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(100).setNetWeightForNonStandardCar(50).build();
        assertEquals(50, car.getNetWeight().intValue());
    }

    @Test
    void getCurrentWeight() {
        RailroadCar car = carBuilder.setRandomProperties().setFlag(CarType.NON_STANDARD)
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.TOXIC)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(100).setNetWeightForNonStandardCar(50).build();
        assertEquals(50, car.getCurrentWeight().intValue());

        IDeliverable load =
                new LoadBuilder(locoBase)
                        .setRandomProperties()
                        .setWeight(10)
                        .setFlags(Set.of(LoadType.TOXIC))
                        .build();
        ((ILoadCarrier<IDeliverable>) car).load(load);
        assertEquals(60, car.getCurrentWeight().intValue());
        ((ILoadCarrier<IDeliverable>) car).unLoad(load);
        assertEquals(50, car.getCurrentWeight().intValue());
    }

    @Test
    void setAttachedTo() {
        Locomotive locomotive = locomotiveFactory.createRandomLocomotive();
        RailroadCar car = carsFactory.createCarOfType(CarType.PASSENGERS);
        car.setAttachedTo(locomotive);
        assertEquals(locomotive, car.getLocomotive().get());
    }

    @Test
    void getLocomotive() {
        Locomotive locomotive = locomotiveFactory.createRandomLocomotive();
        RailroadCar car = carsFactory.createCarOfType(CarType.PASSENGERS);
        car.setAttachedTo(locomotive);
        assertEquals(locomotive, car.getLocomotive().get());
    }

    @Test
    void setDetached() {
        Locomotive locomotive = locomotiveFactory.createRandomLocomotive();
        RailroadCar car = carsFactory.createCarOfType(CarType.PASSENGERS);
        car.setAttachedTo(locomotive);
        car.setDetached();
        assertFalse(car.getLocomotive().isPresent());
    }

    @Test
    void isAttached() {
        Locomotive locomotive = locomotiveFactory.createRandomLocomotive();
        RailroadCar car = carsFactory.createCarOfType(CarType.PASSENGERS);
        car.setAttachedTo(locomotive);
        assertTrue(car.isAttached());
        car.setDetached();
        assertFalse(car.isAttached());
    }

    @Test
    void getGrossWeight() {
        RailroadCar car = carBuilder.setRandomProperties().setFlag(CarType.NON_STANDARD)
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.TOXIC)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(100).setNetWeightForNonStandardCar(50).build();
        assertEquals(100, car.getGrossWeight().intValue());
    }

    @Test
    void getCarType() {
        RailroadCar car = carBuilder.setRandomProperties().setFlag(CarType.NON_STANDARD)
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.TOXIC)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(100).setNetWeightForNonStandardCar(50).build();
        assertEquals(CarType.NON_STANDARD, car.getCarType());
    }

    @Test
    void isPowered() {
        RailroadCar car = carBuilder.setRandomProperties().setFlag(CarType.NON_STANDARD)
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.TOXIC)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(100).setNetWeightForNonStandardCar(50).build();
        assertTrue(car.isPowered());
    }
}