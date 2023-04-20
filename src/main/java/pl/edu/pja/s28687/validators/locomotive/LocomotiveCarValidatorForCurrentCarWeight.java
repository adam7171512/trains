package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.train.Locomotive;
import pl.edu.pja.s28687.cars.IRailroadCar;

public class LocomotiveCarValidatorForCurrentCarWeight implements ILocomotiveCarValidator {
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
        return locomotive.getCurrentPayload().add(car.getCurrentWeight()).compareTo(locomotive.getMaxPayload()) <= 0;
    }
    @Override
    public boolean validate(IRailroadCar car, Locomotive locomotive) {
        return validateCarLimit(car, locomotive)
                && validatePoweredCarLimit(car, locomotive)
                && validatePayloadLimit(car, locomotive);
    }
}

