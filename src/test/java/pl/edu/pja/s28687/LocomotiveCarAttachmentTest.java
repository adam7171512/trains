package pl.edu.pja.s28687;

import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.cars.*;
import pl.edu.pja.s28687.validators.CarFreightValidator;
import pl.edu.pja.s28687.validators.ICarLoadValidator;
import pl.edu.pja.s28687.validators.locomotive.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LocomotiveCarAttachmentTest {

    private static final ILocomotiveCarValidator validator = new LocomotiveCarValidatorForCurrentCarWeight();
    private static final ILocomotiveLoadValidator loadValidator = new LocomotiveLoadValidator();
    private static final ICarLoadValidator carValidator = new CarFreightValidator();

    private Locomotive locomotive;

    private Locomotive prepareTestLoc(int payloadLimit, int carLimit, int poweredCarLimit) {
        return new Locomotive(
                "testLoco"
                , 1
                , carLimit
                , BigDecimal.valueOf(payloadLimit)
                , poweredCarLimit
                , BigDecimal.TEN
                , validator
                , loadValidator
        );
    }

    @Test
    void attach_NotPoweredCar_WhenBelowCarLimit_ShouldReturnTrue() {
        locomotive = prepareTestLoc(1000, 2, 2);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        assertTrue(locomotive.attach(basicFreightCar));
    }

    @Test
    void attach_PoweredCar_WhenBelowPoweredCarLimit_ShouldReturnTrue() {
        locomotive = prepareTestLoc(1000, 1, 1);
        IRailroadCar passengerCar = new PassengerCar(1, carValidator);
        assertTrue(locomotive.attach(passengerCar));
    }

    @Test
    void attach_Car_WhenBelowCarLimitAndOtherCarAttached_ShouldReturnTrue() {
        locomotive = prepareTestLoc(1000, 2, 2);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        IRailroadCar passengerCar = new PassengerCar(1, carValidator);
        assertTrue(locomotive.attach(basicFreightCar));
        assertTrue(locomotive.attach(passengerCar));
    }

    @Test
    void attach_NotPoweredCar_WhenBelowCarLimitAndAbovePoweredCarLimit_ShouldReturnTrue() {
        locomotive = prepareTestLoc(1000, 2, 0);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        assertTrue(locomotive.attach(basicFreightCar));
    }

    @Test
    void attach_NotPoweredCar_WhenAboveCarLimit_ShouldThrowValidationException() {
        locomotive = prepareTestLoc(1000, 0, 0);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        assertThrows(ValidationException.class, () -> {
            locomotive.attach(basicFreightCar);
        });
    }

    @Test
    void attach_PoweredCar_WhenAboveCarLimit_ShouldThrowValidationException() {
        locomotive = prepareTestLoc(1000, 0, 0);
        IRailroadCar passengerCar = new PassengerCar(1, carValidator);
        assertThrows(ValidationException.class, () -> {
            locomotive.attach(passengerCar);
        });
    }

    @Test
    void attach_PoweredCar_WhenAbovePoweredCarLimit_ShouldThrowValidationException() {
        locomotive = prepareTestLoc(1000, 2, 0);
        IRailroadCar passengerCar = new PassengerCar(1, carValidator);
        assertThrows(ValidationException.class, () -> {
            locomotive.attach(passengerCar);
        });
    }

    @Test
    void attach_Car_WhenAboveCarLimitAndOtherCarAttached_ShouldThrowValidationException() {
        locomotive = prepareTestLoc(1000, 1, 1);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        IRailroadCar passengerCar = new PassengerCar(1, carValidator);
        assertTrue(locomotive.attach(basicFreightCar));
        assertThrows(ValidationException.class, () -> {
            locomotive.attach(passengerCar);
        });
    }

    @Test
    void attach_TooHeavyCar_ShouldThrowValidationException() {
        locomotive = prepareTestLoc(0, 3, 3);
        IRailroadCar passengerCar = new PassengerCar(1, carValidator);
        assertThrows(ValidationException.class, () -> {
            locomotive.attach(passengerCar);
        });
    }

    @Test
    void attach_HeavyFreightCarToLocomotiveWithPassengerTrainCarValidator_ShouldThrowValidationException() {
        ILocomotiveCarValidator passengerTrainCarValidator = new PassengerTrainCarValidator();
        locomotive = new Locomotive(
                "testLoco"
                , 1
                , 3
                , BigDecimal.valueOf(1000)
                , 3
                , BigDecimal.TEN
                , passengerTrainCarValidator
                , loadValidator
        );

        IRailroadCar heavyFreightCar = new HeavyFreightCar(1, carValidator);
        assertThrows(ValidationException.class, () -> {
            locomotive.attach(heavyFreightCar);
        });
    }

    @Test
    void attach_PassengerCarToLocomotiveWithPassengerTrainCarValidator_ShouldReturnTrue() {
        ILocomotiveCarValidator passengerTrainCarValidator = new PassengerTrainCarValidator();
        locomotive = new Locomotive(
                "testLoco"
                , 1
                , 3
                , BigDecimal.valueOf(1000)
                , 3
                , BigDecimal.TEN
                , passengerTrainCarValidator
                , loadValidator
        );
        IRailroadCar passengerCar = new PassengerCar(1, carValidator);
        assertTrue(locomotive.attach(passengerCar));
    }


    @Test
    void getLoadCarriers_ShouldReturnOnlyLoadCarriers() {
        locomotive = prepareTestLoc(1000, 3, 3);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        IRailroadCar restaurantCar = new RestaurantCar(1);
        locomotive.attach(basicFreightCar);
        locomotive.attach(restaurantCar);
        assertEquals(1, locomotive.getLoadCarriers().size());
        assertEquals(basicFreightCar, locomotive.getLoadCarriers().get(0));
    }

    @Test
    void getCars_ShouldReturnAllAttachedCars() {
        locomotive = prepareTestLoc(1000, 3, 3);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        IRailroadCar restaurantCar = new RestaurantCar(1);
        locomotive.attach(basicFreightCar);
        locomotive.attach(restaurantCar);
        assertEquals(2, locomotive.getCars().size());
        assertEquals(basicFreightCar, locomotive.getCars().get(0));
        assertEquals(restaurantCar, locomotive.getCars().get(1));
    }

    @Test
    void getCurrentCarNumber_TwoCarsAttached_ShouldReturnTwo() {
        locomotive = prepareTestLoc(1000, 3, 3);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        IRailroadCar restaurantCar = new RestaurantCar(1);
        locomotive.attach(basicFreightCar);
        locomotive.attach(restaurantCar);
        assertEquals(2, locomotive.getCurrentCarNumber());
    }

    @Test
    void getCurrentCarNumber_NoCarsAttached_ShouldReturnZero() {
        locomotive = prepareTestLoc(1000, 3, 3);
        assertEquals(0, locomotive.getCurrentCarNumber());
    }

    @Test
    void getPoweredCarsNumber_OnePoweredCarAttachedAndOneUnpowered_ShouldReturnOne() {
        locomotive = prepareTestLoc(1000, 3, 3);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        IRailroadCar restaurantCar = new RestaurantCar(1);
        locomotive.attach(basicFreightCar);
        locomotive.attach(restaurantCar);
        assertEquals(1, locomotive.getPoweredCarsNumber());
    }

    @Test
    void detach_AttachedCarShouldReturnTrue() {
        locomotive = prepareTestLoc(1000, 3, 3);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        locomotive.attach(basicFreightCar);
        assertTrue(locomotive.detach(basicFreightCar));
    }

    @Test
    void detach_UnattachedCar_ShouldThrowValidationException() {
        locomotive = prepareTestLoc(1000, 3, 3);
        IRailroadCar basicFreightCar = new BasicFreightCar(1, carValidator);
        assertThrows(ValidationException.class, () -> {
            locomotive.detach(basicFreightCar);
        });
    }
}