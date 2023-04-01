package pl.edu.pja.s28687.validators;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.RailroadCar;
import pl.edu.pja.s28687.validators.ILocomotiveCarValidator;

public class LocomotiveCarValidator implements ILocomotiveCarValidator {

    @Override
    public boolean validateCarLimit(RailroadCar car, Locomotive locomotive) {
        return locomotive.carsOccupied() < locomotive.getCarLimit();
    }

    @Override
    public boolean validatePoweredCarLimit(RailroadCar car, Locomotive locomotive) {
        if (car.isPowered()) {
            return locomotive.getPoweredCarsNumber() < locomotive.getPoweredCarLimit();
        }
        else return true;
    }


    @Override
    public boolean validatePayloadLimit(RailroadCar car, Locomotive locomotive) {
        return locomotive.getCurrentPayload().add(car.getCurrentWeight()).compareTo(locomotive.getMaxPayload()) <= 0;
    }

    @Override
    public boolean validate(RailroadCar car, Locomotive locomotive) {
        return validateCarLimit(car, locomotive)
                && validatePoweredCarLimit(car, locomotive)
                && validatePayloadLimit(car, locomotive);
    }
}

