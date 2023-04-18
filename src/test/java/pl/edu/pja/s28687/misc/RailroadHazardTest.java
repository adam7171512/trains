package pl.edu.pja.s28687.misc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.ILocomotive;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.ILoadCarrier;
import pl.edu.pja.s28687.cars.IRailroadCar;
import pl.edu.pja.s28687.load.IDeliverable;
import pl.edu.pja.s28687.validators.locomotive.ILocomotiveCarValidator;
import pl.edu.pja.s28687.validators.locomotive.ILocomotiveLoadValidator;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RailroadHazardTest {
    private static final ILocomotiveCarValidator mockCarValidator = new ILocomotiveCarValidator() {
        @Override
        public boolean validateCarLimit(IRailroadCar car, Locomotive receiver) {
            return false;
        }

        @Override
        public boolean validatePoweredCarLimit(IRailroadCar car, Locomotive receiver) {
            return false;
        }

        @Override
        public boolean validatePayloadLimit(IRailroadCar car, Locomotive receiver) {
            return false;
        }

        @Override
        public boolean validate(IRailroadCar component, Locomotive receiver) {
            return false;
        }
    };

    private static final ILocomotiveLoadValidator mockLoadValidator = new ILocomotiveLoadValidator() {
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

    private Locomotive locomotive;

    @BeforeEach
    void setUp() {
        locomotive  = new Locomotive(
                "testLoco"
                , 1
                , 10
                , BigDecimal.TEN
                , 10
                , BigDecimal.valueOf(100)
                , mockCarValidator
                , mockLoadValidator
        );
    }
    @Test
    void setSpeed_Over200_ThrowsRailroadHazard_When_SpeedWasLowerThan200() {
        assertThrows(RailroadHazard.class, () -> locomotive.setCurrentSpeed(BigDecimal.valueOf(201)));
    }

    @Test
    void setSpeed_Over200_DoesNotThrowRailroadHazard_When_SpeedWasAlreadyHigherThan200() {
        assertThrows(RailroadHazard.class, () -> locomotive.setCurrentSpeed(BigDecimal.valueOf(201)));
        assertDoesNotThrow(() -> locomotive.setCurrentSpeed(BigDecimal.valueOf(205)));
    }

    @Test
    void setSpeed_Over200_ThrowsRailroadHazard_When_SpeedDroppedBelow200AndThenExceeded200Again() {
        assertThrows(RailroadHazard.class, () -> locomotive.setCurrentSpeed(BigDecimal.valueOf(201)));
        assertDoesNotThrow(() -> locomotive.setCurrentSpeed(BigDecimal.valueOf(205)));
        assertDoesNotThrow(() -> locomotive.setCurrentSpeed(BigDecimal.valueOf(199)));
        assertThrows(RailroadHazard.class, () -> locomotive.setCurrentSpeed(BigDecimal.valueOf(201)));
    }
}