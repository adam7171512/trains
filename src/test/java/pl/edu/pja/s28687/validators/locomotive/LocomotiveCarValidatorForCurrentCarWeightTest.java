package pl.edu.pja.s28687.validators.locomotive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.factories.CarBuilder;
import pl.edu.pja.s28687.factories.LocomotiveBuilder;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.IToxic;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.ToxicLoad;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LocomotiveCarValidatorForCurrentCarWeightTest {
    private LocoBase locoBase;
    private ILocomotiveCarValidator validator;
    private LocomotiveBuilder locomotiveBuilder;
    private final ILocomotiveLoadValidator loadValidator = new LocomotiveLoadValidator();
    private CarBuilder carBuilder;

    @BeforeEach
    void setUp() {
        locoBase = new LocoBase();
        validator = new LocomotiveCarValidatorForCurrentCarWeight();
        locomotiveBuilder = new LocomotiveBuilder(locoBase);
        carBuilder = new CarBuilder(locoBase);
    }

    private Locomotive prepareLoc(int payloadLimit, int carLimit, int poweredCarLimit){
        return locomotiveBuilder
                .setRegularCars(carLimit)
                .setPoweredCars(poweredCarLimit)
                .setCarValidator(validator)
                .setLoadValidator(loadValidator)
                .setDefaultSpeed(BigDecimal.ONE)
                .setMaxPayload(BigDecimal.valueOf(payloadLimit))
                .build();
    }

    private RailroadCar prepareCar(int netWeight, int maxWeight){
        return carBuilder.setFlag(CarType.NON_STANDARD)
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.TOXIC)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(maxWeight).setNetWeightForNonStandardCar(netWeight).build();
    }

    private IDeliverable prepareLoad(int weight){
        return new ToxicLoad(weight);
    }

    @Test
    void Should_ValidatePayload_When_CarWeightIsLessThanLocomotivePayloadLimit() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        RailroadCar car = prepareCar(100, 1200);
        assertTrue(validator.validatePayloadLimit(car, locomotive));
    }

    @Test
    void Should_ValidatePayload_When_CarWeightIsEqualToLocomotivePayloadLimit() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        RailroadCar car = prepareCar(1000, 1200);
        assertTrue(validator.validatePayloadLimit(car, locomotive));
    }

    @Test
    void Should_Not_ValidatePayload_WhenCarWeightIsGreaterThanLocomotivePayloadLimit() {
        Locomotive locomotive = prepareLoc(1000, 8, 2);
        RailroadCar car = prepareCar(1100, 1200);
        assertFalse(validator.validatePayloadLimit(car, locomotive));
    }

    @Test
    void Should_ValidatePayload_When_CarWeightIsLessThanLocomotiveAvailablePayload() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        RailroadCar car = prepareCar(100, 1200);
        locomotive.attach(car);
        RailroadCar car2 = prepareCar(100, 1200);
        assertTrue(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void Should_ValidatePayload_When_CarWeightIsEqualToLocomotiveAvailablePayload() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        RailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        RailroadCar car2 = prepareCar(500, 1200);
        assertTrue(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void Should_Not_ValidatePayload_When_CarWeightIsGreaterThanLocomotiveAvailablePayload() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        RailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        RailroadCar car2 = prepareCar(600, 1200);
        assertFalse(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void Should_ValidatePayload_When_LoadedCarWeightIsLessThanLocomotiveAvailablePayload() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        RailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        IDeliverable load = prepareLoad(400);

        RailroadCar car2 = prepareCar(50, 1200);
        ((ILoadCarrier<IDeliverable>) car2).load(load);
        assertTrue(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void Should_ValidatePayload_When_LoadedCarWeightIsEqualToLocomotiveAvailablePayload() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        RailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        IDeliverable load = prepareLoad(400);

        RailroadCar car2 = prepareCar(100, 1200);
        ((ILoadCarrier<IDeliverable>) car2).load(load);
        assertTrue(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void Should_Not_ValidatePayload_When_LoadedCarWeightIsGreaterThanLocomotiveAvailablePayload() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        RailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        IDeliverable load = prepareLoad(400);

        RailroadCar car2 = prepareCar(200, 1200);
        ((ILoadCarrier<IDeliverable>) car2).load(load);
        assertFalse(validator.validatePayloadLimit(car2, locomotive));
    }
}