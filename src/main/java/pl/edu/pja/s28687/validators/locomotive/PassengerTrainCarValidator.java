package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.IRailroadCar;

import java.util.Set;

public class PassengerTrainCarValidator extends LocomotiveCarValidatorForMaxCarWeight{
    @Override
    public boolean validate(IRailroadCar car, Locomotive locomotive) {
        return validateCarType(car, locomotive)
                && validateCarLimit(car, locomotive)
                && validatePoweredCarLimit(car, locomotive)
                && validatePayloadLimit(car, locomotive);
    }

    public boolean validateCarType(IRailroadCar car, Locomotive locomotive) {
        Set<CarType> allowedCarTypes = Set.of(CarType.PASSENGERS,
                CarType.LUGGAGE, CarType.RESTAURANT, CarType.POST_OFFICE);
        return allowedCarTypes.contains(car.getCarType());
    }
}
