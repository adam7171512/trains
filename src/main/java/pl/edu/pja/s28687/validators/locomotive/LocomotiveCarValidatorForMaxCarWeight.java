package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.train.Locomotive;
import pl.edu.pja.s28687.cars.IRailroadCar;

import java.math.BigDecimal;

/**
 * A car validator for locomotives that validates the car weight by assuming
 * gross weight of the car instead of its current weight.
 * Using this validator avoids scenarios where we could attach
 * many cars to the locomotive, but we could not load them up with cargo.
 * This validator checks that a new railroad car can be added to the locomotive
 * based on the locomotive's car limit, powered car limit, and payload limit.
 */
public class LocomotiveCarValidatorForMaxCarWeight implements ILocomotiveCarValidator {
    @Override
    public boolean validateCarLimit(IRailroadCar car, Locomotive locomotive) {
        return locomotive.getCurrentCarNumber() < locomotive.getCarLimit();
    }

    @Override
    public boolean validatePoweredCarLimit(IRailroadCar car, Locomotive locomotive) {
        return !car.isPowered()
                || locomotive.getPoweredCarsNumber()
                < locomotive.getPoweredCarLimit();
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
                .compareTo(locomotive.getMaxPayload()) <= 0;
    }

    @Override
    public boolean validate(IRailroadCar car, Locomotive locomotive) {
        return validateCarLimit(car, locomotive)
                && validatePoweredCarLimit(car, locomotive)
                && validatePayloadLimit(car, locomotive);
    }
}

