package pl.edu.pja.s28687.validators.locomotive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.ILocomotive;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.AbstractRailroadCar;
import pl.edu.pja.s28687.factories.CarBuilder;
import pl.edu.pja.s28687.factories.LocomotiveBuilder;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.load.ToxicLoad;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocomotiveCarValidatorForCurrentCarWeightTest {
    private static final LocoBase locoBase = new LocoBase();
    private static final ILocomotiveCarValidator validator = new LocomotiveCarValidatorForCurrentCarWeight();
    private static final ILocomotiveLoadValidator loadValidator = new ILocomotiveLoadValidator() {
        @Override
        public boolean validate(IDeliverable load, ILocomotive receiver) {
            return false;
        }

        @Override
        public List<ILoadCarrier<? extends IDeliverable>> getCarsThatCouldLoad(IDeliverable load, ILocomotive receiver) {
            return null;
        }

        @Override
        public boolean validateWeight(IDeliverable load, ILocomotive receiver) {
            return false;
        }
    };
    private LocomotiveBuilder locomotiveBuilder;
    private CarBuilder carBuilder;

    @BeforeEach
    void setUp() {
        locomotiveBuilder = new LocomotiveBuilder(locoBase);
        carBuilder = new CarBuilder(locoBase);
    }

    private Locomotive prepareLoc(int payloadLimit, int carLimit, int poweredCarLimit) {
        return locomotiveBuilder
                .setRegularCars(carLimit)
                .setPoweredCars(poweredCarLimit)
                .setCarValidator(validator)
                .setLoadValidator(loadValidator)
                .setDefaultSpeed(BigDecimal.ONE)
                .setMaxPayload(BigDecimal.valueOf(payloadLimit))
                .build();
    }

    private AbstractRailroadCar prepareCar(int netWeight, int maxWeight) {
        return carBuilder.setFlag(CarType.NON_STANDARD)
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.TOXIC)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(maxWeight).setNetWeightForNonStandardCar(netWeight).build();
    }

    private IDeliverable prepareLoad(int weight) {
        return new ToxicLoad(1, weight);
    }

    @Test
    void validatePayloadLimit_ForCarWeightLowerThanPayloadLimit_ShouldReturnTrue() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        AbstractRailroadCar car = prepareCar(100, 1200);
        assertTrue(validator.validatePayloadLimit(car, locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarWeightEqualToPayloadLimit_ShouldReturnTrue() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        AbstractRailroadCar car = prepareCar(1000, 1200);
        assertTrue(validator.validatePayloadLimit(car, locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarWeightGreaterThanPayloadLimit_ShouldReturnFalse() {
        Locomotive locomotive = prepareLoc(1000, 8, 2);
        AbstractRailroadCar car = prepareCar(1100, 1200);
        assertFalse(validator.validatePayloadLimit(car, locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarWeightLowerThanAvailablePayloadLimitWhenOtherCarIsAttached_ShouldReturnTrue() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        AbstractRailroadCar car = prepareCar(100, 1200);
        locomotive.attach(car);
        AbstractRailroadCar car2 = prepareCar(100, 1200);
        assertTrue(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarWeightEqualToAvailablePayloadLimitWhenOtherCarIsAttached_ShouldReturnTrue() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        AbstractRailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        AbstractRailroadCar car2 = prepareCar(500, 1200);
        assertTrue(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarWeightGreaterThanAvailablePayloadLimitWhenOtherCarIsAttached_ShouldReturnFalse() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        AbstractRailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        AbstractRailroadCar car2 = prepareCar(600, 1200);
        assertFalse(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarWeightLowerThanPayloadLimitWhenCarHasCargo_ShouldReturnTrue() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        AbstractRailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        IDeliverable load = prepareLoad(400);

        AbstractRailroadCar car2 = prepareCar(50, 1200);
        ((ILoadCarrier<IDeliverable>) car2).load(load);
        assertTrue(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarWeightEqualToPayloadLimitWhenCarHasCargo_ShouldReturnTrue() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        AbstractRailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        IDeliverable load = prepareLoad(400);

        AbstractRailroadCar car2 = prepareCar(100, 1200);
        ((ILoadCarrier<IDeliverable>) car2).load(load);
        assertTrue(validator.validatePayloadLimit(car2, locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarWeightGreaterThanPayloadLimitWhenCarHasCargo_ShouldReturnFalse() {
        Locomotive locomotive = prepareLoc(1000, 2, 2);
        AbstractRailroadCar car = prepareCar(500, 1200);
        locomotive.attach(car);
        IDeliverable load = prepareLoad(400);

        AbstractRailroadCar car2 = prepareCar(200, 1200);
        ((ILoadCarrier<IDeliverable>) car2).load(load);
        assertFalse(validator.validatePayloadLimit(car2, locomotive));
    }
}