package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.IRailroadCar;

import java.math.BigDecimal;

public class LocomotiveCarValidatorForMaxCarWeight implements ILocomotiveCarValidator {
    @Override
    public boolean validateCarLimit(IRailroadCar car, Locomotive locomotive) {
        return locomotive.getCurrentCarNumber() < locomotive.getCarLimit();
    }
    @Override
    public boolean validatePoweredCarLimit(IRailroadCar car, Locomotive locomotive) {
        return !car.isPowered() || locomotive.getPoweredCarsNumber() < locomotive.getPoweredCarLimit();
    }
    @Override
    public boolean validatePayloadLimit(IRailroadCar car, Locomotive locomotive) {
        return locomotive
                .getCars()
                .stream()
                .map(IRailroadCar::getGrossWeight)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .add(car.getGrossWeight())
                .compareTo(locomotive.getMaxPayload())
                <= 0;
    }
    @Override
    public boolean validate(IRailroadCar car, Locomotive locomotive) {
        return validateCarLimit(car, locomotive)
                && validatePoweredCarLimit(car, locomotive)
                && validatePayloadLimit(car, locomotive);
    }
}

