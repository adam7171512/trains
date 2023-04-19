package pl.edu.pja.s28687.validators.locomotive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.ILocomotive;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.AbstractRailroadCar;
import pl.edu.pja.s28687.factories.CarBuilder;
import pl.edu.pja.s28687.factories.CarsFactory;
import pl.edu.pja.s28687.factories.LocomotiveBuilder;
import pl.edu.pja.s28687.load.BasicFreightLoad;
import pl.edu.pja.s28687.load.IBasicFreight;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.load.LoadType;
import pl.edu.pja.s28687.logistics.LocoBase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocomotiveCarValidatorForMaxCarWeightTest {
    private static final LocoBase locoBase = new LocoBase();
    private static final ILocomotiveCarValidator validator = new LocomotiveCarValidatorForMaxCarWeight();
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
    private CarsFactory carsFactory;

    @BeforeEach
    void setUp() {
        locomotiveBuilder = new LocomotiveBuilder(locoBase);
        carsFactory = new CarsFactory(locoBase);
        carBuilder = new CarBuilder(locoBase);

    }

    private Locomotive prepareLocomotive(int payloadLimit, int carLimit, int poweredCarLimit) {
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
                .setPoweredForNonStandardCar(true).setLoadTypes(Set.of(LoadType.BASIC_FREIGHT)).setShipperForNonStandardCar("test")
                .setSecurityInfoForNonStandardCar("test").setNumberOfSeatsForNonStandardCar(10)
                .setMaxWeightForNonStandardCar(maxWeight).setNetWeightForNonStandardCar(netWeight).build();
    }

    @Test
    void validateCarLimit_WhenLocomotiveHasNoCars_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.PASSENGERS), locomotive));
    }

    @Test
    void validateCarLimit_WhenLocomotiveHasLessCarsAttachedThanLimit_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 2, 2);
        locomotive.attach(carsFactory.createCarOfType(CarType.PASSENGERS));
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.PASSENGERS), locomotive));
    }

    @Test
    void validateCarLimit_WhenLocomotiveNumberOfCarsAttachedEqualToLimit_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        locomotive.attach(carsFactory.createCarOfType(CarType.PASSENGERS));
        assertFalse(validator.validateCarLimit(carsFactory.createCarOfType(CarType.PASSENGERS), locomotive));
    }

    @Test
    void validateCarLimit_WhenLocomotiveHasNoCarsAndCarIsPowered_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.REFRIGERATED), locomotive));
    }

    @Test
    void validateCarLimit_WhenLocomotiveNumberOfPoweredCarsAttachedBelowLimit_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 5, 2);
        locomotive.attach(carsFactory.createCarOfType(CarType.REFRIGERATED));
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.REFRIGERATED), locomotive));
    }

    @Test
    void validatePoweredCarLimit_WhenLocomotiveNumberOfPoweredCarsAttachedEqualToLimit_ShouldReturnFalse() {
        Locomotive locomotive = prepareLocomotive(1000, 5, 1);
        locomotive.attach(carsFactory.createCarOfType(CarType.REFRIGERATED));
        assertFalse(validator.validatePoweredCarLimit(carsFactory.createCarOfType(CarType.REFRIGERATED), locomotive));
    }

    @Test
    void validate_WhenLocomotiveNumberOfPoweredCarsAttachedEqualToLimit_ShouldReturnFalse() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 5);
        locomotive.attach(carsFactory.createCarOfType(CarType.REFRIGERATED));
        assertFalse(validator.validate(carsFactory.createCarOfType(CarType.REFRIGERATED), locomotive));
    }

    @Test
    void validateCarLimit_WhenLocomotiveReachedPoweredCarLimitAndCarIsNotPowered_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 5, 1);
        locomotive.attach(carsFactory.createCarOfType(CarType.REFRIGERATED));
        assertTrue(validator.validateCarLimit(carsFactory.createCarOfType(CarType.BASIC_FREIGHT), locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarMaxWeightLowerThanPayloadLimit_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertTrue(validator.validatePayloadLimit(prepareCar(100, 800), locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarMaxWeightEqualToPayloadLimit_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertTrue(validator.validatePayloadLimit(prepareCar(1000, 1000), locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarMaxWeightGreaterThanPayloadLimit_ShouldReturnFalse() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        assertFalse(validator.validatePayloadLimit(prepareCar(1000, 1200), locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarMaxWeightLowerThanAvailablePayloadLimitWhenOtherCarIsAttached_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        locomotive.attach(prepareCar(100, 200));
        assertTrue(validator.validatePayloadLimit(prepareCar(100, 200), locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarMaxWeightEqualToAvailablePayloadLimitWhenOtherCarIsAttached_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        locomotive.attach(prepareCar(100, 500));
        assertTrue(validator.validatePayloadLimit(prepareCar(100, 500), locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarMaxWeightGreaterThanAvailablePayloadLimitWhenOtherCarIsAttached_ShouldReturnFalse() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        locomotive.attach(prepareCar(100, 500));
        assertFalse(validator.validatePayloadLimit(prepareCar(100, 600), locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarMaxWeightLowerThanPayloadLimitWhenOtherCarWithCargoLoaded_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        ILoadCarrier<IBasicFreight> loadCarrier = (ILoadCarrier<IBasicFreight>) prepareCar(100, 700);
        loadCarrier.load(new BasicFreightLoad(1, 600));
        locomotive.attach(loadCarrier);
        assertTrue(validator.validatePayloadLimit(prepareCar(100, 300), locomotive));
    }

    @Test
    void validatePayloadLimit_ForCarMaxWeightGreaterThanPayloadLimitWhenOtherCarWithCargoLoaded_ShouldReturnTrue() {
        Locomotive locomotive = prepareLocomotive(1000, 1, 1);
        ILoadCarrier<IBasicFreight> loadCarrier = (ILoadCarrier<IBasicFreight>) prepareCar(100, 700);
        loadCarrier.load(new BasicFreightLoad(1, 600));
        locomotive.attach(loadCarrier);
        assertFalse(validator.validatePayloadLimit(prepareCar(100, 700), locomotive));
    }
}