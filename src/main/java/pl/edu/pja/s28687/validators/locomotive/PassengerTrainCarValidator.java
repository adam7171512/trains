package pl.edu.pja.s28687.validators.locomotive;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.cars.CarType;
import pl.edu.pja.s28687.cars.IRailroadCar;

import java.util.Set;

/**
 * A car validator for passenger trains that checks if a given railroad car is a valid type for a passenger train.
 * This validator also checks if a new railroad car can be added to the passenger train
 * based on the train's car limit and powered car limit.
 * This validator always validates the payload limit, because the payload limit is not relevant for passenger trains.
 */
public class PassengerTrainCarValidator implements ILocomotiveCarValidator {

    /**
     * Validates whether a given railroad car could be attached to passenger train.
     *
     * @param car        The railroad car to be validated
     * @param locomotive The locomotive to which the car would be attached
     * @return true if the car is a valid type for a passenger train, false otherwise
     */
    @Override
    public boolean validate(IRailroadCar car, Locomotive locomotive) {
        return validateCarType(car) && validateCarLimit(car, locomotive) && validatePoweredCarLimit(car, locomotive) && validatePayloadLimit(car, locomotive);
    }

    /**
     * Validates whether a given railroad car is a valid type for a passenger train.
     *
     * @param car The railroad car to be validated
     * @return true if the car is a valid type for a passenger train, false otherwise
     */
    public boolean validateCarType(IRailroadCar car) {
        Set<CarType> allowedCarTypes = Set.of(CarType.PASSENGERS, CarType.LUGGAGE, CarType.RESTAURANT, CarType.POST_OFFICE);
        return allowedCarTypes.contains(car.getCarType());
    }

    /**
     * Validates whether a given railroad car can be added to the given locomotive
     * based on the locomotive's car limit.
     *
     * @param car      The railroad car to be validated
     * @param receiver The locomotive that will receive the validated railroad car
     * @return true if the car can be added to the locomotive based on its car limit, false otherwise
     */
    @Override
    public boolean validateCarLimit(IRailroadCar car, Locomotive receiver) {
        return receiver.getCurrentCarNumber() < receiver.getCarLimit();
    }

    /**
     * Validates whether a given railroad car can be added to the given locomotive
     * based on the locomotive's powered car number limit.
     *
     * @param car      The powered railroad car to be validated
     * @param receiver The locomotive that will receive the validated powered railroad car
     * @return true if the powered car can be added to the locomotive based on its powered
     * car limit, false otherwise
     */
    @Override
    public boolean validatePoweredCarLimit(IRailroadCar car, Locomotive receiver) {
        return (!car.isPowered() || receiver.getPoweredCarsNumber() < receiver.getPoweredCarLimit());
    }

    /**
     * Validates whether a given railroad car can be added to the given locomotive
     * based on the locomotive's available payload limit.
     * This method always returns true, because the payload limit is not relevant for passenger trains.
     *
     * @param car      The railroad car to be validated
     * @param receiver The locomotive that will receive the validated railroad car
     * @return true
     */
    @Override
    public boolean validatePayloadLimit(IRailroadCar car, Locomotive receiver) {
        return true;
    }
}
