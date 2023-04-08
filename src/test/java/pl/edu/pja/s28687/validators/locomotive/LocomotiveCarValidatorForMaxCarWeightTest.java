package pl.edu.pja.s28687.validators.locomotive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.RailroadCar;
import pl.edu.pja.s28687.factories.CarBuilder;
import pl.edu.pja.s28687.factories.CarsFactory;
import pl.edu.pja.s28687.factories.LocomotiveBuilder;
import pl.edu.pja.s28687.load.*;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LocomotiveCarValidatorForMaxCarWeightTest {
    private LocoBase locoBase;
    private LocomotiveBuilder locomotiveBuilder;
    private CarBuilder carBuilder;
    private final ILocomotiveCarValidator validator = new LocomotiveCarValidatorForMaxCarWeight();
    private final ILocomotiveLoadValidator loadValidator = new LocomotiveLoadValidator();
    private CarsFactory carsFactory;

    @BeforeEach
    void setUp(){
        locoBase = new LocoBase();
        locomotiveBuilder = new LocomotiveBuilder(locoBase);
        carsFactory = new CarsFactory(locoBase);
        carBuilder = new CarBuilder(locoBase);

    }

    private Locomotive prepareLocomotive(int payloadLimit, int carLimit, int poweredCarLimit){
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
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.BASIC_FREIGHT)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(maxWeight).setNetWeightForNonStandardCar(netWeight).build();
    }

    @Test
    void Should_ValidateCarLimit_When_LocomotiveHasNoCars() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.PASSENGERS), locomotive));
    }

    @Test
    void Should_ValidateCarLimit_When_LocomotiveHasLassCarsThanLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 2, 2);
        locomotive.attach(carsFactory.createCarOfType(CarType.PASSENGERS));
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.PASSENGERS), locomotive));
    }

    @Test
    void Should_NotValidateCarLimit_When_LocomotiveHasReachedCarLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        locomotive.attach(carsFactory.createCarOfType(CarType.PASSENGERS));
        assertFalse(validator.validateCarLimit(carsFactory.createCarOfType(CarType.PASSENGERS), locomotive));
    }

    @Test
    void Should_ValidatePoweredCarLimit_When_LocomotiveHasNoCars() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.REFRIGERATED), locomotive));
    }

    @Test
    void Should_ValidatePoweredCarLimit_When_LocomotiveHasLassPoweredCarsThanLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 5, 2);
        locomotive.attach(carsFactory.createCarOfType(CarType.REFRIGERATED));
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.REFRIGERATED), locomotive));
    }

    @Test
    void Should_NotValidatePoweredCarLimit_When_LocomotiveHasReachedPoweredCarLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 5, 1);
        locomotive.attach(carsFactory.createCarOfType(CarType.REFRIGERATED));
        assertFalse(validator.validatePoweredCarLimit(carsFactory.createCarOfType(CarType.REFRIGERATED), locomotive));
    }

    @Test
    void Should_NotValidatePoweredCar_When_LocomotiveHasReachedRegularCarLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 5);
        locomotive.attach(carsFactory.createCarOfType(CarType.REFRIGERATED));
        assertFalse(validator.validate(carsFactory.createCarOfType(CarType.REFRIGERATED), locomotive));
    }

    @Test
    void Should_ValidateRegularCar_When_LocomotiveHasReachedRPoweredCarLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 5, 1);
        locomotive.attach(carsFactory.createCarOfType(CarType.REFRIGERATED));
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.BASIC_FREIGHT), locomotive));
    }

    @Test
    void Should_ValidatePayloadLimit_When_LocomotiveHasNoCars_And_CarMaxWeightLessThanLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertTrue(validator.validatePayloadLimit(prepareCar(100, 800), locomotive));
    }

    @Test
    void Should_ValidatePayloadLimit_When_LocomotiveHasNoCars_And_CarMaxWeightEqualToLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertTrue(validator.validatePayloadLimit(prepareCar(1000, 1000), locomotive));
    }

    @Test
    void Should_NotValidatePayloadLimit_When_LocomotiveHasNoCars_And_CarMaxWeightMoreThanLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertFalse(validator.validatePayloadLimit(prepareCar(1000, 1200), locomotive));
    }

    @Test
    void Should_ValidatePayloadLimit_When_LocomotiveHasCars_And_CarMaxWeightLessThanLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        locomotive.attach(prepareCar(100, 200));
        assertTrue(validator.validatePayloadLimit(prepareCar(100, 200), locomotive));
    }

    @Test
    void Should_ValidatePayloadLimit_When_LocomotiveHasCars_And_CarMaxWeightEqualToLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        locomotive.attach(prepareCar(100, 500));
        assertTrue(validator.validatePayloadLimit(prepareCar(100, 500), locomotive));
    }

    @Test
    void Should_NotValidatePayloadLimit_When_LocomotiveHasCars_And_CarMaxWeightMoreThanLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        locomotive.attach(prepareCar(100, 500));
        assertFalse(validator.validatePayloadLimit(prepareCar(100, 600), locomotive));
    }

    @Test
    void Should_ValidatePayloadLimit_When_LocomotiveHasLoadedCar_And_CarMaxWeightLessThanLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        ILoadCarrier<IBasicFreight> loadCarrier = (ILoadCarrier<IBasicFreight>) prepareCar(100, 700);
        loadCarrier.load(new BasicFreightLoad(600));
        locomotive.attach(loadCarrier);
        assertTrue(validator.validatePayloadLimit(prepareCar(100, 300), locomotive));
    }

    @Test
    void Should_NotValidatePayloadLimit_When_LocomotiveHasLoadedCar_And_CarMaxWeightMoreThanLimit() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        ILoadCarrier<IBasicFreight> loadCarrier = (ILoadCarrier<IBasicFreight>) prepareCar(100, 700);
        loadCarrier.load(new BasicFreightLoad(600));
        locomotive.attach(loadCarrier);
        assertFalse(validator.validatePayloadLimit(prepareCar(100, 700), locomotive));
    }
}